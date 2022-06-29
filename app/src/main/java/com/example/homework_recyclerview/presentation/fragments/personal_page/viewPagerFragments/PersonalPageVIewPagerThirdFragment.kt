package com.example.homework_recyclerview.presentation.fragments.personal_page.viewPagerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.convertor.R

class PersonalPageVIewPagerThirdFragment : Fragment(R.layout.personal_page_viewpager_third_page) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.personal_page_viewpager_third_page, container, false)
    }

}