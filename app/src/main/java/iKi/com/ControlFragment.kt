package iKi.com

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import iKi.com.PagerFragments.*
import iKi.com.databinding.FragmentControlBinding
import kotlinx.android.synthetic.main.fragment_control.*


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
            HomeFragment(),                     //1
            ProfileFragment(),                  //2
            MessageFragment(),                  //3
            NotificationFragment(),             //4
            PhotoFragment(),                    //5
            VideoFragment(),                    //6

        )
        val icontitleList = arrayListOf<iconBinding>(
            iconBinding("Home", R.drawable.ic_home),            //1
            iconBinding("Profile", R.drawable.ic_account),      //2
            iconBinding("Message", R.drawable.ic_chat),         //3
            iconBinding("Notification", R.drawable.ic_noti),    //4
            iconBinding("Photo", R.drawable.ic_photo),          //5
            iconBinding("Live", R.drawable.ic_video),          //6
        )

        binding.viewPager.adapter = ViewPagerAdapter(
            fragmentList,requireActivity().supportFragmentManager,lifecycle
        )

        tabLayout = binding.fragmentabs

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

class iconBinding(var title: String, var icon: Int){
    @JvmName("getTitle1")
    fun getTitle():String = title
    @JvmName("getIcon1")
    fun getIcon():Int = icon
}



