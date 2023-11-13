package com.azrinurvani.cleanarchitecture.framework

import com.azrinurvani.core.usecase.AddNote
import com.azrinurvani.core.usecase.GetAllNotes
import com.azrinurvani.core.usecase.GetNote
import com.azrinurvani.core.usecase.RemoveNote

data class UseCases (
    val addNote : AddNote,
    val getAllNotes : GetAllNotes,
    val getNote : GetNote,
    val removeNote: RemoveNote
)