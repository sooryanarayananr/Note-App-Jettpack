package hoods.com.noteapplication.feature_note.domain.repository

import hoods.com.noteapplication.feature_note.data.local.model.Note
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Long):Flow<Note>

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(id: Long)
}