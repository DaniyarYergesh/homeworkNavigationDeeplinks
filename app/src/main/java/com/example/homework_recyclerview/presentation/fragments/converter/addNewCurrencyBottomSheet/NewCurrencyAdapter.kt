package com.example.homework_recyclerview.presentation.fragments.converter.addNewCurrencyBottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.convertor.databinding.ItemAddCurrencyRvBinding
import com.example.homework_recyclerview.domain.repository.Currency

class NewCurrencyAdapter(val onClick:()->Unit):ListAdapter<Currency, NewCurrencyAdapter.NewCurrencyViewHolder>
    (CustomerModelCallback())
{

    inner class NewCurrencyViewHolder(private val binding: ItemAddCurrencyRvBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Currency, onClick: () -> Unit){
            binding.currencyName.text = item.type
            binding.currencyName.setOnClickListener{
                onClick
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewCurrencyViewHolder(ItemAddCurrencyRvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewCurrencyViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }


}

class CustomerModelCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem == newItem
}