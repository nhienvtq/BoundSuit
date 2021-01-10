package iKi.com.PagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import iKi.com.Decoration.TopPaddingDecoration
import iKi.com.R
import iKi.com.databinding.FragmentProfileBinding
import iKi.com.profileData.ProfileViewModel
import iKi.com.profileData.profileRecyclerViewAdapter

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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
        binding.floatingAddButton.setOnClickListener(){
            findNavController().navigate(R.id.action_controlFragment_to_addProfileFragment)
        }
        binding.floatingDelButton.setOnClickListener(){
            insProfileViewModel.delAllProfile()
        }
        initRecyclerView()
    }

    private lateinit var insProfileViewModel: ProfileViewModel
    private fun initRecyclerView(){
        val profile_adapter = profileRecyclerViewAdapter()
        binding.profileRecyclerview.adapter = profile_adapter
        binding.profileRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val TopPaddingDecoration = TopPaddingDecoration(30)
        binding.profileRecyclerview.addItemDecoration(TopPaddingDecoration)
        //viewModel
        insProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        insProfileViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            profile -> profile_adapter.setData(profile)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}