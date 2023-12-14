package hoods.com.noteapplication.feature_note.domain.use_case

import hoods.com.noteapplication.feature_note.data.local.model.Note
import hoods.com.noteapplication.feature_note.domain.repository.Repository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(note : Note)
    {
        repository.update(note)
    }
}