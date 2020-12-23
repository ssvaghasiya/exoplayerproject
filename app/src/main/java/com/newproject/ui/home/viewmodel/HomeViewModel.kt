package com.newproject.ui.home.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.newproject.R
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityHomeBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.home.datamodel.*
import com.newproject.ui.home.utils.HomeDataAdapter
import com.newproject.ui.home.utils.PopUp
import com.newproject.ui.taskdetail.taskdetaildesc.view.TaskDetailsDescriptionActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newproject.ui.gallaryfragment.GallaryFragment
import com.newproject.ui.homefragment.view.HomeFragment
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


class HomeViewModel(application: Application) : BaseViewModel(application),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binder: ActivityHomeBinding
    lateinit var mContext: Context

    fun setBinder(
        binder: ActivityHomeBinding
    ) {
        this.binder = binder
        this.mContext = binder.root.context
        binder.viewModel = this
        binder.viewClickHandler = ViewClickHandler()
        binder.topbar.isTextShow = true
        binder.topbar.topBarClickListener = SlideMenuClickListener()
        init()
    }


    fun init() {
        loadFragment(HomeFragment())
        binder.navView.setNavigationItemSelectedListener(this)
        toggle = ActionBarDrawerToggle(
            mContext as Activity, binder.drawerLayout, binder.topbar.toolbarMain,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binder.drawerLayout.addDrawerListener(toggle)

    }

    fun onResume() {

    }


    inner class ViewClickHandler {

        fun onSearchClick(view: View) {
        }

        fun onCloseClick(view: View) {
        }

        fun onExploreClick(view: View) {

        }
    }


    inner class SlideMenuClickListener : TopBarClickListener {
        override fun onTopBarClickListener(view: View?, value: String?) {
            Utils.hideKeyBoard(getContext(), view!!)
            if (value.equals(getLabelText(R.string.menu))) {
                try {
//                    binder.topbar.headerNavLogo.addDrawerListener(toggle)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun onBackClicked(view: View?) {
        }
    }


    fun loadFragment(fragment: Fragment) {
        var transaction = (mContext as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> {
                loadFragment(HomeFragment())
            }
            R.id.nav_item_two -> {
                loadFragment(GallaryFragment())
            }
            R.id.nav_item_three -> showToast("Item 3 Selected")
            R.id.nav_item_four -> showToast("Item 4 Selected")
            R.id.nav_item_five -> showToast("Item 5 Selected")
            R.id.nav_item_six -> showToast("Item 6 Selected")
        }
        binder.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun onPostCreate(savedInstanceState: Bundle?) {
        toggle.syncState()
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        toggle.onConfigurationChanged(newConfig)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
    }

    fun onBackPressed(): Boolean {
        if (binder.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binder.drawerLayout.closeDrawer(GravityCompat.START)
            return true
        } else{
            return false
        }
    }

}

