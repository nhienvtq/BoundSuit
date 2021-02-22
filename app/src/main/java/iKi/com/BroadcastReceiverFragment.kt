package iKi.com

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import iKi.com.databinding.FragmentBroadcastReceiverBinding

class BroadcastReceiverFragment : Fragment() {
    private lateinit var _binding: FragmentBroadcastReceiverBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_broadcast_receiver,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    val receiver = NetworkChangeReceiver()
    val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    override fun onStart() {
        super.onStart()
        requireContext().registerReceiver(receiver, filter)
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(receiver)
    }
}