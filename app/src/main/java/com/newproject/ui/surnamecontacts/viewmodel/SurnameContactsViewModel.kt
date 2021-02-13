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
import com.newproject.ui.persondetail.datamodel.PersonDetailData
import com.newproject.ui.persondetail.view.PersonDetailsActivity
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData
import com.newproject.ui.surnamecontacts.utils.SurnameContactsAdapter
import com.newproject.ui.surnamecontacts.view.SurnameContactsActivity


class SurnameContactsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivitySurnameContactsBinding
    private lateinit var mContext: Context
    lateinit var adapter: SurnameContactsAdapter
    var surname_id: String? = null
    private val TAG = "SurnameContactsViewModel"

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
        surname_id = (mContext as Activity).intent.extras?.getString("surname_id")

        adapter = SurnameContactsAdapter(mContext)
        binder.rvSurname.adapter = adapter
        adapter.setEventListener(object : SurnameContactsAdapter.EventListener {
            override fun onItemClick(pos: Int, item: SurnameContactsData) {
                var intent = Intent(mContext, PersonDetailsActivity::class.java)
                intent.putExtra("person",item)
                mContext.startActivity(intent)
            }
        })
        getMemberList()
    }

    private fun getMemberList() {
        try {

//            var query = db.collection(FirestoreTable.CHAT)
//                .whereEqualTo(RequestParamsUtils.SENDER_ID, loggedInUserId)
//            showDialog("",mContext as Activity)
//            Debug.e("Id", surname_id.toString())
//            var query = db!!.collection(FirestoreTable.MAIN_MEMBER_NAME)
//
//            query.get().addOnSuccessListener { result ->
//                if (result != null && result.isEmpty.not()) {
//                    val item = result.toObjects(SurnameContactsData::class.java)
//                    for (i in item) {
//                        if (i.surname_id.equals(surname_id!!.trim())) {
//                            adapter.add(i)
//                        }
//                    }
//                    Debug.e("Get All Data Successfully")
//                }
//                dismissDialog()
//            }.addOnFailureListener {
//                it.printStackTrace()
//                dismissDialog()
//            }.addOnCompleteListener {
//                dismissDialog()
//            }
            showDialog("",mContext as Activity)
            db!!.collection(FirestoreTable.MAIN_MEMBER_NAME).whereEqualTo("surname_id",surname_id).get()
                .addOnSuccessListener{ documents ->
                    dismissDialog()
                    for(document in documents){
                        Debug.e(TAG,"${document.id}=>${document.data}")
                    }
                }
                .addOnFailureListener{exception ->
                    dismissDialog()
                    Debug.e("Error getting documents:",exception.message.toString())
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