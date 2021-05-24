package com.example.mymoviedatabase.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoviedatabase.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_button) {
            TODO()
        }

        if (item.itemId == R.id.about_button) {
            TODO()
        }

        if (item.itemId == R.id.settings) {
            TODO()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, SettingsFragment())
//                .addToBackStack(null)
//                .commit()
        }

        if (item.itemId == R.id.back_button) {
                supportFragmentManager.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }
}