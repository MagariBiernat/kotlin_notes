package com.kikunote.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users", indices = arrayOf(Index(value = arrayOf("email"), unique=true)))
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name="id") var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "email") var email: String = "",
    @ColumnInfo(name = "password") var password: String = ""
): Parcelable