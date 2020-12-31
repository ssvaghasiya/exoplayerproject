package com.newproject.ui.homefragment.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.newproject.R
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.FragmentHomeBinding
import com.newproject.ui.gallery.view.GalleryActivity
import com.newproject.ui.homefragment.utils.GridDecore
import com.newproject.ui.homefragment.utils.HomeAdapter
import com.newproject.ui.homefragment.utils.HomeData
import com.newproject.ui.homefragment.view.HomeFragment
import com.newproject.ui.surname.view.SurnameActivity
import com.newproject.ui.surname.viewmodel.SurnameViewModel
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener

class HomeFragmentViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: FragmentHomeBinding
    private lateinit var mContext: Context
    lateinit var adapter: HomeAdapter

    var sampleImages = intArrayOf(
        R.drawable.image_4,
        R.drawable.image_4,
        R.drawable.image_4,
        R.drawable.image_4,
        R.drawable.image_4
    )

    var dataList: MutableList<HomeData> = mutableListOf()

    fun setBinder(binder: FragmentHomeBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        init()
    }

    private fun init() {
//        dataList.clear()
//        dataList.add(HomeData(R.drawable.gallary, mContext.getString(R.string.gallery)))
//        dataList.add(HomeData(R.drawable.users, mContext.getString(R.string.users)))
//        dataList.add(HomeData(R.drawable.ads1, mContext.getString(R.string.advertise)))
//        dataList.add(HomeData(R.drawable.contact1, mContext.getString(R.string.contact)))
//        adapter = HomeAdapter(mContext)
//        adapter.addAll(dataList)
//        binde
//        r.recyclerViewHome.adapter = adapter
//        binder.recyclerViewHome.layoutManager =
//            GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false)
//        binder.recyclerViewHome.addItemDecoration(GridDecore())
//        adapter.setEventListener(object : HomeAdapter.EventListener {
//            override fun onItemClick(pos: Int) {
//                if (pos == 0) {
//                    var intent: Intent = Intent(mContext, GalleryActivity::class.java)
//                    mContext.startActivity(intent)
//                } else if(pos == 1){
//                    var intent: Intent = Intent(mContext, SurnameActivity::class.java)
//                    mContext.startActivity(intent)
//                }
//            }
//        })
        binder.carouselViewHomefrag.setImageListener(imageListener)
        binder.carouselViewHomefrag.setImageClickListener(imageClickListener)
        binder.carouselViewHomefrag.pageCount = sampleImages.size

    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            imageView?.setImageResource(sampleImages[position])
        }
    }


    var imageClickListener: ImageClickListener = object : ImageClickListener {
        override fun onClick(position: Int) {
//            Toast.makeText(mContext, "Carousel $position", Toast.LENGTH_SHORT).show()
        }
    }

    inner class ViewClickHandler {
        fun onGallery(view: View) {
            try {
                var intent = Intent(mContext, GalleryActivity::class.java)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onContact(view: View) {
            try {
                var intent = Intent(mContext, SurnameActivity::class.java)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}