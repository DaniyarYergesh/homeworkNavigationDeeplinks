package com.example.homework_recyclerview.domain.repository

import androidx.annotation.DrawableRes
import com.example.homework_recyclerview.domain.repository.Parent

data class Currency(
    override val id: Int,
    var text: Double,
    val type: String,
    @DrawableRes val flag: Int,
    val course: Double
): Parent