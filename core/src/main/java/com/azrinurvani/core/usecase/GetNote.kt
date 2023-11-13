package com.azrinurvani.core.usecase

import com.azrinurvani.core.repository.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(id : Long) = noteRepository.getNote(id)
}