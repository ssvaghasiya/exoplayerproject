package com.newproject.ui.memberdetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityAdsBinding
import com.newproject.databinding.ActivityMemberDetailsBinding
import com.newproject.ui.ads.viewmodel.AdsViewModel
import com.newproject.ui.memberdetails.viewmodel.MemberDetailsViewModel

class MemberDetailsActivity : BaseActivity() {

    lateinit var viewModel: MemberDetailsViewModel
    lateinit var binding: ActivityMemberDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member_details)
        viewModel = ViewModelProvider(activity).get(MemberDetailsViewModel::class.java)
        viewModel.setBinder(binding)
    }
}