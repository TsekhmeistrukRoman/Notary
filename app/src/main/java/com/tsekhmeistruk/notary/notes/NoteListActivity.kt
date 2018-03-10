package com.tsekhmeistruk.notary.notes

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
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


class NoteListActivity : BaseActivity(), NoteListAdapter.OnNoteClickListener, AddEditNoteFragment.OnFragmentInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NotesViewModule

    private lateinit var listAdapter: NoteListAdapter

    var choosedNote: Note? = null
    private var choosedNotePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        getAppComponent().inject(this)

        add_button.setOnClickListener { startFragment(AddEditNoteFragment.newInstance(), true) }

        initNoteRecyclerView()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotesViewModule::class.java)
        initLiveData()

        swipe_container.setOnRefreshListener(
                {
                    viewModel.getAllNotes()
                }
        )

        viewModel.getAllNotes()
    }

    override fun onBackPressed() {
        hideKeyboard()

        val count = supportFragmentManager.backStackEntryCount
        if (count != 0) {
            if (choosedNotePosition != -1) {
                if (choosedNote != listAdapter.getItem(choosedNotePosition)) {
                    if (choosedNote!!.title.isNotEmpty()) {
                        viewModel.updateNote(choosedNote!!)
                    } else {
                        viewModel.removeNoteFromDatabase(choosedNote!!)
                    }
                }
            }
        }

        super.onBackPressed()
    }

    override fun onNoteClick(position: Int) {
        choosedNote = listAdapter.getItem(position).copy()
        choosedNotePosition = position

        startFragment(AddEditNoteFragment.newInstance(true), true)
    }

    override fun onFragmentInteraction(wasAdded: Boolean, note: Note?) {
        if (wasAdded) {
            if (note != null) {
                listAdapter.add(note)
            }
        } else {
            listAdapter.removeItem(choosedNotePosition)
        }
    }

    private fun initLiveData() {
        viewModel.getLiveDataList().observe(this, android.arch.lifecycle.Observer<DataResource<List<Note>>> { res ->
            if (res != null) {
                when (res.status) {
                    Status.LOADING -> {
                        showLoadingIndicator()
                    }
                    Status.ERROR -> {
                        hideLoadingIndicator()
                        Toast.makeText(applicationContext, getText(R.string.error), Toast.LENGTH_LONG).show()
                        listAdapter.clearList()
                    }
                    Status.SUCCESS -> {
                        hideLoadingIndicator()
                        listAdapter.clearList()
                        listAdapter.add(res.data as List<Note>)
                        swipe_container.isRefreshing = false
                    }
                }
            }
        })

        viewModel.getLiveDataItem().observe(this, android.arch.lifecycle.Observer<DataResource<Note>> { res ->
            if (res != null) {
                when (res.status) {
                    Status.LOADING -> {
                        showLoadingIndicator()
                    }
                    Status.ERROR -> {
                        hideLoadingIndicator()
                        Toast.makeText(applicationContext, getText(R.string.error), Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS -> {
                        hideLoadingIndicator()
                        if (choosedNotePosition != -1) {
                            listAdapter.getItem(choosedNotePosition).update(res.data!!)
                            listAdapter.notifyItemChanged(choosedNotePosition)
                            choosedNotePosition = -1
                            choosedNote = null
                        }
                    }
                }
            }
        })

        viewModel.getLiveDataRemoved().observe(this, android.arch.lifecycle.Observer<DataResource<Note>> { res ->
            if (res != null) {
                when (res.status) {
                    Status.LOADING -> {
                        showLoadingIndicator()
                    }
                    Status.ERROR -> {
                        hideLoadingIndicator()
                        Toast.makeText(applicationContext, getText(R.string.error), Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS -> {
                        hideLoadingIndicator()
                        val index = listAdapter.findNotePosition(res.data!!)
                        if (index != -1) {
                            listAdapter.removeItem(index)
                        }
                        choosedNotePosition = -1
                        choosedNote = null
                    }
                }
            }
        })
    }

    private fun initNoteRecyclerView() {
        listAdapter = NoteListAdapter(this)
        note_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        note_list.itemAnimator = DefaultItemAnimator()
        note_list.adapter = listAdapter

        val itemTouchHelper = ItemTouchHelper(createHelperCallback())
        itemTouchHelper.attachToRecyclerView(note_list)
    }

    private fun createHelperCallback(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                viewModel.removeNoteFromDatabase(listAdapter.getItem(position))
            }
        }
    }
}
