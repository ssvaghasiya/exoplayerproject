package com.newproject.ui.surname.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityGalleryBinding
import com.newproject.databinding.ActivitySurnameBinding
import com.newproject.ui.gallery.viewmodel.GalleryViewModel
import com.newproject.ui.surname.viewmodel.SurnameViewModel

class SurnameActivity : BaseActivity() {

    lateinit var binding: ActivitySurnameBinding
    lateinit var viewModel: SurnameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_surname)
        viewModel = ViewModelProvider(activity).get(SurnameViewModel::class.java)
        viewModel.setBinder(binding)
    }
}