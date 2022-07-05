package com.example.homework_recyclerview.presentation.fragments.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.convertor.databinding.ItemCurrencyRvBinding
import com.example.homework_recyclerview.domain.repository.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConvertorAdapter(
    private val clickListener: () -> Unit,
    private val function: (Currency, Int) -> Unit
) : ListAdapter<Currency, CurrencyViewHolder>(CustomerModelCallback()), CurrencyAdapterItemTouchHelper {

    val data = mutableListOf<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyViewHolder(function, ItemCurrencyRvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    fun deleteItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val fromEmoji = data[from]
        data.removeAt(from)
        if (to < from) {
            data.add(to, fromEmoji)
        } else {
            data.add(to - 1, fromEmoji)
        }
        notifyItemMoved(from, to)
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        moveItem(fromPosition, toPosition)
    }

    override fun onDismiss(position: Int) {
        deleteItem(position)
    }
}


class CustomerModelCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem == newItem
}