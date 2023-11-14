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


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases : UseCases

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)
    }

    fun saveNote(note: Note){
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun getNote(id : Long){
        coroutineScope.launch {
            val note = useCases.getNote(id = id)
            currentNote.postValue(note)
        }
    }
    fun deleteNote(note : Note){
        coroutineScope.launch {
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }
}