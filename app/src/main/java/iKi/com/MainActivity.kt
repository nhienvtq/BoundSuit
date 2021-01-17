package iKi.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//contain nav_graph
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}