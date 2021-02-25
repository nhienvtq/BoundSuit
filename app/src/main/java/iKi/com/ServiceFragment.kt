package iKi.com

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.ComponentName
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import iKi.com.databinding.FragmentHomeBinding
import iKi.com.databinding.FragmentServiceBinding

class ServiceFragment : Fragment() {

    private lateinit var _binding: FragmentServiceBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = Intent(requireContext(), StartService::class.java)
        intent.putExtra("iKi","iKi service starting")
        triggerButton(binding.startServiceBtn)
        triggerButton(binding.stopServiceBtn)
    }

    private lateinit var intent: Intent

    private lateinit var mService: StartService
    private var mBound: Boolean = false

    private lateinit var serviceConnection: ServiceConnection
    private fun onServiceStart(){

        requireContext().startService(intent)

//        serviceConnection = object: ServiceConnection {
//            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//                val binder = service as StartService.LocalBinder
//                mService = binder.getService()
//                mBound = true
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//                mBound = false
//            }
//
//        }
//        requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }


    private fun onServiceStop(){

        try {
            requireContext().stopService(intent)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

//        requireContext().unbindService(serviceConnection)
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
                        binding.startServiceBtn -> onServiceStart()
                        binding.stopServiceBtn -> onServiceStop()
                    }
                }
            }
            true
        }
    }


}