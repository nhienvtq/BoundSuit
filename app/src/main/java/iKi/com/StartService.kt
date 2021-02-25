package iKi.com

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.Parcelable
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
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.musicmedia)
            mediaPlayer.isLooping = true
        }
        mediaPlayer.start()

        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        Toast.makeText(this, "onUnbind", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }
}