package com.azrinurvani.cleanarchitecture.framework.di

import com.azrinurvani.cleanarchitecture.framework.UseCases
import com.azrinurvani.core.repository.NoteRepository
import com.azrinurvani.core.usecase.AddNote
import com.azrinurvani.core.usecase.GetAllNotes
import com.azrinurvani.core.usecase.GetNote
import com.azrinurvani.core.usecase.RemoveNote
import dagger.Module
import dagger.Provides


@Module
class UseCasesModule {

    @Provides
    fun getUsesCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )
}