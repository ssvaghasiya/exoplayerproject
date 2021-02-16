package com.newproject.ui.ads.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityAdsBinding
import com.newproject.databinding.ActivityTempBinding
import com.newproject.ui.ads.viewmodel.AdsViewModel
import com.newproject.ui.temp.viewmodel.TempViewModel

class AdsActivity : BaseActivity() {

    lateinit var viewModel: AdsViewModel
    lateinit var binding: ActivityAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ads)
        viewModel = ViewModelProvider(activity).get(AdsViewModel::class.java)
        viewModel.setBinder(binding)
    }
}