package org.effervescence.app17.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.view.*

import org.effervescence.app17.R

import java.util.ArrayList

/**
 * Created by sashank on 3/10/17.
 */

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(TeamFragment())
        fragmentList.add(DeveloperFragment())
        fragmentList.add(SponsorFragment())
        fragmentList.add(AboutUsFragment())

        val mPagerAdapter = InfoFragmentAdapter(activity.supportFragmentManager, fragmentList)
        view.pager.adapter = mPagerAdapter
        view.tab_layout.setupWithViewPager(view.pager)

    }

    private inner class InfoFragmentAdapter internal constructor(fragmentManager: FragmentManager, fragmentList: List<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {

        private val titles = arrayOf("TEAM", "DEVELOPERS", "SPONSORS", "ABOUT")
        internal var fragments: MutableList<Fragment> = ArrayList()

        init {
            fragments.addAll(fragmentList)
        }

        override fun getItem(position: Int): Fragment {
            Log.e("FRAGMENT", Integer.toString(position))
            return fragments[position]
        }

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence = titles[position]
    }
}
