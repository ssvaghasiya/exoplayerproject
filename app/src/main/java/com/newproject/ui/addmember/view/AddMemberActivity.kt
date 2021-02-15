package com.newproject.ui.addmember.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityAddMemberBinding
import com.newproject.databinding.ActivityAddPersonBinding
import com.newproject.ui.addmember.viewmodel.AddMemberViewModel
import com.newproject.ui.addperson.viewmodel.AddPersonViewModel

class AddMemberActivity : BaseActivity() {

    lateinit var binding: ActivityAddMemberBinding
    lateinit var viewModel: AddMemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_member)
        viewModel = ViewModelProvider(activity).get(AddMemberViewModel::class.java)
        viewModel.setBinder(binding)
    }
}