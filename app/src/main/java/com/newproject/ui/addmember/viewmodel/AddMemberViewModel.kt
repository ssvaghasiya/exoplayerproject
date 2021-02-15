package com.newproject.ui.addmember.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.newproject.R
import com.newproject.apputils.Debug
import com.newproject.apputils.FirestoreTable
import com.newproject.apputils.Utils
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityAddMemberBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.addmember.utils.AddMemberSpinnerAdapter
import com.newproject.ui.addperson.utils.AddPersonSpinnerAdapter
import com.newproject.ui.persondetail.datamodel.PersonDetailData
import com.newproject.ui.surname.datamodel.SurnameData
import com.newproject.ui.surnamecontacts.datamodel.SurnameContactsData
import kotlinx.android.synthetic.main.activity_add_member.view.*


class AddMemberViewModel(application: Application) : BaseViewModel(application),
    AdapterView.OnItemSelectedListener {

    private lateinit var binder: ActivityAddMemberBinding
    private lateinit var mContext: Context
    private var data = mutableListOf<SurnameData>()
    var mGetPhoneNumber: ArrayList<SurnameContactsData>? = null
    var addMemberSpinnerAdapter: AddMemberSpinnerAdapter? = null
    var mGetSurName: ArrayList<SurnameData>? = null
    var personDetailData: PersonDetailData? = null
    private val TAG = "AddMemberViewModel"


    fun setBinder(binder: ActivityAddMemberBinding) {
        this.binder = binder
        this.mContext = binder.root.context
        this.binder.viewModel = this
        this.binder.viewClickHandler = ViewClickHandler()
        personDetailData = PersonDetailData()
        mGetPhoneNumber = ArrayList()
        mGetSurName = ArrayList()
        init()
    }

    private fun init() {
        getMainMemberList()
        mGetSurName =
            (mContext as Activity).getIntent().extras?.getSerializable("surname_list") as ArrayList<SurnameData>
        Debug.e("array", mGetSurName.toString())
    }

    private fun getMainMemberList() {
        try {
            showDialog("", mContext as Activity)
            var query = db!!.collection(FirestoreTable.MAIN_MEMBER_NAME)


            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    val item = result.toObjects(SurnameContactsData::class.java)

                    try {
                        mGetPhoneNumber?.clear()
                        mGetPhoneNumber?.addAll(item)
                        Debug.e(
                            "mGetPhoneNumber",
                            mGetPhoneNumber?.size.toString() + " " + data.size.toString()
                        )
                        if (mGetPhoneNumber?.size!! > 0) {
                            addMemberSpinnerAdapter =
                                AddMemberSpinnerAdapter(mContext, mGetPhoneNumber);
                            binder.spinnerMainMemberPhone.adapter = addMemberSpinnerAdapter
                            binder.spinnerMainMemberPhone.onItemSelectedListener = this
                        }

                        if (mGetSurName?.size!! > 0) {
                            val customAdapter = AddPersonSpinnerAdapter()
                            customAdapter.customeSpinnerAdapter(mContext, mGetSurName!!)
                            binder.spinnerSurName.adapter = customAdapter
                            binder.spinnerSurName.onItemSelectedListener = this
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

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

    fun addMember(personDetailData: PersonDetailData) {
        showDialog("", mContext as Activity)
        personDetailData.id = db!!.collection(FirestoreTable.SUB_DETAIL_EACH_MEMBER).document().id
        db!!.collection(FirestoreTable.SUB_DETAIL_EACH_MEMBER)
            .document(personDetailData.id!!)
            .set(personDetailData)
            .addOnSuccessListener {
                dismissDialog()
                Debug.e(TAG, "Member Added Successfully")
                binder.edtAge.setText("")
                binder.edtDOB.setText("")
                binder.edtEducation.setText("")
                binder.edtName.setText("")
                binder.edtPhone.setText("")
                binder.edtOccupation.setText("")
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
        fun onAddMember(view: View) {
            try {
                personDetailData?.age = binder.edtAge.text.toString()
                personDetailData?.dob = binder.edtDOB.text.toString()
                personDetailData?.education = binder.edtEducation.text.toString()
                personDetailData?.name = binder.edtName.text.toString()
                personDetailData?.phone = binder.edtPhone.text.toString()
                personDetailData?.occupation = binder.edtOccupation.text.toString()
                addMember(personDetailData!!)
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
        if (adapterView?.id == binder.spinnerSurName.id) {
            personDetailData?.surname_id = mGetSurName?.get(position)?.id.toString()

            Toast.makeText(
                mContext,
                mGetSurName?.get(position)?.id.toString() + " " + mGetSurName?.get(position)?.surname,
                Toast.LENGTH_SHORT
            ).show();
        } else if (adapterView?.id == binder.spinnerMainMemberPhone.id) {
            personDetailData?.main_number =  mGetPhoneNumber?.get(position)?.phone
            Toast.makeText(
                mContext,
                mGetPhoneNumber?.get(position)?.id.toString() + " " + mGetPhoneNumber?.get(position)?.phone,
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}