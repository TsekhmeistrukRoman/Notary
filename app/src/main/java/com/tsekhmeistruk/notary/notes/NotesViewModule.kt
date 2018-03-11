package com.tsekhmeistruk.notary.notes

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
import java.util.concurrent.TimeoutException

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NotesViewModule(private var notesRepository: NotesRepository) : BaseViewModel() {

    private val tag = "NotesViewModule"

    private val liveDataList = MutableLiveData<DataResource<List<Note>>>()
    private val liveDataItem = MutableLiveData<DataResource<Note>>()
    private val liveDataRemoved = MutableLiveData<DataResource<Note>>()

    private var counter = 0 // variable for error when loading data from db emulating

    fun getAllNotes() {
        addDisposable(notesRepository.getAllNotes()
                .doOnSubscribe { postValueList(DataResource.loading(null)) }
                .delay(1500, TimeUnit.MILLISECONDS)
                .map { list: List<Note> ->
                    // Block of code for error simulation
                    run {
                        if (counter != 2) {
                            counter++
                        } else {
                            counter = 0
                            throw TimeoutException("Simulated error")
                        }
                    }
                    list
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValueList(DataResource.success(it)) },
                        {
                            Log.e(tag, it.message)
                            setValueList(DataResource.error(it, null))
                        }))
    }

    @MainThread
    private fun setValueList(newValue: DataResource<List<Note>>) {
        liveDataList.value = newValue
    }

    private fun postValueList(newValue: DataResource<List<Note>>) {
        liveDataList.postValue(newValue)
    }

    @MainThread
    private fun setValueItem(newValue: DataResource<Note>) {
        if (!Objects.equals(liveDataList.value, newValue)) {
            liveDataItem.value = newValue
        }
    }

    private fun postValueItem(newValue: DataResource<Note>) {
        liveDataItem.postValue(newValue)
    }

    @MainThread
    private fun setValueRemoved(newValue: DataResource<Note>) {
        if (!Objects.equals(liveDataRemoved.value, newValue)) {
            liveDataRemoved.value = newValue
        }
    }

    private fun postValueRemoved(newValue: DataResource<Note>) {
        liveDataRemoved.postValue(newValue)
    }

    fun getLiveDataList(): MutableLiveData<DataResource<List<Note>>> {
        return liveDataList
    }

    fun getLiveDataItem(): MutableLiveData<DataResource<Note>> {
        return liveDataItem
    }

    fun getLiveDataRemoved(): MutableLiveData<DataResource<Note>> {
        return liveDataRemoved
    }

    fun removeNoteFromDatabase(note: Note) {
        addDisposable(notesRepository.removeNote(note.id)
                .doOnSubscribe { postValueRemoved(DataResource.loading(null)) }
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValueRemoved(DataResource.success(note)) },
                        {
                            Log.e(tag, it.message)
                            setValueRemoved(DataResource.error(it, note))
                        }))
    }

    fun updateNote(note: Note) {
        addDisposable(notesRepository.updateNote(note)
                .doOnSubscribe { postValueItem(DataResource.loading(null)) }
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValueItem(DataResource.success(note)) },
                        {
                            Log.e(tag, it.message)
                            setValueItem(DataResource.error(it, null))
                        }))
    }
}
