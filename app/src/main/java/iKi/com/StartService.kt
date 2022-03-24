package iKi.com

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaPlayer
import android.os.*
import android.widget.Toast
import java.util.*

class StartService: Service() {

    private lateinit var mediaPlayer: MediaPlayer

    // Binder given to clients
    private val binder = LocalBinder()
    // Random number generator
    private val mGenerator = Random()
    /** method for clients  */
    val randomNumber: Int
        get() = mGenerator.nextInt(100)
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): StartService = this@StartService
    }

    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show()
        return null
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.musicmedia)
        mediaPlayer.isLooping = true
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var toastString = ""
        if (intent != null){
            toastString = intent.getStringExtra("iKi").toString()
        }
        Toast.makeText(this,"onStartCommand: " + toastString, Toast.LENGTH_SHORT).show()
        mediaPlayer.start()

//        notifyAlert("start service", "background service is running")


        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "onUnbind", Toast.LENGTH_SHORT).show()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private fun notifyAlert(title: String, content: String){
        val channelId = resources.getString(R.string.channel_name)
        val description = resources.getString(R.string.channel_description)
        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, DisplayNotificacionActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId,description,
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_chat)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_home
                    )
                )
                .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_chat)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_home
                    )
                )
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())

    }
}