package com.example.homework_recyclerview.presentation.fragments.converter.addNewCurrencyBottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convertor.databinding.ItemAddCurrencyRvBinding

class NewCurrencyAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<NewCurrencyAdapter.NewCurrencyViewHolder>() {

    var data = listOf<String>()

    inner class NewCurrencyViewHolder(private val binding: ItemAddCurrencyRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, onClick: (String) -> Unit) {
            binding.currencyName.text = item
            binding.currencyName.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewCurrencyViewHolder(ItemAddCurrencyRvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewCurrencyViewHolder, position: Int) {
        holder.bind(data[position], onClick)
    }

    override fun getItemCount() = data.size
}