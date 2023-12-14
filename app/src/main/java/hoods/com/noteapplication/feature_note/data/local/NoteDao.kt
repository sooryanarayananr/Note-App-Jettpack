package hoods.com.noteapplication.feature_note.data.local

import androidx.room.*
import hoods.com.noteapplication.feature_note.data.local.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY createdDate")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id ORDER BY createdDate")
    fun getNoteById(id: Long): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("DELETE FROM notes WHERE id=:id")
    suspend fun delete(id: Long)
}