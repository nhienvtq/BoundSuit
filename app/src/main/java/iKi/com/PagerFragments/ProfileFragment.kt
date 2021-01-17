package iKi.com.PagerFragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
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

//Display - Create - Delete - Update profile Room database
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingOptButton.setOnClickListener(){
            if (!optionBtnClick){
                OptBtnOpen()
            } else {
                OptBtnClose()
            }
        }
        //navigate to add fragment
        binding.floatingAddButton.setOnClickListener(){
            findNavController().navigate(R.id.action_controlFragment_to_addProfileFragment)
            binding.floatingAddButton.visibility = View.INVISIBLE
            binding.floatingDelButton.visibility = View.INVISIBLE
            optionBtnClick = false
        }
        //delete all data
        binding.floatingDelButton.setOnClickListener(){
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Delete all profile?")
            builder.setPositiveButton("Delete"){_, _ ->
                insProfileViewModel.delAllProfile()
                Toast.makeText(context, "Profiles deleted", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel"){ _, _  ->}
            builder.create().show()
        }
        //initialize profile RecyclerView
        initRecyclerView()
    }

    private fun OptBtnOpen(){
        optionBtnClick = true
        binding.floatingAddButton.visibility = View.VISIBLE
        binding.floatingDelButton.visibility = View.VISIBLE

        val PopOpen = AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_popup)
        binding.floatingAddButton.startAnimation(PopOpen)
        binding.floatingDelButton.startAnimation(PopOpen)

        val rotateOpen = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_minus90deg)
        binding.floatingOptButton.startAnimation(rotateOpen)

        binding.floatingAddButton.isClickable = true
        binding.floatingDelButton.isClickable = true
    }
    private fun OptBtnClose(){
        optionBtnClick = false
        binding.floatingAddButton.visibility = View.INVISIBLE
        binding.floatingDelButton.visibility = View.INVISIBLE

        val PopClose = AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_popup)
        binding.floatingAddButton.startAnimation(PopClose)
        binding.floatingDelButton.startAnimation(PopClose)

        val rotateClose = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_plus90deg)
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

    //Handle get position of clicked RecyclerView - delete the selected data
    override fun onItemClick(item: Profile, position: Int) {
        val profile = Profile(item.id,"",0,"","",
            "","","","")
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Delete this profile?")
        builder.setPositiveButton("Delete"){_, _ ->
            insProfileViewModel.delProfile(profile)
            Toast.makeText(context, "Profile deleted", Toast.LENGTH_SHORT).show()
            profile_adapter = profileRecyclerViewAdapter( this)
            binding.profileRecyclerview.adapter = profile_adapter
        }
        builder.setNegativeButton("Cancel"){ _, _  ->}
        builder.create().show()
    }
}