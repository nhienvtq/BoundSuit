package iKi.com

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import iKi.com.databinding.FragmentWifiBinding

class WifiFragment : Fragment() {

    private lateinit var _binding: FragmentWifiBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wifi, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager.isWifiEnabled){
            binding.wifiSwitch.isChecked = true
            binding.wifiimageView.alpha = 1.0f
            binding.wifiSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_wifi)
        } else {
            binding.wifiSwitch.isChecked = false
            binding.wifiimageView.alpha = 0.5f
            binding.wifiSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_wifi_off)
        }

        binding.wifiimageView.setOnClickListener(){
            binding.wifiSwitch.isChecked = !binding.wifiSwitch.isChecked
        }
        binding.wifiSwitch.setOnCheckedChangeListener(){ compoundButton: CompoundButton, b: Boolean ->
            if (binding.wifiSwitch.isChecked){
                wifiManager.isWifiEnabled = true
                binding.wifiimageView.alpha = 1.0f
                binding.wifiSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_wifi)
            } else {
                wifiManager.isWifiEnabled = false
                binding.wifiimageView.alpha = 0.5f
                binding.wifiSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_wifi_off)
            }
        }
    }
}