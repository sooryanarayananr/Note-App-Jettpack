package hoods.com.noteapplication.feature_note.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import hoods.com.noteapplication.feature_note.data.local.model.Note
import hoods.com.noteapplication.feature_note.domain.use_case.AddNoteUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.GetAllNotesUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.GetNoteByIdUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel @AssistedInject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    @Assisted private val noteId: Long,
) : ViewModel() {
    var state by mutableStateOf(DetailState())
        private set
    val isFormNotBlank:Boolean
        get() = state.title.isNotEmpty() &&
                state.content.isNotEmpty()
    private val note: Note
        get() = state.run {
            Note(
                id,
                title,
                content,
                createdDate
            )
        }
    private fun initialize()
    {
        val isUpdaingNote= noteId!=-1L
        state = state.copy(isUpdatingNote = isUpdaingNote)
        if(isUpdaingNote)
        {
            getNoteById()
        }
    }
    private fun getNoteById() = viewModelScope.launch {
        getNoteByIdUseCase(noteId).collectLatest {
            state=state.copy(
                id=note.id,
                title=note.title,
                content=note.content,
                createdDate = note.createdDate
            )
        }
    }

    fun onTitleChange(title:String)
    {
        state = state.copy(title = title)
    }
    fun onContentChange(content:String)
    {
        state = state.copy(content = content)
    }

    fun addOrUpdateNote()= viewModelScope.launch {
        addNoteUseCase(note = note)
    }
}

data class DetailState(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val createdDate: Date = Date(),
    val isUpdatingNote: Boolean = false
)

@Suppress("UNCHECKED_CAST")
class DetailedViewModelFactory(
    private val noteId: Long,
    private val assistedFactory: DeatilAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(noteId) as T
    }
}

@AssistedFactory
interface DeatilAssistedFactory {
    fun create(noteId: Long): DetailViewModel
}