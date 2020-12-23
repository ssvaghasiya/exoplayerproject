package com.newproject.ui.home.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityHomeBinding
import com.newproject.ui.home.viewmodel.HomeViewModel


class HomeActivity : BaseActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(activity).get(HomeViewModel::class.java)
        viewModel.setBinder(binding)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModel.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (viewModel.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(){
        if(!viewModel.onBackPressed()){
            super.onBackPressed()
        }
    }
}
