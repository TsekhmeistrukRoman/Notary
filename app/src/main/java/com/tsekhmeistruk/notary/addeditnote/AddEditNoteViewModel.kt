package com.tsekhmeistruk.notary.addeditnote

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.util.Log
import com.tsekhmeistruk.notary.data.Note
import com.tsekhmeistruk.notary.data.source.NotesRepository
import com.tsekhmeistruk.notary.widgets.util.BaseViewModel
import com.tsekhmeistruk.notary.widgets.util.DataResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */

class AddEditNoteViewModel(private var notesRepository: NotesRepository) : BaseViewModel() {

    private val tag = "AddEditNoteViewModel"

    private val liveData = MutableLiveData<DataResource<Note>>()

    fun addNoteToDatabase(note: Note) {
        addDisposable(notesRepository.addNote(note)
                .doOnSubscribe { postValue(DataResource.loading(null)) }
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValue(DataResource.success(note)) },
                        {
                            Log.e(tag, it.message)
                            setValue(DataResource.error(it, note))
                        }))
    }

    fun removeNoteFromDatabase(note: Note) {
        addDisposable(notesRepository.removeNote(note.id)
                .doOnSubscribe { postValue(DataResource.loading(null)) }
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValue(DataResource.success(note)) },
                        {
                            Log.e(tag, it.message)
                            setValue(DataResource.error(it, note))
                        }))
    }

    @MainThread
    private fun setValue(newValue: DataResource<Note>) {
        if (!Objects.equals(liveData.value, newValue)) {
            liveData.value = newValue
        }
    }

    private fun postValue(newValue: DataResource<Note>) {
        liveData.postValue(newValue)
    }

    fun getLiveData(): MutableLiveData<DataResource<Note>> {
        return liveData
    }
}
