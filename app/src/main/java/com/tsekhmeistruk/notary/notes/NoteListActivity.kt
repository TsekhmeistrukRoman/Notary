package com.tsekhmeistruk.notary.notes

import android.os.Bundle
import com.tsekhmeistruk.notary.R
import com.tsekhmeistruk.notary.addeditnote.AddEditNoteFragment
import com.tsekhmeistruk.notary.util.BaseActivity
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        add_button.setOnClickListener { startFragment(AddEditNoteFragment.newInstance(), true) }
    }
}
