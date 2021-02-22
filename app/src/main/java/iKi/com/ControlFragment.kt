package iKi.com

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import iKi.com.Pagers.*
import iKi.com.databinding.FragmentControlBinding


class ControlFragment : Fragment() {
    private var _binding: FragmentControlBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_control, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf<Fragment>(
            Pager1Fragment(),                       //1
            Pager2Fragment(),                       //2
            Pager3Fragment(),                       //3
            Pager4Fragment(),                       //4
            Pager5Fragment(),                       //5
            Pager6Fragment()                        //6
        )
        val icontitleList = arrayListOf<iconBinding>(
            iconBinding("Pager 1", R.drawable.ic_home) ,        //1
            iconBinding("Pager 2", R.drawable.ic_account),      //2
            iconBinding("Pager 3", R.drawable.ic_chat),         //3
            iconBinding("Pager 4", R.drawable.ic_noti),         //4
            iconBinding("Pager 5", R.drawable.ic_photo),        //5
            iconBinding("Pager 6", R.drawable.ic_video)         //6
        )

        binding.viewPager.adapter = ViewPagerAdapter(
            fragmentList,requireActivity().supportFragmentManager,lifecycle
        )

        tabLayout = binding.fragmentabs
        viewPager = binding.viewPager
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            for (i in 0..fragmentList.size){
                tab.text = icontitleList[position].getTitle()
                tab.setIcon(icontitleList[position].getIcon())
            }
        }.attach()
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.alpha = 255
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.alpha = 50
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                //doing nothing
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class iconBinding(var title: String, var icon: Int){
    @JvmName("getTitle1")
    fun getTitle():String = title
    @JvmName("getIcon1")
    fun getIcon():Int = icon
}



