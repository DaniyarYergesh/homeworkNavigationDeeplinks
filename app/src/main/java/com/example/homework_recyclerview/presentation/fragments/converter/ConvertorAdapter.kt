package com.example.homework_recyclerview.presentation.fragments.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.convertor.databinding.ItemCurrencyRvBinding
import com.example.homework_recyclerview.domain.repository.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConvertorAdapter(
    private val deleteItem:(Currency)->Unit,
    private val moveItem:(Int,Int)->Unit,
    private val function: (Currency, Int) -> Unit,
    private val value: LiveData<Int>
) : ListAdapter<Currency, CurrencyViewHolder>(CustomerModelCallback()), CurrencyAdapterItemTouchHelper {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyViewHolder(ItemCurrencyRvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position), position, value, function)
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        moveItem(fromPosition, toPosition)
    }

    override fun onDismiss(position: Int) {
        val currency = getItem(position)
        deleteItem(currency)
        notifyItemRemoved(position)
    }

}


class CustomerModelCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem == newItem
}