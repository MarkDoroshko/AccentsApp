package com.example.data.di

import com.example.data.repository.UserProgressRepositoryImpl
import com.example.data.repository.WordsRepositoryImpl
import com.example.domain.repository.UserProgressRepository
import com.example.domain.repository.WordsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindWordsRepository(impl: WordsRepositoryImpl): WordsRepository

    @Binds
    @Singleton
    fun bindUserProgressRepository(impl: UserProgressRepositoryImpl): UserProgressRepository
}
