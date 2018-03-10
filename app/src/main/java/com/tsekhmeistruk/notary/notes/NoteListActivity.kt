package com.tsekhmeistruk.notary.notes

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.tsekhmeistruk.notary.R
import com.tsekhmeistruk.notary.addeditnote.AddEditNoteFragment
import com.tsekhmeistruk.notary.data.Note
import com.tsekhmeistruk.notary.widgets.Status
import com.tsekhmeistruk.notary.widgets.adapters.NoteListAdapter
import com.tsekhmeistruk.notary.widgets.util.BaseActivity
import com.tsekhmeistruk.notary.widgets.util.DataResource
import kotlinx.android.synthetic.main.activity_note_list.*
import javax.inject.Inject

class NoteListActivity : BaseActivity(), NoteListAdapter.OnNoteClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NotesViewModule

    private lateinit var listAdapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        getAppComponent().inject(this)

        add_button.setOnClickListener { startFragment(AddEditNoteFragment.newInstance(), true) }

        listAdapter = NoteListAdapter(this)
        note_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        note_list.itemAnimator = DefaultItemAnimator()
        note_list.adapter = listAdapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotesViewModule::class.java)
        viewModel.getLiveData().observe(this, android.arch.lifecycle.Observer<DataResource<List<Note>>> { res ->
            if (res != null) {
                when (res.status) {
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Toast.makeText(applicationContext, getText(R.string.error), Toast.LENGTH_LONG).show()
                        listAdapter.clearList()
                    }
                    Status.SUCCESS -> {
                        listAdapter.add(res.data as List<Note>)
                    }
                }
            }
        })

        viewModel.getAllNotes()
    }

    override fun onNoteClick(position: Int) {
        startFragment(AddEditNoteFragment.newInstance(listAdapter.getItem(position)), true)
    }
}
