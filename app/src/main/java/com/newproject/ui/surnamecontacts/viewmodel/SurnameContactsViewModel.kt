package com.newproject.ui.surnamecontacts.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.firestore.Query
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivitySurnameContactsBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.memberdetails.view.MemberDetailsActivity
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData
import com.newproject.ui.surnamecontacts.utils.SurnameContactsAdapter


class SurnameContactsViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var binder: ActivitySurnameContactsBinding
    private lateinit var mContext: Context
    lateinit var adapter: SurnameContactsAdapter
    private val TAG = "SurnameContactsViewModel"
    var item: SurnameData? = null
    var MY_PERMISSIONS_REQUEST_CALL_PHONE = 1022

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
                var intent = Intent(mContext, MemberDetailsActivity::class.java)
                intent.putExtra("person", item)
                mContext.startActivity(intent)
            }

            override fun onWhatsappClick(pos: Int, item: SurnameContactsData) {
                val contact: String?
                if (item.phone?.substring(0, 3).equals("+91")) {
                    contact = item.phone // use country code with your phone number
                } else {
                    contact = "+91" + item.phone
                }

                val url = "https://api.whatsapp.com/send?phone=$contact"
                try {
                    val pm: PackageManager = mContext.getPackageManager()
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    (mContext).startActivity(i)
                } catch (e: PackageManager.NameNotFoundException) {
                    Toast.makeText(
                        mContext,
                        "Whatsapp app not installed in your phone",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
            }

            override fun onCallMake(pos: Int, item: SurnameContactsData) {
//                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + item.phone))
//                mContext.startActivity(intent)

                val number = "tel:" + item.phone
                val mIntent = Intent(Intent.ACTION_CALL)
                mIntent.setData(Uri.parse(number))
// Here, thisActivity is the current activity
// Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(
                        mContext as Activity,
                        Manifest.permission.CALL_PHONE
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        mContext as Activity, arrayOf(Manifest.permission.CALL_PHONE),
                        MY_PERMISSIONS_REQUEST_CALL_PHONE
                    )

                    // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //You already have permission
                    try {
                        mContext.startActivity(mIntent)
                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    }
                }
            }
        })
        getMemberList()
    }

    private fun getMemberList() {
        try {
            Debug.e("surname_id", item?.id.toString())
            showDialog("", mContext as Activity)
            db!!.collection(FirestoreTable.MAIN_MEMBER_NAME)
                .orderBy("name", Query.Direction.ASCENDING)
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


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CALL_PHONE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the phone call
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
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