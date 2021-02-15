package com.newproject.ui.surnamecontacts.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.firebase.firestore.Query
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivitySurnameContactsBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.persondetail.view.PersonDetailsActivity
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData
import com.newproject.ui.surnamecontacts.utils.SurnameContactsAdapter


class SurnameContactsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivitySurnameContactsBinding
    private lateinit var mContext: Context
    lateinit var adapter: SurnameContactsAdapter
    private val TAG = "SurnameContactsViewModel"
    var item: SurnameData? = null

    fun setBinder(binder: ActivitySurnameContactsBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        binder.topBar.isTextShow = true
        binder.topBar.topBarClickListener = SlideMenuClickListener()
        init()
    }

    private fun init() {
        item =
            (mContext as Activity).intent.extras?.getSerializable("surname") as SurnameData

        adapter = SurnameContactsAdapter(mContext)
        binder.rvSurname.adapter = adapter
        adapter.setEventListener(object : SurnameContactsAdapter.EventListener {
            override fun onItemClick(pos: Int, item: SurnameContactsData) {
                var intent = Intent(mContext, PersonDetailsActivity::class.java)
                intent.putExtra("person", item)
                mContext.startActivity(intent)
            }
        })
        getMemberList()
    }

    private fun getMemberList() {
        try {

            Debug.e("surname_id", item?.id.toString())
            showDialog("", mContext as Activity)
            db!!.collection(FirestoreTable.MAIN_MEMBER_NAME)
                .orderBy("name",Query.Direction.ASCENDING)
                .whereEqualTo("surname_id", item!!.id!!.trim()).get()
                .addOnSuccessListener { documents ->
                    dismissDialog()
                    Debug.e("documents", documents.size().toString())
                    val item = documents.toObjects(SurnameContactsData::class.java)
                    adapter.addAll(item)

//                    for (document in documents) {
//                        Debug.e(TAG, "${document.id}=>${document.data}")
//                        adapter.add(document.toObject(SurnameContactsData::class.java))
//                    }
                }
                .addOnFailureListener { exception ->
                    dismissDialog()
                    Debug.e("Error getting documents:", exception.message.toString())
                }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class SlideMenuClickListener : TopBarClickListener {
        override fun onTopBarClickListener(view: View?, value: String?) {
            Utils.hideKeyBoard(getContext(), view!!)
            if (value.equals(getLabelText(R.string.menu))) {
                try {

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun onBackClicked(view: View?) {
        }
    }

    inner class ViewClickHandler {
        fun onSeeAll(view: View) {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}