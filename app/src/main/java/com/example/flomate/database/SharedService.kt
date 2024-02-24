package com.example.flomate.database

import android.content.Context
import android.content.SharedPreferences
import com.example.flomate.ApplicationClass

object SharedService {

    private const val PREF_NAME = "SHARED_PREFERENCES_UNIQUE_KEY"
    private const val KEY_LOGIN = "login_user"
    private const val KEY_EMAIL = "login_email"


    private val context by lazy { ApplicationClass.instance?.baseContext }
    private val sharedPreferences: SharedPreferences =
        context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var isLogin: Boolean
        get() = sharedPreferences.getBoolean(KEY_LOGIN, false)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_LOGIN, value).apply()
        }


    var emailId: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_EMAIL, value).apply()
        }

}