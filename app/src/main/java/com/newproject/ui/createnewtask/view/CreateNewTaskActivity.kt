package com.newproject.ui.createnewtask.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityCreateNewTaskBinding
import com.newproject.ui.createnewtask.viewmodel.CreateNewTaskViewModel

class CreateNewTaskActivity : BaseActivity() {

    lateinit var binding: ActivityCreateNewTaskBinding
    lateinit var viewModel: CreateNewTaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_task)
        viewModel = ViewModelProvider(activity).get(CreateNewTaskViewModel::class.java)
        viewModel.setBinder(binding)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }
}