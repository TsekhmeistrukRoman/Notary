package com.tsekhmeistruk.notary.addeditnote

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.tsekhmeistruk.notary.R
import com.tsekhmeistruk.notary.data.Note
import com.tsekhmeistruk.notary.notes.NoteListActivity
import com.tsekhmeistruk.notary.widgets.Status
import com.tsekhmeistruk.notary.widgets.util.BaseActivity
import com.tsekhmeistruk.notary.widgets.util.DataResource
import kotlinx.android.synthetic.main.fragment_add_edit.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AddEditNoteFragment : Fragment() {

    companion object {
        private val passedNoteKey = "Note"

        fun newInstance(): AddEditNoteFragment {
            return AddEditNoteFragment()
        }

        fun newInstance(isExistent: Boolean): AddEditNoteFragment {
            val bundle = Bundle()
            bundle.putBoolean(passedNoteKey, isExistent)
            val addEditFragment = AddEditNoteFragment()
            addEditFragment.arguments = bundle
            return addEditFragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AddEditNoteViewModel

    private lateinit var interactionListener: OnFragmentInteractionListener
    private var isNoteExistent: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        (activity as BaseActivity).getAppComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_add_edit, container, false)

        val pagerAdapter = DrawablePagerAdapter()
        v.view_pager.adapter = pagerAdapter
        v.page_indicator.setViewPager(v.view_pager)
        v.back.setOnClickListener { activity.onBackPressed() }
        v.done.setOnClickListener {
            if (v.title.text.toString() == "") {
                Toast.makeText(context, getText(R.string.empty_title), Toast.LENGTH_LONG).show()
            } else {
                (activity as BaseActivity).hideKeyboard()
                val note = Note(v.title.text.toString(), getDate(), getDrawableResource(v.view_pager.currentItem), v.view_pager.currentItem)
                viewModel.addNoteToDatabase(note)
            }
        }

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            if (arguments.getBoolean(passedNoteKey)) {
                view?.remove?.visibility = View.VISIBLE
                view?.remove?.setOnClickListener { viewModel.removeNoteFromDatabase((activity as NoteListActivity).choosedNote!!) }
                view?.done?.visibility = View.GONE
                view?.title?.setText((activity as NoteListActivity).choosedNote!!.title)
                view?.view_pager?.currentItem = (activity as NoteListActivity).choosedNote!!.pagerPosition

                isNoteExistent = false

                view?.title?.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        (activity as NoteListActivity).choosedNote!!.title = p0.toString()
                    }
                })

                view?.view_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    }

                    override fun onPageSelected(position: Int) {
                        (activity as NoteListActivity).choosedNote!!.pagerPosition = position
                        (activity as NoteListActivity).choosedNote!!.colorResource = getDrawableResource(position)
                    }
                })
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddEditNoteViewModel::class.java)
        viewModel.getLiveData().observe(this, android.arch.lifecycle.Observer<DataResource<Note>> { res ->
            if (res != null) {
                when (res.status) {
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Toast.makeText(context, getText(R.string.error), Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS -> {
                        activity.onBackPressed()
                        interactionListener.onFragmentInteraction(isNoteExistent, res.data)
                    }
                }
            }
        })
    }

    override fun onAttach(context: Activity?) {
        super.onAttach(context)

        try {
            interactionListener = context as OnFragmentInteractionListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    private fun getDrawableResource(pagerItemPosition: Int): Int {
        return when (pagerItemPosition) {
            0 -> R.drawable.red_drawable
            1 -> R.drawable.blue_drawable
            2 -> R.drawable.green_drawable
            3 -> R.drawable.yellow_drawable
            else -> 0
        }
    }

    private fun getDate(): String {
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy/MM/dd/kk:mm:ss")

        return format.format(currentDate)
    }

    private inner class DrawablePagerAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(activity)
            val pagerItem = inflater.inflate(R.layout.item_drawable_pager,
                    container,
                    false) as ImageView

            when (position) {
                0 -> pagerItem.setImageResource(R.drawable.red_drawable)
                1 -> pagerItem.setImageResource(R.drawable.blue_drawable)
                2 -> pagerItem.setImageResource(R.drawable.green_drawable)
                3 -> pagerItem.setImageResource(R.drawable.yellow_drawable)
            }

            container.addView(pagerItem)
            return pagerItem
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return 4
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(wasAdded: Boolean, note: Note?)
    }
}
