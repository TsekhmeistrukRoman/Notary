package com.tsekhmeistruk.notary.data.source

import com.tsekhmeistruk.notary.data.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
interface NotesDataSource {

    fun getAllNotes(): Single<List<Note>>

    fun getNoteById(noteId: String): Single<Note>

    fun removeNote(noteId: String): Completable

    fun updateNote(note: Note): Completable

    fun addNote(note: Note): Completable
}
