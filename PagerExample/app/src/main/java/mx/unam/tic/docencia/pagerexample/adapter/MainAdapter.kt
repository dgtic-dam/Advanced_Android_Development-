package mx.unam.tic.docencia.pagerexample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

const val BEHAVIOR_SET_USER_VISIBLE_HINT=0

class MainAdapter(val titlesPage:ArrayList<String>, val fragments:ArrayList<Fragment>,fm:FragmentManager):

    // FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) { //3 elementos


    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) { // Más de 3 elementos

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titlesPage[position]
    }
}