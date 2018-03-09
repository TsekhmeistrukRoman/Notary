package com.tsekhmeistruk.notary.notes

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import com.tsekhmeistruk.notary.R
import com.tsekhmeistruk.notary.addeditnote.AddEditNoteFragment
import com.tsekhmeistruk.notary.widgets.util.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_note_list.*
import javax.inject.Inject

class NoteListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NotesViewModule

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        getAppComponent().inject(this)

        add_button.setOnClickListener { startFragment(AddEditNoteFragment.newInstance(), true) }
    }

    /*override fun onStart() {
        super.onStart()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotesViewModule::class.java)

        // TODO
        disposable.add(viewModel.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }*/
}
