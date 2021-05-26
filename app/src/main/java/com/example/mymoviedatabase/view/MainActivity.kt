package com.example.mymoviedatabase.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.view.fragments.MainFragment
import com.example.mymoviedatabase.view.fragments.SettingsFragment

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
        if (item.itemId == R.id.search) {
            Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
        }

        if (item.itemId == R.id.about_button) {
            Toast.makeText(this, "About app", Toast.LENGTH_LONG).show()
        }

        if (item.itemId == R.id.settings) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        if (item.itemId == R.id.back_button) {
                supportFragmentManager.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }
}