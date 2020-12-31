package com.newproject.ui.temp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivitySurnameContactsBinding
import com.newproject.databinding.ActivityTempBinding
import com.newproject.ui.surnamecontacts.viewmodel.SurnameContactsViewModel
import com.newproject.ui.temp.viewmodel.TempViewModel

class TempActivity : BaseActivity() {

    lateinit var viewModel: TempViewModel
    lateinit var binding: ActivityTempBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_temp)
        viewModel = ViewModelProvider(activity).get(TempViewModel::class.java)
        viewModel.setBinder(binding)
    }
}