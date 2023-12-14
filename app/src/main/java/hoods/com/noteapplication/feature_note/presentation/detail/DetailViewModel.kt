package hoods.com.noteapplication.feature_note.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import hoods.com.noteapplication.feature_note.domain.use_case.AddNoteUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.GetAllNotesUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.GetNoteByIdUseCase

class DetailViewModel @AssistedInject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    @Assisted private val noteId: Long,
) : ViewModel() {
    //var state by mutableStateOf(DetailState())

}
class DetailedViewModelFactory(
    private val noteId:Long
):ViewModelProvider.Factory
{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//
//    }
}