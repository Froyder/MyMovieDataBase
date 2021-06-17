package com.example.mymoviedatabase.view

import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoviedatabase.MainBroadcastReceiver
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.view.fragments.*

class MainActivity : AppCompatActivity() {

    private val receiver = MainBroadcastReceiver()

    companion object {
        fun newInstance() = MainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(receiver, IntentFilter(CONNECTIVITY_ACTION))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
        }

        if (item.itemId == R.id.about_button) {
            Toast.makeText(this, "About app", Toast.LENGTH_LONG).show()
        }

        if (item.itemId == R.id.settings) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment())
                .addToBackStack("")
                    .commitAllowingStateLoss()
        }

        if (item.itemId == R.id.back_button) {
            supportFragmentManager.popBackStack()
        }

        if (item.itemId == R.id.history) {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HistoryFragment())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
        }
        if (item.itemId == R.id.menu_content_provider) {
            supportFragmentManager.apply {
                beginTransaction()
                    .replace(R.id.container, ContentProviderFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
            true
        }

        if (item.itemId == R.id.menu_google_maps) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GoogleMapsFragment())
                .addToBackStack("")
                .commitAllowingStateLoss()
        }

        return super.onOptionsItemSelected(item)
    }
}