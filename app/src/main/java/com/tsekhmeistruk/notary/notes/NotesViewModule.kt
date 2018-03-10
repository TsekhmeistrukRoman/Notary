package com.tsekhmeistruk.notary.notes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.MainThread
import android.util.Log
import com.tsekhmeistruk.notary.data.Note
import com.tsekhmeistruk.notary.data.source.NotesRepository
import com.tsekhmeistruk.notary.widgets.util.DataResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NotesViewModule(private var notesRepository: NotesRepository) : ViewModel() {

    private val tag = "NotesViewModule"

    private val compositeDisposable = CompositeDisposable()
    private val liveDataList = MutableLiveData<DataResource<List<Note>>>()
    private val liveDataItem = MutableLiveData<DataResource<Note>>()
    private val liveDataRemoved = MutableLiveData<DataResource<Note>>()

    fun getAllNotes() {
        compositeDisposable.add(notesRepository.getAllNotes()
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
        if (!Objects.equals(liveDataList.value, newValue)) {
            liveDataList.value = newValue
        }
    }

    @MainThread
    private fun setValueItem(newValue: DataResource<Note>) {
        if (!Objects.equals(liveDataList.value, newValue)) {
            liveDataItem.value = newValue
        }
    }

    @MainThread
    private fun setValueRemoved(newValue: DataResource<Note>) {
        if (!Objects.equals(liveDataRemoved.value, newValue)) {
            liveDataRemoved.value = newValue
        }
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
        compositeDisposable.add(notesRepository.removeNote(note.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValueRemoved(DataResource.success(note)) },
                        {
                            Log.e(tag, it.message)
                            setValueRemoved(DataResource.error(it, note))
                        }))
    }

    fun updateNote(note: Note) {
        compositeDisposable.add(notesRepository.updateNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValueItem(DataResource.success(note)) },
                        {
                            Log.e(tag, it.message)
                            setValueItem(DataResource.error(it, null))
                        }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
