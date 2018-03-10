package com.tsekhmeistruk.notary.data.source

import com.tsekhmeistruk.notary.data.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NotesRepository @Inject constructor(private val notesLocalDataSource: NotesDataSource) : NotesDataSource {

    override fun getAllNotes(): Single<List<Note>> {
        return notesLocalDataSource.getAllNotes()
    }

    override fun getNoteById(noteId: String): Single<Note> {
        return notesLocalDataSource.getNoteById(noteId)
    }

    override fun removeNote(noteId: String): Completable {
        return notesLocalDataSource.removeNote(noteId)
    }

    override fun updateNote(note: Note): Completable {
        return notesLocalDataSource.updateNote(note)
    }

    override fun addNote(note: Note): Completable {
        return notesLocalDataSource.addNote(note)
    }
}
