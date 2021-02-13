package com.newproject.ui.persondetail.view

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityPersonDetailsBinding
import com.newproject.ui.persondetail.viewmodel.PersonDetailsViewModel

class PersonDetailsActivity : BaseActivity() {

    lateinit var binding: ActivityPersonDetailsBinding
    lateinit var viewModel: PersonDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_details)
        viewModel = ViewModelProvider(activity).get(PersonDetailsViewModel::class.java)
        viewModel.setBinder(binding)
    }
}