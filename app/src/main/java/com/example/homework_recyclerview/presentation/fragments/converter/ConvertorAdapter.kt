package com.example.homework_recyclerview.presentation.fragments.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convertor.R
import com.example.convertor.databinding.ItemCurrencyRvBinding
import com.example.homework_recyclerview.*
import com.example.homework_recyclerview.domain.repository.Currency
import com.example.homework_recyclerview.domain.repository.Parent

class ConvertorAdapter(
    private val clickListener: () -> Unit,
    private val function: (Currency, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableListOf<Parent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_currency_rv -> CurrencyViewHolder(
                function,
                ItemCurrencyRvBinding.inflate(inflater, parent, false)
            )
            R.layout.item_btn_add_rv -> AddButtonViewHolder(
                inflater,
                parent,
                clickListener
            )
            else -> CurrencyViewHolder(
                function,
                ItemCurrencyRvBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrencyViewHolder -> holder.bind(data[position] as Currency, position)
            is AddButtonViewHolder -> holder.bind(data[position] as Add)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setItems(list: List<Parent>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun addNewItem(newItem: Parent, position: Int) {
        data.add(position, newItem as Currency)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun deleteCurrency(currency: Currency) {
        data.remove(currency)
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

    fun sortByName() {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.sortBy { it.type }
        val button: Parent = data.last()
        val items = mutableListOf<Parent>()
        items.addAll(0, data1)
        items.add(button)
        setItems(items)
    }

    fun sortByPrice() {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.sortBy { it.text }
        val button: Parent = data.last()
        val items = mutableListOf<Parent>()
        items.addAll(0, data1)
        items.add(button)
        setItems(items)
    }

    fun getPositionType(newItem: Parent): Int {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.add(newItem as Currency)
        data1.sortBy { it.type }
        return data1.indexOf(newItem)
    }

    fun getPositionName(newItem: Parent): Int {
        val data1 = data.dropLast(1) as MutableList<Currency>
        data1.add(newItem as Currency)
        data1.sortBy { it.text }
        return data1.indexOf(newItem)
    }

    fun updateCurrencyData(textTenge: String) {
        val data1 = data.dropLast(1) as MutableList<Currency>
        for (item in data1) {
            item.text = textTenge.toInt() / item.course
            notifyDataSetChanged()
        }

    }

}
//
//object CustomerModelCallback : DiffUtil.ItemCallback<Parent>() {
//    override fun areItemsTheSame(oldItem: Parent, newItem: Parent): Boolean =
//        oldItem.id == newItem.id
//
//    override fun areContentsTheSame(oldItem: Parent, newItem: Parent): Boolean =
//        oldItem == newItem
//}