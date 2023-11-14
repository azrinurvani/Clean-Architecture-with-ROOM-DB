package com.azrinurvani.cleanarchitecture.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.azrinurvani.core.data.Note
import com.azrinurvani.core.repository.NoteRepository
import com.azrinurvani.core.usecase.AddNote
import com.azrinurvani.core.usecase.GetAllNotes
import com.azrinurvani.core.usecase.GetNote
import com.azrinurvani.core.usecase.RemoveNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val repository = NoteRepository(RoomNoteDataSource(application))

    val useCases = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

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