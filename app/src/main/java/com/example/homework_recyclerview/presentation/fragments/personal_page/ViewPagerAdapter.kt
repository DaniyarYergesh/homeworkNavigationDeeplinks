package com.example.homework_recyclerview.presentation.fragments.personal_page

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homework_recyclerview.presentation.fragments.personal_page.viewPagerFragments.PersonalPageViewPagerFirstFragment
import com.example.homework_recyclerview.presentation.fragments.personal_page.viewPagerFragments.PersonalPageViewPagerSecondFragment
import com.example.homework_recyclerview.presentation.fragments.personal_page.viewPagerFragments.PersonalPageVIewPagerThirdFragment

class ViewPagerAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {

    private val fragments = mutableListOf(
        PersonalPageViewPagerFirstFragment(), PersonalPageViewPagerSecondFragment(), PersonalPageVIewPagerThirdFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getPageTitle(position: Int): CharSequence{
         return when(fragments[position]){
            is PersonalPageViewPagerFirstFragment -> "Основные"
            is PersonalPageViewPagerSecondFragment -> "Статистика"
            else -> "Еще один таб"
        }
    }
}