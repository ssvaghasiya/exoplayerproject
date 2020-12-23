package com.newproject.apputils

import android.content.Context
import android.content.res.Configuration
import java.util.*

/**
 * Created by Bhavesh Hirpara on 25-05-2020
 */
object LanguageHelper {

    var mEnglishFlag = "en"
    var mArabicFlag = "ar"

    fun setLocale(context: Context?): Context {
        return updateResources(context!!, getCurrentLanguage(context)!!)
    }

    inline fun setNewLocale(context: Context, language: String) {
        persistLanguagePreference(context, language)
        updateResources(context, language)
    }

    inline fun getCurrentLanguage(context: Context?): String? {
        var mCurrentLanguage: String?
        mCurrentLanguage = getLanguage(context!!)
        return mCurrentLanguage
    }

    fun persistLanguagePreference(context: Context, language: String) {
        setLanguage(context, language)

    }

    fun setLanguage(c: Context, lang: String) {
        Utils.setPref(c, Constant.LANGUAGE, lang)
    }

    fun getLanguage(c: Context): String? {
        return Utils.getPref(c, Constant.LANGUAGE, "en")
    }

    fun getStringByLocal(context: Context, resId: Int, locale: String): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(locale))
        return context.createConfigurationContext(configuration).resources.getString(resId)
    }

    fun updateResources(context: Context, language: String): Context {

        var contextFun = context

        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        configuration.setLocale(locale)
        contextFun = context.createConfigurationContext(configuration)
        return contextFun
    }

}