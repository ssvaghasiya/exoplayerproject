package com.newproject.ui.mytask.view

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityMyTaskBinding
import com.newproject.ui.mytask.viewmodel.MyTaskViewModel

class MyTaskActivity : BaseActivity() {

    lateinit var binding: ActivityMyTaskBinding
    lateinit var viewModel: MyTaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_task)
        viewModel = ViewModelProvider(activity).get(MyTaskViewModel::class.java)
        viewModel.setBinder(binding)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.onBackPressed()
    }
}