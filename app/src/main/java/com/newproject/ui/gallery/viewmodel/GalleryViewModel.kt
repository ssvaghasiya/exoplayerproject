package com.newproject.ui.gallery.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.firebase.firestore.Query
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityGalleryBinding
import com.newproject.ui.exoplayer.exoplayerlikeyoutube.utils.VideoAdapter
import com.newproject.ui.exoplayer.exoplayerlikeyoutube.utils.VideoData
import com.newproject.ui.exoplayer.view.ExoplayerActivity
import com.newproject.ui.gallery.datamodel.GalleryData
import com.newproject.ui.gallery.utils.GalleryAdapter
import com.newproject.ui.homefragment.utils.HomeAdapter
import com.newproject.ui.homefragment.utils.HomeData
import com.newproject.ui.surname.datamodel.SurnameData

class GalleryViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityGalleryBinding
    private lateinit var mContext: Context
    lateinit var adapter: GalleryAdapter

    fun setBinder(binder: ActivityGalleryBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        init()
    }

    private fun init() {
        adapter = GalleryAdapter(mContext)
        binder.recyclerViewGallary.adapter = adapter
        adapter.setEventListener(object : GalleryAdapter.EventListener {
            override fun onItemClick(pos: Int, item: GalleryData) {

            }
        })
        getGalleryList()
    }

    private fun getGalleryList() {
        try {

            showDialog("", mContext as Activity)
            var query = db!!.collection(FirestoreTable.GALLERY)

            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    val item = result.toObjects(GalleryData::class.java)
                    adapter.addAll(item)
                    Debug.e("Get All Data Successfully")
                }
                dismissDialog()
            }.addOnFailureListener {
                it.printStackTrace()
                dismissDialog()
            }.addOnCompleteListener {
                dismissDialog()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class ViewClickHandler {

    }
}