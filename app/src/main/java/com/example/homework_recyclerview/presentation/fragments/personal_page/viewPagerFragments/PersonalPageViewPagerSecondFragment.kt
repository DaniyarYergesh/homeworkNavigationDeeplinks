package com.example.homework_recyclerview.presentation.fragments.personal_page.viewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.example.convertor.R

class PersonalPageViewPagerSecondFragment : Fragment(R.layout.personal_page_viewpager_second_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spendedTime = view.findViewById<TextView>(R.id.spendedTime)
        spendedTime.setText("Hello World")
    }
}