package com.example.homework_recyclerview.presentation.fragments.converter.addNewCurrencyBottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convertor.databinding.DialogBottomSheetAddItemBinding
import com.example.homework_recyclerview.presentation.fragments.converter.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewCurrencyFragment : BottomSheetDialogFragment() {

    private lateinit var myLambda: (String) -> Unit
    private val viewModel: MainViewModel by sharedViewModel()
    private var _binding: DialogBottomSheetAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBottomSheetAddItemBinding.inflate(inflater, container, false)

        viewModel.loadCurrencyRates()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myLambda =  {
            Log.e("NewCurrencyFragment", "before call myLambda: ${viewModel.currencyList.value.toString()}")
            viewModel.addNewRate(it,viewModel.rates[it]!!)

            dismiss()
            Log.e("NewCurrencyFragment", "after call myLambda: ${viewModel.currencyList.value.toString()}")
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val ratesRecyclerView = binding.ratesRecyclerView

        val adapter = NewCurrencyAdapter(emptyList(), myLambda)

        ratesRecyclerView.adapter = adapter
        ratesRecyclerView.layoutManager = layoutManager

        viewModel.listOfRates.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}