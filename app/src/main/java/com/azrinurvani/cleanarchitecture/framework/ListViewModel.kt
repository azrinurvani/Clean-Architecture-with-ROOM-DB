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

class ListViewModel (application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val repository = NoteRepository(RoomNoteDataSource(application))

    val useCases = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

    val notes = MutableLiveData<List<Note>>()

    fun getNotes() = coroutineScope.launch {
        val notesList = useCases.getAllNotes
        notes.postValue(notesList.invoke())
    }
}