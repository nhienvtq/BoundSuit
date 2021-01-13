package iKi.com.PagerFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import iKi.com.R
import iKi.com.databinding.FragmentProfileBinding
import iKi.com.profileData.OnProfileItemClickListener
import iKi.com.profileData.Profile
import iKi.com.profileData.ProfileViewModel
import iKi.com.profileData.profileRecyclerViewAdapter

class ProfileFragment : Fragment(), OnProfileItemClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var optionBtnClick = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_minus90deg)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_plus90deg)}
    private val PopOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_popup)}
    private val PopClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_popup)}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingOptButton.setOnClickListener(){
            if (!optionBtnClick){
                OptBtnOpen()
            } else {
                OptBtnClose()
            }
        }
        binding.floatingAddButton.setOnClickListener(){
            findNavController().navigate(R.id.action_controlFragment_to_addProfileFragment)
            binding.floatingAddButton.visibility = View.INVISIBLE
            binding.floatingDelButton.visibility = View.INVISIBLE
            optionBtnClick = false
        }
        binding.floatingDelButton.setOnClickListener(){
            insProfileViewModel.delAllProfile()
        }
        initRecyclerView()
    }
    private fun OptBtnOpen()
    {
        optionBtnClick = true

        binding.floatingAddButton.visibility = View.VISIBLE
        binding.floatingDelButton.visibility = View.VISIBLE

        binding.floatingAddButton.startAnimation(PopOpen)
        binding.floatingDelButton.startAnimation(PopOpen)
        binding.floatingOptButton.startAnimation(rotateOpen)

        binding.floatingAddButton.isClickable = true
        binding.floatingDelButton.isClickable = true
    }
    private fun OptBtnClose()
    {
        optionBtnClick = false

        binding.floatingAddButton.visibility = View.INVISIBLE
        binding.floatingDelButton.visibility = View.INVISIBLE

        binding.floatingAddButton.startAnimation(PopClose)
        binding.floatingDelButton.startAnimation(PopClose)
        binding.floatingOptButton.startAnimation(rotateClose)

        binding.floatingAddButton.isClickable = false
        binding.floatingDelButton.isClickable = false

    }

    override fun onPause() {
        binding.floatingAddButton.visibility = View.INVISIBLE
        binding.floatingDelButton.visibility = View.INVISIBLE
        optionBtnClick = false

        super.onPause()

    }

    private lateinit var insProfileViewModel: ProfileViewModel
    private lateinit var profile_adapter: profileRecyclerViewAdapter
    private fun initRecyclerView(){
        profile_adapter = profileRecyclerViewAdapter( this)
        binding.profileRecyclerview.adapter = profile_adapter
        binding.profileRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        //viewModel
        insProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        insProfileViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            profile -> profile_adapter.setData(profile, requireContext())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: Profile, position: Int) {
            val profile = Profile(
                item.id,"",0,"","",
                "","","","")
        insProfileViewModel.delProfile(profile)
        profile_adapter = profileRecyclerViewAdapter( this)
        binding.profileRecyclerview.adapter = profile_adapter
    }
}