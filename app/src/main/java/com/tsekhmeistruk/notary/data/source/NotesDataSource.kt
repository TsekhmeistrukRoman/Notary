package com.tsekhmeistruk.notary.data.source

import com.tsekhmeistruk.notary.data.Note
import io.reactivex.Flowable

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
interface NotesDataSource {

    fun getAllNotes(): Flowable<List<Note>>

    fun removeNote(noteId: String): Flowable<Void>

    fun updateNote(noteId: String): Flowable<Note>

    fun getNoteById(noteId: String): Flowable<Note>

    fun addNote(note: Note): Flowable<Note>
}
