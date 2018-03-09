package com.tsekhmeistruk.notary.data.source.local

import com.tsekhmeistruk.notary.data.Note
import com.tsekhmeistruk.notary.data.source.NotesDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NotesLocalDataSource private constructor(private val notesDao: NotesDao) : NotesDataSource {

    override fun getAllNotes(): Flowable<List<Note>> {
        return notesDao.getAllNotes()
    }

    override fun getNoteById(noteId: String): Single<Note> {
        return notesDao.getNoteById(noteId)
    }

    override fun removeNote(noteId: String): Completable {
        return Completable.fromAction {
            notesDao.deleteNoteById(noteId)
        }
    }

    override fun updateNote(note: Note): Completable {
        return Completable.fromAction {
            notesDao.updateNote(note)
        }
    }

    override fun addNote(note: Note): Completable {
        return Completable.fromAction {
            notesDao.insertNote(note)
        }
    }
}
