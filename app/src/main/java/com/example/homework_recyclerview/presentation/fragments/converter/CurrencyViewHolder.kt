package com.example.homework_recyclerview.presentation.fragments.converter

import androidx.recyclerview.widget.RecyclerView
import com.example.convertor.databinding.ItemCurrencyRvBinding
import com.example.homework_recyclerview.domain.repository.Currency

class CurrencyViewHolder(
    private var function: (Currency, Int) -> Unit,
    private val binding: ItemCurrencyRvBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: Currency, position: Int) {

        binding.currencyText.setText(item.text.toString())
        binding.currencyType.text = item.type
        binding.currencyFlag.setBackgroundResource(item.flag)
        binding.currencyFlag.setOnLongClickListener {
            function(item, position)
            true
        }
    }

}