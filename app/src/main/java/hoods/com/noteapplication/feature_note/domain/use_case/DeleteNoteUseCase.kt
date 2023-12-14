package hoods.com.noteapplication.feature_note.domain.use_case

import hoods.com.noteapplication.feature_note.domain.repository.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor
    (
    private val repository: Repository
) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}