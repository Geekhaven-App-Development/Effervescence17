package org.effervescence.app17.activities

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.effervescence.app17.R
import org.effervescence.app17.fragments.*

class MainActivity : AppCompatActivity() {

    private var currentVal = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if(currentVal == item.itemId){
            return@OnNavigationItemSelectedListener true
        }
        currentVal = item.itemId
        when (item.itemId) {

            R.id.navigation_home -> {
                switchFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_schedule -> {
                switchFragment(EventsViewFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchFragment(UpdatesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_proshows -> {
                switchFragment(ProShowsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                switchFragment(InfoFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        val backStackEntry = supportFragmentManager.backStackEntryCount
        if (backStackEntry > 0) {
            for (i in 0 until backStackEntry) {
                supportFragmentManager.popBackStackImmediate()
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

        currentVal = R.id.navigation_home
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun onResume() {
        super.onResume()
        val backStackEntry = supportFragmentManager.backStackEntryCount
        if (backStackEntry > 0) {
            for (i in 0 until backStackEntry) {
                supportFragmentManager.popBackStackImmediate()
            }
        }
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
