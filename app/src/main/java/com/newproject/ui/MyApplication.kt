package com.newproject.ui

import android.app.Application
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.newproject.apputils.Constant
import com.newproject.apputils.Debug


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(foregroundBackgroundListener)
    }

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    fun initFirebase() {
        initFirebaseCloud()
        initFirebaseStorage()
    }

    private fun initFirebaseCloud() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
//            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.firestoreSettings = settings

//        if (!Utils.isUserLoggedIn(getContext())) {
//            auth.signInAnonymously()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Debug.e("signInAnonymously Success", "")
//                    } else {
//                        Debug.e("signInAnonymously Fail", "")
//                    }
//
//                }
//        }
    }

    lateinit var storageRef: StorageReference
    lateinit var firebaseStorage: FirebaseStorage
    private fun initFirebaseStorage() {
        firebaseStorage = FirebaseStorage.getInstance()
        storageRef = firebaseStorage.reference
    }


    internal var foregroundBackgroundListener = ForegroundBackgroundListener()

    internal inner class ForegroundBackgroundListener : LifecycleObserver {

        var handler = Handler()

        var runGoToBackground: Runnable = Runnable {
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(Intent(Constant.APP_GOES_BACKGROUND))

//            setUserOnlineStatus(false)
        }

        var runGoToforground: Runnable = Runnable {
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(Intent(Constant.APP_GOES_FORGROUND))

//            setUserOnlineStatus(true)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun startSomething() {
            Debug.e("ProcessLog", "APP IS ON FOREGROUND")
            handler.removeCallbacks(runGoToforground)
            handler.postDelayed(runGoToforground, 100)

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stopSomething() {
            handler.removeCallbacks(runGoToBackground)
            handler.postDelayed(runGoToBackground, 100)
            Debug.e("ProcessLog", "APP IS ON BACKGROUND")
        }
    }


}