package com.brandon.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.brandon.parstagram.fragments.ComposeFragment
import com.brandon.parstagram.fragments.FeedFragment
import com.brandon.parstagram.fragments.ProfileFragment
import com.brandon.parstagram.fragments.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->

            var fragmentToShow: Fragment? = null
            when(item.itemId){
                R.id.action_home -> {
                    fragmentToShow = FeedFragment()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
                R.id.action_log_out -> {
                    onLogOut()
                }
                R.id.action_setting -> {
                    fragmentToShow = SettingFragment()
                }
            }

            if(fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true
        }

        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home

    }

    companion object{
        const val TAG = "MainActivity"
    }

    fun onLogOut(){
        ParseUser.logOut()
        val currentUser = ParseUser.getCurrentUser()
        if(currentUser != null){
            //some problem happened
            Toast.makeText(this, "Error in Logging out", Toast.LENGTH_SHORT).show()
        }else{
            goToLoginActivity()
        }
    }

    private  fun goToLoginActivity(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}