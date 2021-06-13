package ru.iskhakoff.rarible_app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class AuthViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val fragmentsList = mutableListOf<Fragment>()

    override fun getItemCount(): Int = fragmentsList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }

    fun addFragment(fragment: Fragment) {
        fragmentsList.add(fragment)
    }
}