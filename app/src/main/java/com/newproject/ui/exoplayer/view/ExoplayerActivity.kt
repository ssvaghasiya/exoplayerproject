package com.newproject.ui.exoplayer.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityExoplayerBinding
import com.newproject.databinding.ActivityHomeBinding
import com.newproject.ui.exoplayer.viewmodel.ExoplayerViewModel
import com.newproject.ui.home.viewmodel.HomeViewModel

class ExoplayerActivity : BaseActivity() {

    lateinit var binding: ActivityExoplayerBinding
    lateinit var viewModel: ExoplayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exoplayer)
        viewModel = ViewModelProvider(activity).get(ExoplayerViewModel::class.java)
        viewModel.setBinder(binding)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.onConfigurationChanged(newConfig)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.onRestart()
    }
}