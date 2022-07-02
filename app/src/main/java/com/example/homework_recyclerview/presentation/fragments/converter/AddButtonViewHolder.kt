package com.example.homework_recyclerview.presentation.fragments.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.convertor.R
import com.example.homework_recyclerview.Add

class AddButtonViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    val clickListener: () -> Unit
) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.item_btn_add_rv,
        parent,
        false
    )
) {
    private val addTextTextView = itemView.findViewById<TextView>(R.id.addText)
    private val addIconView = itemView.findViewById<ImageView>(R.id.path837)
    private val addCurrencyButton = itemView.findViewById<Button>(R.id.addButton)

    fun bind(item: Add) {
        addTextTextView.text = item.text
        addIconView.setBackgroundResource(item.flag)
        addCurrencyButton.setOnClickListener {
            clickListener()
        }

    }
}