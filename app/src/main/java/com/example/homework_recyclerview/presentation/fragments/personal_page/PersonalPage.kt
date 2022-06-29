package com.example.homework_recyclerview.presentation.fragments.personal_page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.convertor.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PersonalPage : Fragment(R.layout.layout_fragment_personal_page) {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)

        val tabLayout = view.findViewById<TabLayout>(R.id.tablayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter
        tabLayout.setTabMode(TabLayout.MODE_FIXED)
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)
        TabLayoutMediator(tabLayout, viewPager){tab, position ->

            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}