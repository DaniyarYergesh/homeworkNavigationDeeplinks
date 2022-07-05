package com.example.homework_recyclerview.domain.repository

import androidx.annotation.DrawableRes
import com.example.homework_recyclerview.domain.repository.Parent

data class Add(
    override val id: Int,
    val text: String,
    @DrawableRes val flag: Int
): Parent
