package com.newproject.ui.taskdetail.taskdetaildesc.view

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityTaskDetailsDescriptionBinding
import com.newproject.ui.taskdetail.taskdetaildesc.viewmodel.TaskDetailsDescriptionViewModel

class TaskDetailsDescriptionActivity : BaseActivity() {

    lateinit var binding: ActivityTaskDetailsDescriptionBinding
    lateinit var viewModel: TaskDetailsDescriptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_details_description)
        viewModel = ViewModelProvider(activity).get(TaskDetailsDescriptionViewModel::class.java)
        viewModel.setBinder(binding)
    }
}