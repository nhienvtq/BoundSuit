package iKi.com

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast


class NetworkBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (networkInfo!!.isConnected){
            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "disconnected", Toast.LENGTH_SHORT).show()
        }
    }
}