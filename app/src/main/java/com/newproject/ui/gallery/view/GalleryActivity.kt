package com.newproject.ui.gallery.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityGalleryBinding
import com.newproject.databinding.ActivityHomeBinding
import com.newproject.ui.gallery.viewmodel.GalleryViewModel
import com.newproject.ui.home.viewmodel.HomeViewModel

class GalleryActivity : BaseActivity() {

    lateinit var binding: ActivityGalleryBinding
    lateinit var viewModel: GalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        viewModel = ViewModelProvider(activity).get(GalleryViewModel::class.java)
        viewModel.setBinder(binding)
    }
}