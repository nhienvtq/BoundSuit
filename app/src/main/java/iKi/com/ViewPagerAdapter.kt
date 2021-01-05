package iKi.com

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    listFragment: ArrayList<Fragment>,
    fragmentmanager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentmanager, lifecycle) {

    private val fragmentList = listFragment

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}