package com.example.testtask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testtask.models.LinkEntity

@Database(entities = [(LinkEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linksDao(): LinksDao
}