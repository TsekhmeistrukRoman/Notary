package com.tsekhmeistruk.notary.addeditnote

import android.arch.lifecycle.ViewModel
import com.tsekhmeistruk.notary.data.Note
import com.tsekhmeistruk.notary.data.source.NotesRepository
import io.reactivex.Completable

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */

class AddEditNoteViewModel(private var notesRepository: NotesRepository) : ViewModel() {

    fun addNoteToDatabase(note: Note): Completable {
        return notesRepository.addNote(note)
    }
}
