package com.tsekhmeistruk.notary.di

import android.app.Application
import android.arch.persistence.room.Room
import com.tsekhmeistruk.notary.data.source.NotesRepository
import com.tsekhmeistruk.notary.data.source.local.NotesDao
import com.tsekhmeistruk.notary.data.source.local.NotesDataBase
import com.tsekhmeistruk.notary.data.source.local.NotesLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */

@Module
class RoomModule(val application: Application) {

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepository(NotesLocalDataSource(notesDao))
    }

    @Provides
    @Singleton
    fun provideNotesDao(dataBase: NotesDataBase): NotesDao {
        return dataBase.noteDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(): NotesDataBase = Room.databaseBuilder(
            application, NotesDataBase::class.java, "Notes.db")
            .build()
}
