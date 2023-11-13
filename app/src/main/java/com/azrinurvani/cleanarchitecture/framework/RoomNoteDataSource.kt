package com.azrinurvani.cleanarchitecture.framework

import android.content.Context
import com.azrinurvani.cleanarchitecture.framework.db.DatabaseService
import com.azrinurvani.cleanarchitecture.framework.db.NoteEntity
import com.azrinurvani.core.data.Note
import com.azrinurvani.core.repository.NoteDataSource

class RoomNoteDataSource(context: Context) : NoteDataSource {

    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long?) : Note? = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note)  = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}