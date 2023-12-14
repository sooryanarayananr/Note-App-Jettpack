package hoods.com.noteapplication.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hoods.com.noteapplication.feature_note.data.local.NoteDao
import hoods.com.noteapplication.feature_note.data.local.NoteDatabase
import hoods.com.noteapplication.feature_note.domain.repository.Repository
import hoods.com.noteapplication.feature_note.domain.use_case.AddNoteUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.DeleteNoteUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.GetAllNotesUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.GetNoteByIdUseCase
import hoods.com.noteapplication.feature_note.domain.use_case.NoteUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao =
        database.noteDao

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context, NoteDatabase::class.java,
            "notes_db"
        )
            .build()
}

//@Provides
//@Singleton
//fun provideNoteUseCase(repository: Repository): NoteUseCases {
//    return NoteUseCases(
//        getNotes = GetAllNotesUseCase(repository),
//        deleteNote = DeleteNoteUseCase(repository),
//        addNote = AddNoteUseCase(repository),
//        getSingleNote = GetNoteByIdUseCase(repository)
//    )
//}
