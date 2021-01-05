package iKi.com

import android.annotation.SuppressLint
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import iKi.com.PagerFragments.FragmentOne
import iKi.com.PagerFragments.FragmentTwo
import iKi.com.databinding.FragmentControlBinding
import kotlinx.android.synthetic.main.fragment_home.*


class ControlFragment : Fragment() {
    private lateinit var binding: FragmentControlBinding
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_control, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf<Fragment>(
            FragmentOne(),      //1
            FragmentTwo()       //2
        )
        val titleList = arrayListOf<String>(
            "Home",
            "Profile"
        )
        val iconList = arrayListOf<Int>(
            R.drawable.ic_home,
            R.drawable.ic_account
        )
        binding.viewPager.adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        tabLayout = binding.fragmentabs



        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            for (i in 0..fragmentList.size){
                tab.text = titleList[position]
                tab.setIcon(iconList[position])
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
                    TODO("Not yet implemented")
                }

            })

//        initRecyclerView()
    }


//    private fun initRecyclerView(){
//        val Re_adapter = RecyclerviewAdapter()
//        binding.dataRecyclerview.adapter = Re_adapter
//        binding.dataRecyclerview.layoutManager = LinearLayoutManager(requireContext())
//        Re_adapter.submitList(datasource.createDataSet())
//    }
}



