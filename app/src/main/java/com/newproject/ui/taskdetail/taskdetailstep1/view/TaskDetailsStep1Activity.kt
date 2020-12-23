package com.newproject.ui.taskdetail.taskdetailstep1.view

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityTaskDetailsBinding
import com.newproject.ui.taskdetail.taskdetailstep1.viewmodel.TaskDetailsStep1ViewModel

class TaskDetailsStep1Activity : BaseActivity() {

    lateinit var binding: ActivityTaskDetailsBinding
    lateinit var viewModel: TaskDetailsStep1ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_details)
        viewModel = ViewModelProvider(activity).get(TaskDetailsStep1ViewModel::class.java)
        viewModel.setBinder(binding)
    }
}