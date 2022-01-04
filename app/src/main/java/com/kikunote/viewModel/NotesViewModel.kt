package com.kikunote.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kikunote.dao.NoteDao
import com.kikunote.database.NoteRoomDatabase
import com.kikunote.entity.Note
import com.kikunote.session.UserSession

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val listNotes = MutableLiveData<ArrayList<Note>>()
    private var dao: NoteDao
    private var session: UserSession = UserSession(context)
    private var owner: String


    init {
        owner = session.getUserDetails()["email"].toString()
        val database = NoteRoomDatabase.getDatabase(context)
        dao = database.getNoteDao()

    }

    fun setNotes() {
        val listItems = arrayListOf<Note>()
        Log.i("listItemsNVM", dao.getAll(owner).toString())
        listItems.addAll(dao.getAll(owner))
        listNotes.postValue(listItems)
    }

    fun setNotesByLabel(label: String) {
        val listItems = arrayListOf<Note>()

        listItems.addAll(dao.getByLabel(label, owner))
        listNotes.postValue(listItems)
    }

    fun setNotesByTitle(title: String) {
        val listItems = arrayListOf<Note>()

        listItems.addAll(dao.getByTitle(title, owner))
        listNotes.postValue(listItems)
    }

    fun insertNote(note: Note){
        dao.insert(note.copy(owner = owner))
    }

    fun updateNote(note: Note){
        dao.update(note.copy(owner = owner))
    }

    fun deleteNote(note: Note){
        dao.delete(note.copy(owner = owner))
    }

    fun getNotes(): LiveData<ArrayList<Note>> {
        return listNotes
    }
}

