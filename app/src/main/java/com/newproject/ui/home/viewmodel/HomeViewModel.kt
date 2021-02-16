package com.newproject.ui.home.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import com.newproject.R
import com.newproject.apputils.*
import com.newproject.base.viewmodel.BaseViewModel
import com.newproject.databinding.ActivityHomeBinding
import com.newproject.interfaces.TopBarClickListener
import com.newproject.ui.addperson.view.AddPersonActivity
import com.newproject.ui.ads.view.AdsActivity
import com.newproject.ui.gallery.datamodel.GalleryData
import com.newproject.ui.gallery.view.GalleryActivity
import com.newproject.ui.home.view.HomeActivity
import com.newproject.ui.homefragment.utils.HomeAdapter
import com.newproject.ui.surname.view.SurnameActivity
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener


class HomeViewModel(application: Application) : BaseViewModel(application){

    private lateinit var binder: ActivityHomeBinding
    lateinit var mContext: Context
    lateinit var adapter: HomeAdapter

    var sampleImages = intArrayOf(
        R.drawable.image_4,
        R.drawable.image_4,
        R.drawable.image_4,
        R.drawable.image_4,
        R.drawable.image_4
    )
    var adsList = mutableListOf<GalleryData>()


    fun setBinder(
        binder: ActivityHomeBinding
    ) {
        this.binder = binder
        this.mContext = binder.root.context
        binder.viewModel = this
        binder.viewClickHandler = ViewClickHandler()
        binder.topbar.isTextShow = true
        binder.topbar.isNavShow = true
        binder.topbar.isBackShow = false
        binder.topbar.isLanguageImgShow = false
        binder.topbar.topBarClickListener = SlideMenuClickListener()
        init()
    }


    fun init() {
        initDrawer(mContext)
        getAdsList()

    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
//            imageView?.setImageResource(adsList[position])
            val urlLogo = adsList[position].image
            Utils.loadImage(imageView!!, urlLogo!!,mContext, R.drawable.placeholder)
        }
    }


    var imageClickListener: ImageClickListener = object : ImageClickListener {
        override fun onClick(position: Int) {
//            Toast.makeText(mContext, "Carousel $position", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAdsList() {
        try {

//            showDia/log("", mContext as Activity)
            var query = db!!.collection(FirestoreTable.BUSINESSADVERTISE)

            query.get().addOnSuccessListener { result ->
                if (result != null && result.isEmpty.not()) {
                    val item = result.toObjects(GalleryData::class.java)
                    adsList.addAll(item)
                    binder.carouselViewHomefrag.setImageListener(imageListener)
                    binder.carouselViewHomefrag.setImageClickListener(imageClickListener)
                    binder.carouselViewHomefrag.pageCount = adsList.size
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
        fun onGallery(view: View) {
            try {
                var intent = Intent(mContext, GalleryActivity::class.java)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onContact(view: View) {
            try {
                var intent = Intent(mContext, SurnameActivity::class.java)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun onAdvertise(view: View) {
            try {
                var intent = Intent(mContext, AdsActivity::class.java)
                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        fun onAddPerson(view: View) {
            try {
//                var intent = Intent(mContext, AddPersonActivity::class.java)
//                mContext.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    inner class SlideMenuClickListener : TopBarClickListener {
        override fun onTopBarClickListener(view: View?, value: String?) {
            Utils.hideKeyBoard(getContext(), view!!)
            if (value.equals(getLabelText(R.string.language_change))) {
                try {
                    onLanguage(view)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (value.equals(getLabelText(R.string.menu))) {
                try {
                    onTopMenuClick()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun onBackClicked(view: View?) {
        }
    }

    fun onLanguage(view: View) {
        try {
            val popupMenu: PopupMenu = PopupMenu(mContext,binder.topbar.languageIcon)
            popupMenu.menuInflater.inflate(R.menu.popup_lang,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.english ->{
                        var lan = Utils.getPref(mContext,Constant.LANGUAGE, LanguageHelper.mGujaratiFlag)
                        if(!lan!!.equals(LanguageHelper.mEnglishFlag) ) {
                            Utils.setPref(
                                mContext,
                                Constant.LANGUAGE,
                                LanguageHelper.mEnglishFlag
                            )
                            LanguageHelper.setNewLocale(mContext, LanguageHelper.mEnglishFlag)
                            loginRegisterActivity()
                        }
                    }
                    R.id.gujarati ->{
                        var lan = Utils.getPref(mContext,Constant.LANGUAGE,LanguageHelper.mGujaratiFlag)
                        if(!lan!!.equals(LanguageHelper.mGujaratiFlag) ) {
                            Utils.setPref(
                                mContext,
                                Constant.LANGUAGE,
                                LanguageHelper.mGujaratiFlag
                            )
                            LanguageHelper.setNewLocale(mContext, LanguageHelper.mGujaratiFlag)
                            loginRegisterActivity()
                        }
                    }
                }
                true
            })
            popupMenu.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loginRegisterActivity(){
        val i = Intent(mContext, HomeActivity()::class.java)
        i.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(i)
    }
}

