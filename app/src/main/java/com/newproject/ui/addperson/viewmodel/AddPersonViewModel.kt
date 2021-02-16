package com.newproject.ui.addperson.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityAddPersonBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.addmember.view.AddMemberActivity
import com.newproject.ui.addperson.utils.AddPersonSpinnerAdapter
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData


class AddPersonViewModel(application: Application) : BaseViewModel(application), AdapterView.OnItemSelectedListener {

    private lateinit var binder: ActivityAddPersonBinding
    private lateinit var mContext: Context
//    private var surnameList = mutableListOf<SurnameData>()
    private val TAG = "AddPersonViewModel"
    var surnameData: SurnameData? = null
    var surnameContactsData: SurnameContactsData? = null
    var mGetSurName: ArrayList<SurnameData>? = null


    fun setBinder(binder: ActivityAddPersonBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        surnameData = SurnameData()
        surnameContactsData = SurnameContactsData()
        mGetSurName = ArrayList()
        init()
    }

    private fun init() {
        getSurnameList()
    }

    private fun getSurnameList() {
        try {
            showDialog("", mContext as Activity)
            var query = db!!.collection(FirestoreTable.SURNAME)


            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    val item = result.toObjects(SurnameData::class.java)
//                    surnameList.clear()
//                    surnameList.addAll(item)
                    try {
                        binder.edtAddress.setText("")
                        binder.edtBusinessAddress.setText("")
                        binder.edtBusinessDetail.setText("")
                        binder.edtName.setText("")
                        binder.edtPhone.setText("")

                        mGetSurName?.clear()
                        mGetSurName?.addAll(item)
                        if(mGetSurName?.size!! > 0){
                            val customAdapter = AddPersonSpinnerAdapter()
                            customAdapter.customeSpinnerAdapter(mContext, mGetSurName!!)
                            binder.spinnerSurName.adapter = customAdapter
                            binder.spinnerSurName.onItemSelectedListener = this
                        }
                        Debug.e("Get All Data Successfully")
                    } catch (e: Exception) {
                        e.printStackTrace()
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

    fun addSurname(surnameData1: SurnameData) {
        showDialog("", mContext as Activity)
        surnameData1.id = db!!.collection(FirestoreTable.SURNAME).document().id
        db!!.collection(FirestoreTable.SURNAME)
            .document(surnameData1.id!!)
            .set(surnameData1)
            .addOnSuccessListener {
                dismissDialog()
                Debug.e(TAG, "SurName Added Successfully")
                getSurnameList()
                //getCustomers(catalogue)
            }
            .addOnFailureListener { exception ->
                dismissDialog()
                exception.printStackTrace()
            }
    }

    fun addPerson(personData: SurnameContactsData) {
        showDialog("", mContext as Activity)
        personData.id = db!!.collection(FirestoreTable.MAIN_MEMBER_NAME).document().id
        db!!.collection(FirestoreTable.MAIN_MEMBER_NAME)
            .document(personData.id!!)
            .set(personData)
            .addOnSuccessListener {
                dismissDialog()
                Debug.e(TAG, "Person Added Successfully")
            }
            .addOnFailureListener { exception ->
                dismissDialog()
                exception.printStackTrace()
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
        fun onAddPerson(view: View) {
            try {
                surnameContactsData?.address = binder.edtAddress.text.toString()
                surnameContactsData?.business_address = binder.edtBusinessAddress.text.toString()
                surnameContactsData?.business_detail = binder.edtBusinessDetail.text.toString()
                surnameContactsData?.name = binder.edtName.text.toString()
                surnameContactsData?.phone = binder.edtPhone.text.toString()

                addPerson(surnameContactsData!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onNext(view: View) {
            try {
                var intent = Intent(mContext, AddMemberActivity::class.java)
                intent.putExtra("surname_list", mGetSurName)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onAddSurname(view: View) {
            try {
                surnameData?.surname = binder.edtSurName.text.toString()
                addSurname(surnameData!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onItemSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        surnameContactsData?.surname_id = mGetSurName?.get(position)?.id.toString()
            Toast.makeText(
                mContext,
                mGetSurName?.get(position)?.id.toString() + " " + mGetSurName?.get(position)?.surname,
                Toast.LENGTH_SHORT
            ).show();
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}