package com.tsekhmeistruk.notary.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tsekhmeistruk.notary.data.Note

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
@Database(entities = arrayOf(Note::class), version = 1)
abstract class NotesDataBase : RoomDatabase() {

    abstract fun noteDao(): NotesDao
}
