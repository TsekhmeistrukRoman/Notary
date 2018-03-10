package com.tsekhmeistruk.notary.widgets.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsekhmeistruk.notary.R
import com.tsekhmeistruk.notary.data.Note
import kotlinx.android.synthetic.main.item_note_layout.view.*

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NoteListAdapter(private val listener: OnNoteClickListener) : SimpleAdapter<Note, RecyclerView.ViewHolder>() {

    fun findNotePosition(note: Note): Int {
        list!!.forEach {
            if (it.id == note.id) {
                return list!!.indexOf(it)
            }
        }

        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_note_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bindNote(getItem(position), listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindNote(note: Note, listener: OnNoteClickListener) {
            with(note) {
                itemView.note_color.setImageResource(colorResource)
                itemView.note_text.text = title
                itemView.note_date.text = date
            }
            itemView.setOnClickListener { listener.onNoteClick(adapterPosition) }
        }
    }

    interface OnNoteClickListener {

        fun onNoteClick(position: Int)
    }
}
