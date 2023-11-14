package com.azrinurvani.cleanarchitecture.framework.di

import com.azrinurvani.cleanarchitecture.framework.ListViewModel
import com.azrinurvani.cleanarchitecture.framework.NoteViewModel
import dagger.Component


@Component(modules = [
    AppModule::class,
    RepositoryModule::class,
    UseCasesModule::class])
interface ViewModelComponent {

    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)

}