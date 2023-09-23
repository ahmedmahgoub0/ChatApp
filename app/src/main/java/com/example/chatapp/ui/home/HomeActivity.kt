package com.example.chatapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class HomeActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        initViews()
        subscribeToLiveData()
    }

    private fun initViews() {
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this

        setupViewPager()
    }

    private fun subscribeToLiveData() {
        viewModel.eventLiveData.observe(this, ::handleEvent)
    }

    private fun handleEvent(viewEvent: HomeViewEvent?) {
        when (viewEvent) {
            HomeViewEvent.NavigateToAddRoom -> {
                navigateToAddRoom()
            }

            else -> {}
        }
    }

    private fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }


    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewBinding.content.viewPager.adapter = viewPagerAdapter
        bindTabLayout()
    }

    private fun bindTabLayout() {
        val roomsTab = viewBinding.content.tabLayout.newTab()
        roomsTab.text = "My Rooms"
        viewBinding.content.tabLayout.addTab(roomsTab)
        val browseTab = viewBinding.content.tabLayout.newTab()
        browseTab.text = "Browse"
        viewBinding.content.tabLayout.addTab(browseTab)

        viewBinding.content.tabLayout
            .addOnTabSelectedListener(object :
                OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewBinding.content.viewPager.currentItem = tab!!.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            }
            )
        viewBinding.content.viewPager
            .registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val selectedTab = viewBinding.content.tabLayout.getTabAt(position)
                    viewBinding.content.tabLayout.selectTab(selectedTab)
                }
            })
    }

}