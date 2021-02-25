package iKi.com

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import iKi.com.databinding.FragmentBluetoothBinding
import iKi.com.databinding.FragmentHomeBinding

class BluetoothFragment : Fragment() {

    private lateinit var _binding: FragmentBluetoothBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bluetooth, container, false)
        return binding.root
    }

    private lateinit var  bluetoothAdapter: BluetoothAdapter
    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter.isEnabled){
            binding.bluetoothSwitch.isChecked = true
            binding.bluetoothimageView.alpha = 1.0f
            binding.bluetoothSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_bluetooth)
        } else {
            binding.bluetoothSwitch.isChecked = false
            binding.bluetoothimageView.alpha = 0.5f
            binding.bluetoothSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_bluetooth_off)
        }

        binding.bluetoothimageView.setOnClickListener(){
            if(!binding.bluetoothSwitch.isChecked){
                binding.bluetoothSwitch.isChecked = !binding.bluetoothSwitch.isChecked
            } else {
                if (bluetoothAdapter.isEnabled){
                    val paireDevices = bluetoothAdapter.bondedDevices
                    val devicesList = ArrayList<String>()
                    for (bluetoothDevices: BluetoothDevice in paireDevices){
                        devicesList.add(bluetoothDevices.name + " " + bluetoothDevices.address)
                    }
                    binding.pairedListview.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, devicesList)
                }
            }

        }
        binding.bluetoothSwitch.setOnCheckedChangeListener(){ compoundButton: CompoundButton, b: Boolean ->
            if (binding.bluetoothSwitch.isChecked){
                binding.bluetoothimageView.alpha = 1.0f
                binding.bluetoothSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_bluetooth)
                val turnOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(turnOn,0)
            } else {
                binding.bluetoothimageView.alpha = 0.5f
                binding.bluetoothSwitch.thumbDrawable = resources.getDrawable(R.drawable.ic_bluetooth_off)
                bluetoothAdapter.disable()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1){
            if (bluetoothAdapter.startDiscovery()){
                Toast.makeText(requireContext(), "Device is visible", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}