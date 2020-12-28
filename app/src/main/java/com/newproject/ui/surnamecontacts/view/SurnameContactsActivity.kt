package com.newproject.ui.surnamecontacts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivitySurnameBinding
import com.newproject.databinding.ActivitySurnameContactsBinding
import com.newproject.ui.surname.viewmodel.SurnameViewModel
import com.newproject.ui.surnamecontacts.viewmodel.SurnameContactsViewModel

class SurnameContactsActivity : BaseActivity() {

    lateinit var binding: ActivitySurnameContactsBinding
    lateinit var viewModel: SurnameContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surname_contacts)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_surname)
        viewModel = ViewModelProvider(activity).get(SurnameContactsViewModel::class.java)
        viewModel.setBinder(binding)
    }
}