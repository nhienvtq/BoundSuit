package iKi.com

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import iKi.com.R
import iKi.com.databinding.FragmentHomeBinding
import iKi.com.databinding.FragmentPhotoBinding

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        triggerButton(binding.databaseBtn, R.id.action_homeFragment_to_profileFragment)
        triggerButton(binding.viewpagerBtn, R.id.action_homeFragment_to_controlFragment)
        triggerButton(binding.RestfulBtn, R.id.action_homeFragment_to_photoFragment)
        triggerButton(binding.lifecycleBtn, R.id.action_homeFragment_to_lifecycleFragment)
        triggerButton(binding.BroadcastReceiverBtn, R.id.action_homeFragment_to_broadcastReceiverFragment)
    }
    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    private fun triggerButton(view: CardView,  naviagateActionId: Int){
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
                    findNavController().navigate(naviagateActionId)
                }
            }
            true
        }
    }
}