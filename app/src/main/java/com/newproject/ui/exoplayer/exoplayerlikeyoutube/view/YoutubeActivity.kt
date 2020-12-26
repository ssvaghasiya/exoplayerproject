package com.newproject.ui.exoplayer.exoplayerlikeyoutube.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityExoplayerBinding
import com.newproject.databinding.ActivityYoutubeBinding
import com.newproject.ui.exoplayer.exoplayerlikeyoutube.viewmodel.YoutubeViewModel
import com.newproject.ui.exoplayer.viewmodel.ExoplayerViewModel

class YoutubeActivity : BaseActivity() {

    lateinit var binding: ActivityYoutubeBinding
    lateinit var viewModel: YoutubeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.screen_bg)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_youtube)
        viewModel = ViewModelProvider(activity).get(YoutubeViewModel::class.java)
        viewModel.setBinder(binding)
    }
}