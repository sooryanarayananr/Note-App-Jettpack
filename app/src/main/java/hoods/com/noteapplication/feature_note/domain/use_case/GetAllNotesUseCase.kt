package hoods.com.noteapplication.feature_note.domain.use_case

import hoods.com.noteapplication.feature_note.data.local.model.Note
import hoods.com.noteapplication.feature_note.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor
    (
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Note>> =
        repository.getAllNotes()
}

