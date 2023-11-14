package com.azrinurvani.cleanarchitecture.framework.di

import android.app.Application
import com.azrinurvani.cleanarchitecture.framework.RoomNoteDataSource
import com.azrinurvani.core.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}