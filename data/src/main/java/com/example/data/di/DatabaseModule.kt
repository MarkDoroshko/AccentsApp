package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.WordDao
import com.example.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "words.db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    @Singleton
    @Provides
    fun provideWordsDao(
        database: AppDatabase
    ): WordDao {
        return database.wordDao()
    }
}