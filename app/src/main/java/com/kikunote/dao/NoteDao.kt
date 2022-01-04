package com.kikunote.dao

import androidx.room.*
import com.kikunote.entity.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE owner = :owner ORDER BY id DESC")
    fun getAll(owner: String) : List<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Int) : List<Note>

    @Query("SELECT * FROM notes WHERE title LIKE :search AND owner = :owner ORDER BY id DESC")
    fun getByTitle(search: String?, owner: String): List<Note>

    @Query("SELECT * FROM notes WHERE label = :id AND owner = :owner ORDER BY id DESC")
    fun getByLabel(id: String, owner: String) : List<Note>
}