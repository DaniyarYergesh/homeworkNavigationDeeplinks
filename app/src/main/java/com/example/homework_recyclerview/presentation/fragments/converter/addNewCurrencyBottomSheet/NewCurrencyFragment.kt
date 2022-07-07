package com.example.homework_recyclerview.presentation.fragments.converter.addNewCurrencyBottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convertor.databinding.DialogBottomSheetAddItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class NewCurrencyFragment : BottomSheetDialogFragment(){

    private var _binding: DialogBottomSheetAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBottomSheetAddItemBinding.inflate(inflater,container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val ratesRecyclerView = binding.ratesRecyclerView
        val adapter = NewCurrencyAdapter()



        ratesRecyclerView.adapter = adapter
        ratesRecyclerView.layoutManager = layoutManager
    }



    interface SecondBottomSheet {

        fun addNewItemFromBottomSheet(
            nameOfCurrency: TextInputEditText,
            costRespectiveToTenge: TextInputEditText,
            res: Int
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}