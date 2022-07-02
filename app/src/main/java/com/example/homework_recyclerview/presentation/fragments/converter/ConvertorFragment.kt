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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.example.convertor.R
import com.example.convertor.databinding.LayoutFragmentConvertorBinding
import com.example.homework_recyclerview.Add
import com.example.homework_recyclerview.domain.repository.Currency
import com.example.homework_recyclerview.domain.repository.Parent
import com.example.homework_recyclerview.presentation.fragments.converter.bottomSheet_Dialog.BottomSheetDialog
import com.example.homework_recyclerview.presentation.fragments.converter.bottomSheet_Dialog.SelectCurrencyBottomSheet
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import homework_recyclerview.DeleteDialogCallback
import homework_recyclerview.DeleteDialogFragment
import kotlin.random.Random

class ConvertorFragment : Fragment(), DeleteDialogCallback,
    BottomSheetDialog.SecondBottomSheet {

    private var _binding : LayoutFragmentConvertorBinding? = null
    private val binding get() = _binding!!

    private var deletedCurrency: Currency? = null
    private var positionOfDeletedItem: Int? = null
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var chosenIndex = -1
    private var adapter: ConvertorAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var currencyList: List<Parent>
    private lateinit var kzCurrency: TextInputEditText

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
    }


    private fun setupFun() {
        currencyList = listOf(
            Currency(0, "Доллары, США ", R.drawable.image_1_2, 400),
            Currency(0, "Лира, Турция", R.drawable.image_1_3, 200),
            Currency(0, "Евро, EC", R.drawable.image_1_4, 100),
            Currency(0, "Доллары, США", R.drawable.image_1_5, 300),
            Currency(0, "Доллары, США", R.drawable.image_1_2, 80),
            Currency(0, "Доллары, США", R.drawable.image_1_2, 90),
            Currency(0, "Лира, Турция", R.drawable.image_1_3, 150),
            Currency(0, "Евро, EC", R.drawable.image_1_4, 60),
            Add("Добавить", R.drawable.path837)
        )

        val myLambda: () -> Unit =
            {
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

        adapter = ConvertorAdapter(
            myLambda,
            myLambda2
        )

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val myRecyclerView = binding.recyclerView

        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = layoutManager

        adapter?.setItems(currencyList)

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter!!))
        itemTouchHelper.attachToRecyclerView(myRecyclerView)

        val itemTouchHelper1 = ItemTouchHelper(DragDropMove(adapter!!))
        itemTouchHelper1.attachToRecyclerView(myRecyclerView)

        kzCurrency = binding.currencyTextKaz
        kzCurrency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isNotBlank()) {
                    adapter?.updateCurrencyData(text)
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
                R.id.alphabet -> {
                    chosenIndex = 0
                    adapter?.sortByName()
                    true
                }
                R.id.currency -> {
                    chosenIndex = 1
                    adapter?.sortByPrice()
                    true
                }
                R.id.drop_sorting -> {
                    adapter?.setItems(currencyList)
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
        adapter?.deleteCurrency(deletedCurrency!!)
        Snackbar.make(
            binding.recyclerView,
            "Item Deleted",
            Snackbar.LENGTH_SHORT
        )
            .setAction("Undo") {
                adapter?.addNewItem(deletedCurrency!!, positionOfDeletedItem!!)
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
        var position = 0
        val random: Int = Random.nextInt(9)
        if (chosenIndex == -1) {
            position = adapter?.data!!.size - 1
        }
        if (chosenIndex == 0) {
            position = adapter?.getPositionType(currencyList[random]) ?: 0
        }
        if (chosenIndex == 1) {
            position = adapter?.getPositionName(currencyList[random]) ?: 0
        }
        adapter?.addNewItem(
            Currency(
                kzCurrency.text.toString()
                    .toInt() / Integer.parseInt(costRespectiveToTenge.text.toString()),
                nameOfCurrency.text.toString(),
                res,
                Integer.parseInt(costRespectiveToTenge.text.toString())
            ), position
        )
        scrollBottom(adapter?.itemCount ?: 0)
    }


}