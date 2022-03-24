package iKi.com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import iKi.com.databinding.FragmentCustomizeViewBinding

class CustomizeViewFragment: Fragment() {
    private lateinit var _binding: FragmentCustomizeViewBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customize_view, container, false)
        return binding.root
    }
}