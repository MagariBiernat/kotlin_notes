package com.kikunote.session

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.kikunote.activity.LoginActivity
import com.kikunote.activity.MainActivity

class UserSession(private var context: Context) {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor

    companion object {
        const val PREF_NAME: String = "UserContextPreferences"
        const val IS_LOGGED_IN: String = "isLoggedIn"
        const val KEY_USERNAME: String = "username"
        const val KEY_EMAIL: String = "email"
        private var PRIVATE_MODE: Int = 0
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Companion.PRIVATE_MODE)
        editor = pref.edit()
    }

    fun createLoginSession(name: String, email:String) {
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(KEY_USERNAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun checkIsLoggedIn(){
        if(!this.isLoggedIn()){
            val intent: Intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        } else {
            val intent: Intent = Intent(context, MainActivity::class.java )
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    fun getUserDetails(): HashMap<String, String> {
        val user: Map<String, String> = HashMap<String, String>()
        (user as HashMap).put(KEY_USERNAME, pref.getString(KEY_USERNAME, null).toString())
        (user as HashMap).put(KEY_EMAIL, pref.getString(KEY_EMAIL, null).toString())

        return user
    }


    fun logoutUser() {
        editor.clear()
        editor.commit()

        val intent: Intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }


    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }
}