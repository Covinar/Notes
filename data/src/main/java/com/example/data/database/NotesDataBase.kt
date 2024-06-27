package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = true)
abstract class NotesDataBase : RoomDatabase() {

    abstract fun getDao() : NotesDao

}