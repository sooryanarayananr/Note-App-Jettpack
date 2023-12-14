package hoods.com.noteapplication.feature_note.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hoods.com.noteapplication.feature_note.data.local.model.Note
import hoods.com.noteapplication.common.ScreenViewState
import java.util.Date

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: HomeState,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit = { noteId: Long -> // Add type annotation "Long"
        navController.navigate("note_details/$noteId")
    }
) {
    when (state.notes) {
        is ScreenViewState.Loading -> {
            CircularProgressIndicator()
        }

        is ScreenViewState.Success -> {
            val notes = state.notes.data
            HomeDetail(
                notes = notes,
                modifier = modifier,
                onDeleteNote = onDeleteNote,
                onNoteClicked = onNoteClicked
            )
        }

        is ScreenViewState.Error -> {
            Text(
                text = state.notes.msg ?: "Unknown Error",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun HomeDetail(
    notes: List<Note>,
    modifier: Modifier,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    )
    {
        itemsIndexed(notes) { index, note ->
            if (note != null) {
                NoteCard(
                    index = index,
                    note = note,
                    onDeleteNote = onDeleteNote,
                    onNoteClicked = onNoteClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    index: Int,
    note: Note,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit,
) {
    val isEvenIndex = index % 2 == 0
    val shape = when {
        isEvenIndex -> {
            RoundedCornerShape(
                topStart = 50f,
                bottomEnd = 50f
            )
        }

        else -> {
            RoundedCornerShape(
                topEnd = 50f,
                bottomStart = 50f
            )
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = shape,
        onClick = { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = note.content,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                IconButton(onClick = { onDeleteNote(note.id) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevHome() {
    val navController = rememberNavController() // Initialize a navController
    HomeScreen(
        navController = navController, // Pass the navController
        state = HomeState(
            notes = ScreenViewState.Success(notes)
        ),
        onDeleteNote = {},
        onNoteClicked = {}
    )
}
val placeHolderText = "Hello My Friend, How are you? Are you ok?"
val notes = listOf(
    Note(
        title = "Room db",
        content = placeHolderText + placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Jetpack Compose",
        content = "Testing",
        createdDate = Date()
    ),
    Note(
        title = "Jetpack Compose Main",
        content = "Testing Main",
        createdDate = Date()
    )
)