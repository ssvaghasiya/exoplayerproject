package com.newproject.ui.clients.view

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.newproject.R
import com.newproject.base.view.BaseActivity
import com.newproject.databinding.ActivityClientsBinding
import com.newproject.ui.clients.viewmodel.ClientsViewModel

class ClientsActivity : BaseActivity() {
    lateinit var binding: ActivityClientsBinding
    lateinit var viewModel: ClientsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clients)
        viewModel = ViewModelProvider(activity).get(ClientsViewModel::class.java)
        viewModel.setBinder(binding)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.onBackPressed()
    }
}