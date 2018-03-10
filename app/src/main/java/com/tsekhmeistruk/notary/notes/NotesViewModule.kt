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
    private val liveData = MutableLiveData<DataResource<List<Note>>>()

    fun getAllNotes() {
        compositeDisposable.add(notesRepository.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setValue(DataResource.success(it)) },
                        {
                            Log.e(tag, it.message)
                            setValue(DataResource.error(it, null))
                        }))
    }

    @MainThread
    private fun setValue(newValue: DataResource<List<Note>>) {
        if (!Objects.equals(liveData.value, newValue)) {
            liveData.value = newValue
        }
    }

    fun getLiveData(): MutableLiveData<DataResource<List<Note>>> {
        return liveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
