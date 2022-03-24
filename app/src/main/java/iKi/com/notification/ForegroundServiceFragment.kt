package iKi.com.notification

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import iKi.com.R
import iKi.com.databinding.FragmentForegroundServiceBinding

class ForegroundServiceFragment: Fragment() {

    private lateinit var _binding: FragmentForegroundServiceBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_foreground_service, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startBtn.setOnClickListener{
//          requireContext().startService(Intent(requireContext(), FService::class.java))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requireContext().startForegroundService(
                    Intent(
                        requireContext(),
                        FService::class.java
                    )
                )
            } else {
                requireContext().startService(
                    Intent(
                        requireContext(),
                        FService::class.java
                    )
                )
            }
        }
        binding.stopBtn.setOnClickListener{
            requireContext().stopService(Intent(requireContext(), FService::class.java))
        }
    }
}