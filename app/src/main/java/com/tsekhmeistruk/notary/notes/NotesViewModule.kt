package com.tsekhmeistruk.notary.notes

import android.arch.lifecycle.ViewModel
import com.tsekhmeistruk.notary.data.source.NotesRepository

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NotesViewModule(private var notesRepository: NotesRepository) : ViewModel() {
}
