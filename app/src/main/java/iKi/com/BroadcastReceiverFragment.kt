package iKi.com

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import iKi.com.databinding.FragmentBroadcastReceiverBinding

class BroadcastReceiverFragment : Fragment() {
    private lateinit var _binding: FragmentBroadcastReceiverBinding
    private val binding get() = _binding

    private val receiver = PackageBroadcastReceiver()
    private val intentAction = "iKi.com.action"
    private val intentFilter = IntentFilter(intentAction)
    private val intent = Intent(intentAction)

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
        triggerButton(binding.sendactionBtn)
        triggerButton(binding.broadcastBtn)
        triggerButton(binding.stoBRBtn)
    }

    private fun startBroadcast(){
        requireContext().registerReceiver(receiver, intentFilter)
    }

    private fun stopBroadcast(){
        try {
            requireContext().unregisterReceiver(receiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    private fun sendAction(){
        requireContext().sendBroadcast(intent)
    }

    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    private fun triggerButton(view: CardView){
        view.setOnTouchListener() { _: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.cardElevation = 8f
                    view.alpha = 0.5f
                    view.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(resources.getString(R.color.colorbuttonPress)))
                }
                MotionEvent.ACTION_UP -> {
                    view.cardElevation = 24f
                    view.alpha = 1f
                    view.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(resources.getString(R.color.colorbutton)))
                    when (view) {
                        binding.broadcastBtn -> startBroadcast()
                        binding.stoBRBtn -> stopBroadcast()
                        binding.sendactionBtn -> sendAction()
                    }
                }
            }
            true
        }
    }
}