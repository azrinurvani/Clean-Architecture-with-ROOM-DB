package com.azrinurvani.core.usecase

import com.azrinurvani.core.data.Note
import com.azrinurvani.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}