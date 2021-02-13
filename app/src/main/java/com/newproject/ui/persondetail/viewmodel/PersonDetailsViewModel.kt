package com.newproject.ui.persondetail.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityPersonDetailsBinding
import com.newproject.databinding.ActivitySurnameBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.persondetail.datamodel.PersonDetailData
import com.newproject.ui.persondetail.utils.PersonDetailDataAdapter
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surname.utils.SurnameAdapter
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData
import com.newproject.ui.surnamecontacts.view.SurnameContactsActivity

class PersonDetailsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivityPersonDetailsBinding
    private lateinit var mContext: Context
    lateinit var adapter: PersonDetailDataAdapter
    var person: SurnameContactsData? = null


    fun setBinder(binder: ActivityPersonDetailsBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        binder.topBar.isTextShow = true
        binder.topBar.isBackShow = true
        binder.topBar.topBarClickListener = SlideMenuClickListener()
        init()
    }

    private fun init() {
        person = (mContext as Activity).intent.extras?.getSerializable("person") as SurnameContactsData

        adapter = PersonDetailDataAdapter(mContext)
        binder.rvSurname.adapter = adapter
        adapter.setEventListener(object : PersonDetailDataAdapter.EventListener {

            override fun onItemClick(pos: Int, item: PersonDetailData) {

            }
        })
        getPersonDetails()
    }

    private fun getPersonDetails() {
        try {

//            var query = db.collection(FirestoreTable.CHAT)
//                .whereEqualTo(RequestParamsUtils.SENDER_ID, loggedInUserId)
            showDialog("",mContext as Activity)
            var query = db!!.collection(FirestoreTable.SUB_DETAIL_EACH_MEMBER)


            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    if (result != null && result.isEmpty.not()) {
                        val item = result.toObjects(PersonDetailData::class.java)
                        for (i in item) {
                            Debug.e(i.main_number.toString()+" "+person?.phone)
                            if (i.main_number.equals(person?.phone)) {
                                Debug.e(i.name.toString())
                                adapter.add(i)
                            }
                        }
                        Debug.e("Get All Data Successfully")
                    }
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
        fun onReviewsAndRanks(view: View) {
            try {
//                var intent = Intent(mContext, ReviewsAndRankActivity::class.java)
//                mContext.startActivity(intent)
//                (mContext as Activity).finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onSeeAll(view: View) {
            try {
//                var intent = Intent(mContext, ReviewsAndRankActivity::class.java)
//                mContext.startActivity(intent)
//                (mContext as Activity).finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}