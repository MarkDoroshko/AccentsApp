package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.QuizResultDao
import com.example.data.local.dao.UserProgressDao
import com.example.data.local.dao.WordDao
import com.example.data.local.database.AppDatabase
import com.example.data.local.database.UserDatabase
import com.example.data.local.database.UserDatabaseMigrations
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "words.db"
        ).createFromAsset("ege_stress.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Singleton
    @Provides
    fun provideWordsDao(database: AppDatabase): WordDao = database.wordDao()

    @Singleton
    @Provides
    fun provideUserDatabase(
        @ApplicationContext context: Context
    ): UserDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = UserDatabase::class.java,
            name = "user.db"
        )
            .addMigrations(*UserDatabaseMigrations.ALL)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserProgressDao(database: UserDatabase): UserProgressDao = database.userProgressDao()

    @Singleton
    @Provides
    fun provideQuizResultDao(database: UserDatabase): QuizResultDao = database.quizResultDao()
}
