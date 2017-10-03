package org.effervescence.app17.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.effervescence.app17.R
import org.effervescence.app17.fragments.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_schedule -> {
                switchFragment(DaysViewFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                // Temporary: FIX IT
                switchFragment(SponsorFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_proshows -> {
                switchFragment(ProShowsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                switchFragment(UpdatesFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.containerFrame, HomeFragment())
                .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun goFullScreen(){
        val decor = window.decorView
        var flags = decor.systemUiVisibility
        flags = flags or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        decor.systemUiVisibility = flags
    }

    private fun switchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerFrame,  fragment)
                .commit()
    }
}
