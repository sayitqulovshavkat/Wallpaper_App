package com.mobil.wallpaper_app.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobil.wallpaper_app.R
import com.mobil.wallpaper_app.adapters.PagerAdapter
import com.mobil.wallpaper_app.databinding.FragmentPopularBinding
import com.mobil.wallpaper_app.databinding.ItemTabBinding
import com.mobil.wallpaper_app.room.database.AppDatabase
import com.mobil.wallpaper_app.room.entity.TopicModel

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    lateinit var pagerAdapter: PagerAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var list: List<TopicModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPopularBinding.inflate(inflater, container, false)

        appDatabase = AppDatabase.getInstance(requireContext())
        list = appDatabase.topicDao().getAllTopic()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            pagerAdapter = PagerAdapter(list,"popular", this@PopularFragment)
            binding.viewPager.adapter = pagerAdapter

            TabLayoutMediator(
                tabLayout, viewPager
            ) { tab, position ->
                val tabCount = tabLayout.tabCount
                val tabBinding = ItemTabBinding.inflate(layoutInflater)
                tabBinding.apply {
                    tv.text = list[position].title
                    if (tabCount == 0) {
                        circle.visibility = View.VISIBLE
                        tv.setTextColor(Color.WHITE)
                    } else {
                        circle.visibility = View.INVISIBLE
                        tv.setTextColor(Color.parseColor("#AFADAD"))
                    }
                    tab.customView = root
                }
            }.attach()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val itemTabBinding = ItemTabBinding.bind(tab!!.customView!!)
                    itemTabBinding.tv.setTextColor(Color.WHITE)
                    itemTabBinding.circle.visibility = View.VISIBLE
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val itemTabBinding = ItemTabBinding.bind(tab!!.customView!!)
                    itemTabBinding.tv.setTextColor(Color.parseColor("#AFADAD"))
                    itemTabBinding.circle.visibility = View.INVISIBLE
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}