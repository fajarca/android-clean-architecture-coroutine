package io.fajarca.home.presentation.container


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import io.fajarca.home.R
import io.fajarca.home.databinding.FragmentHomeContainerBinding
import io.fajarca.home.presentation.HomeFragment


class HomeFragmentContainer : Fragment() {

    private lateinit var binding : FragmentHomeContainerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_container, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initTabs()
    }

    private fun initTabs() {
        val viewPager = binding.viewpager

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(HomeFragment(), "Regional")
       /* adapter.addFragment(HomeFragment(), "Business")
        adapter.addFragment(HomeFragment(), "Entertainment")
        adapter.addFragment(HomeFragment(), "General")
        adapter.addFragment(HomeFragment(), "Health")
        adapter.addFragment(HomeFragment(), "Science")
        adapter.addFragment(HomeFragment(), "Technology")*/
        viewPager.adapter = adapter

        val tabLayout = binding.tabs
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    inner class ViewPagerAdapter(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }

}
