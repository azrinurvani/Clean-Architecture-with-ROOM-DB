package com.azrinurvani.cleanarchitecture.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.azrinurvani.cleanarchitecture.framework.di.AppModule
import com.azrinurvani.cleanarchitecture.framework.di.DaggerViewModelComponent
import com.azrinurvani.core.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel (application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases : UseCases

    val notes = MutableLiveData<List<Note>>()

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)
    }

    fun getNotes() = coroutineScope.launch {
        val notesList = useCases.getAllNotes
        notes.postValue(notesList.invoke())
    }
}