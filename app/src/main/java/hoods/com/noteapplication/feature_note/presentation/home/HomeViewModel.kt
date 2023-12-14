package hoods.com.noteapplication.feature_note.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hoods.com.noteapplication.feature_note.data.local.model.Note
import hoods.com.noteapplication.common.ScreenViewState
import hoods.com.noteapplication.feature_note.domain.use_case.DeleteNoteUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.GetAllNotesUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNotesUseCase: DeleteNoteUseCase,
):ViewModel() {
    private val _state:MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow() //read only for room

    init{
        getAllNotes()
    }

    private fun getAllNotes()
    {
        getAllNotesUseCase()
            .onEach {
                _state.value= HomeState(notes = ScreenViewState.Success(it))
            }
            .catch {
                _state.value= HomeState(notes = ScreenViewState.Error(it.message))
            }
            .launchIn(viewModelScope)
    }

    fun deleteNote(noteId: Long) = viewModelScope.launch {
        deleteNotesUseCase(noteId)
    }

}
data class HomeState(
    val notes: ScreenViewState<List<Note>> = ScreenViewState.Loading,
    )