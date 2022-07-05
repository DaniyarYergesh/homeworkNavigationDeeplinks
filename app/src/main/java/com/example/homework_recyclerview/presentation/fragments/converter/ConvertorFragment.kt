package com.example.homework_recyclerview.presentation.fragments.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.example.convertor.R
import com.example.convertor.databinding.LayoutFragmentConvertorBinding
import com.example.homework_recyclerview.domain.repository.Currency
import com.example.homework_recyclerview.presentation.fragments.converter.bottomSheet_Dialog.BottomSheetDialog
import com.example.homework_recyclerview.presentation.fragments.converter.bottomSheet_Dialog.SelectCurrencyBottomSheet
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import homework_recyclerview.DeleteDialogCallback
import homework_recyclerview.DeleteDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConvertorFragment : Fragment(), DeleteDialogCallback,
    BottomSheetDialog.SecondBottomSheet {

    private var _binding: LayoutFragmentConvertorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()
    private var deletedCurrency: Currency? = null
    private var positionOfDeletedItem: Int? = null
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var chosenIndex = -1
    private lateinit var adapter: ConvertorAdapter
    private var layoutManager: LinearLayoutManager? = null

    private lateinit var kzCurrency: TextInputEditText
    private var counter: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutFragmentConvertorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.main_menu)

        setupFun()
        onOptionsItemSelected1()
        setupFirstCurrency()

        viewModel.currencyList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupFirstCurrency() {
        binding.currencyFlag.setImageResource(R.drawable.image_1)
    }


    private fun setupFun() {
        val myLambda: () -> Unit = {
            BottomSheetDialog().show(childFragmentManager, null)
        }

        val myLambda2: (Currency, Int) -> Unit = { item, position ->
            deletedCurrency = item
            positionOfDeletedItem = position

            toolbar.title = "$position Item Selected"
            toolbar.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.toolbarColor
                )
            )
            toolbar.menu.findItem(R.id.menu_del).isVisible = true
            toolbar.menu.findItem(R.id.sorting_by).isVisible = false
            toolbar.menu.findItem(R.id.drop_sorting).isVisible = false
            Log.i("MainActivity", "$deletedCurrency")

        }

        adapter = ConvertorAdapter(myLambda, myLambda2, viewModel.balance)

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val myRecyclerView = binding.recyclerView

        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = layoutManager

        val itemTouchHelper = ItemTouchHelper(DragDropMove(adapter))
        itemTouchHelper.attachToRecyclerView(myRecyclerView)


        kzCurrency = binding.currencyTextKaz
        kzCurrency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.length > Int.MAX_VALUE.toString().length - 1) return
                if (text.isNotBlank()) {
                    viewModel.setBalance(text.toInt())
                }
            }
        }
        )


    }

    private lateinit var dialog: DialogFragment

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val chosenMenuItemId = when (chosenIndex) {
            0 -> R.id.alphabet
            1 -> R.id.currency
            else -> 0
        }
        menu.findItem(chosenMenuItemId).isChecked = true
        super.onPrepareOptionsMenu(menu)
    }

    private fun onOptionsItemSelected1() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_new_currency -> {
                    BottomSheetDialog().show(childFragmentManager, null)
                    true
                }
                R.id.alphabet -> {
                    chosenIndex = 0
                    viewModel.sortByName()
                    true
                }
                R.id.currency -> {
                    chosenIndex = 1
                    viewModel.sortByPrice()
                    true
                }
                R.id.drop_sorting -> {
                    chosenIndex = -1
                    viewModel.sortByID()
                    true
                }
                R.id.menu_del -> {
                    deleteItems()
                    true
                }
                else -> false
            }
        }

    }

    private fun scrollBottom(n: Int) {
        val smoothScroller = object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        }
        smoothScroller.targetPosition = n
        layoutManager?.startSmoothScroll(smoothScroller) // плавная прокрутка
    }

    private fun deleteItems() {
        dialog = DeleteDialogFragment()
        dialog.show(childFragmentManager, null)
        toolbar.title = "Converter"
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.menu.findItem(R.id.menu_del).isVisible = false
        toolbar.menu.findItem(R.id.sorting_by).isVisible = true
        toolbar.menu.findItem(R.id.drop_sorting).isVisible = true
    }

    override fun onDeleteButton() {
        viewModel.deleteCurrency(deletedCurrency!!)
        Snackbar.make(
            binding.recyclerView,
            "Item Deleted",
            Snackbar.LENGTH_SHORT
        )
            .setAction("Undo") {
                viewModel.addNewItem(deletedCurrency!!, positionOfDeletedItem!!)
            }
            .show()
    }

    override fun openSecondBottomSheet(fragmentManager: FragmentManager) {
        SelectCurrencyBottomSheet().show(fragmentManager, null)
    }

    override fun addNewItemFromBottomSheet(
        nameOfCurrency: TextInputEditText,
        costRespectiveToTenge: TextInputEditText,
        res: Int
    ) {
        val newItem = Currency(
            counter++,
            kzCurrency.text.toString().toInt()
                    / Integer.parseInt(costRespectiveToTenge.text.toString()),
            nameOfCurrency.text.toString(),
            res,
            Integer.parseInt(costRespectiveToTenge.text.toString()))


        if (chosenIndex == -1) viewModel.addNewItem(newItem, adapter.itemCount)

        if (chosenIndex == 0) viewModel.getPositionType(newItem)

        if (chosenIndex == 1) viewModel.getPositionName(newItem)

        scrollBottom(adapter.itemCount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}