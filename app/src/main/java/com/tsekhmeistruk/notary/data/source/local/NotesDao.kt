package com.tsekhmeistruk.notary.data.source.local

import android.arch.persistence.room.*
import com.tsekhmeistruk.notary.data.Note
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): Flowable<List<Note>>

    @Query("SELECT * FROM Notes WHERE entryid = :noteId")
    fun getNoteById(noteId: String): Single<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note): Int

    @Query("DELETE FROM Notes WHERE entryid = :noteId")
    fun deleteNoteById(noteId: String): Int
}
