package com.newproject.ui.taskdetail.taskdetailstep2.view

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityTaskDetailStep2Binding
import com.newproject.ui.taskdetail.taskdetailstep2.viewmodel.TaskDetailsStep2ViewModel

class TaskDetailStep2Activity : BaseActivity() {

    lateinit var binding: ActivityTaskDetailStep2Binding
    lateinit var viewModel: TaskDetailsStep2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_detail_step2)
        viewModel = ViewModelProvider(activity).get(TaskDetailsStep2ViewModel::class.java)
        viewModel.setBinder(binding)
    }
}