package iKi.com

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import iKi.com.databinding.FragmentLifecycleBinding


class LifecycleFragment : Fragment() {
    private lateinit var _binding: FragmentLifecycleBinding
    private val binding get() = _binding

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_lifecycle,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notifyTextView.setOnClickListener(){
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
        }
    }

    private fun notifyAlert(title: String, content: String){
        val channelId = resources.getString(R.string.channel_name)
        val description = resources.getString(R.string.channel_description)
        notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(requireContext(), DisplayNotificacionActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(),0,intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId,description,
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(requireContext(), channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_chat)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.ic_home
                    )
                )
                .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(requireContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_chat)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        requireActivity().resources,
                        R.drawable.ic_home
                    )
                )
                .setContentIntent(pendingIntent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES. JELLY_BEAN){
            notificationManager.notify(1234, builder.build())
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notifyAlert("Create", "Notify onCreate")
    }

    override fun onStart() {
        super.onStart()
        notifyAlert("Start", "Notify onStart")
    }

    override fun onResume() {
        super.onResume()
        notifyAlert("Resume", "Notify onResume")

    }

    override fun onPause() {
        super.onPause()
        notifyAlert("Pause", "Notify onPause")
    }

    override fun onStop() {
        super.onStop()
        notifyAlert("Stop", "Notify onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        notifyAlert("Destroy", "Notify onDestroy")
    }


}