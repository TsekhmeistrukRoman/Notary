package com.tsekhmeistruk.notary.notes

import android.arch.lifecycle.ViewModel
import com.tsekhmeistruk.notary.data.Note
import com.tsekhmeistruk.notary.data.source.NotesRepository
import io.reactivex.Flowable

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NotesViewModule(private var notesRepository: NotesRepository) : ViewModel() {

    fun getAllNotes(): Flowable<List<Note>> {
        return notesRepository.getAllNotes()
    }
}
