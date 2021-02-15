package com.newproject.ui.addperson.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityAddPersonBinding
import com.newproject.databinding.ActivitySurnameBinding
import com.newproject.ui.addperson.viewmodel.AddPersonViewModel
import com.newproject.ui.surname.viewmodel.SurnameViewModel

class AddPersonActivity : BaseActivity() {

    lateinit var binding: ActivityAddPersonBinding
    lateinit var viewModel: AddPersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_person)
        viewModel = ViewModelProvider(activity).get(AddPersonViewModel::class.java)
        viewModel.setBinder(binding)
    }
}