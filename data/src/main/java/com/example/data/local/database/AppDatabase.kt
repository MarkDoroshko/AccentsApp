package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.WordDao
import com.example.data.local.model.VariantDbModel
import com.example.data.local.model.WordDbModel

@Database(
    entities = [WordDbModel::class, VariantDbModel::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}