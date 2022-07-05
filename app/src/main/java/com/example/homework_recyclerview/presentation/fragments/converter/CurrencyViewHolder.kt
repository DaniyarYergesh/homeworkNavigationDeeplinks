package com.example.homework_recyclerview.presentation.fragments.converter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.convertor.databinding.ItemCurrencyRvBinding
import com.example.homework_recyclerview.domain.repository.Currency

class CurrencyViewHolder(
    private val binding: ItemCurrencyRvBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: Currency, position: Int, value:LiveData<Int>, function: (Currency, Int) -> Unit) {

        binding.currencyText.setText(item.text.toString())
        binding.currencyType.text = item.type
        binding.currencyFlag.setBackgroundResource(item.flag)
        binding.currencyFlag.setOnLongClickListener {
            function(item, position)
            true
        }
        value.observe(itemView.context as LifecycleOwner){
            val currency = it.toDouble() / item.course
            binding.currencyText.setText("%.4f".format(currency))
        }
    }

}