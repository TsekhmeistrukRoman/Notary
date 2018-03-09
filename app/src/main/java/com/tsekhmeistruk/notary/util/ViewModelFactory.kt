package com.tsekhmeistruk.notary.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tsekhmeistruk.notary.addeditnote.AddEditNoteViewModel
import com.tsekhmeistruk.notary.data.source.NotesRepository
import com.tsekhmeistruk.notary.notes.NotesViewModule
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */

@Singleton
class ViewModelFactory @Inject constructor(private val notesRepository: NotesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddEditNoteViewModel::class.java) -> AddEditNoteViewModel(notesRepository) as T
            modelClass.isAssignableFrom(NotesViewModule::class.java) -> NotesViewModule(notesRepository) as T
            else -> throw IllegalArgumentException("ViewModel not found")
        }
    }
}
