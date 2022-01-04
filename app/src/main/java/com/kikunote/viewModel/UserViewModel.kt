package com.kikunote.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kikunote.dao.UserDao
import com.kikunote.database.NoteRoomDatabase
import com.kikunote.entity.User

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val listUsers = MutableLiveData<ArrayList<User>>()
    private var dao: UserDao

    init {
        val database = NoteRoomDatabase.getDatabase(context)
        dao = database.getUserDao()
    }

    fun insertNewUser(user: User): Boolean{
        val listItems = arrayListOf<User>()

        listItems.addAll(dao.getUserByEmail(user.email))

        if(listItems.isEmpty()){
            dao.insert(user)
            return true
        }
        return false
    }

    fun checkCredentials(email:String, password:String): User? {
        val listItems = arrayListOf<User>()
        listItems.addAll(dao.checkIfUserExists(email, password))

        if(listItems.isNotEmpty()){
            return listItems[0]
        }

        return null
    }

}