package com.tsekhmeistruk.notary.addeditnote

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.tsekhmeistruk.notary.R
import kotlinx.android.synthetic.main.fragment_add_edit.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddEditFragment : Fragment() {

    companion object {
        fun newInstance(): AddEditFragment {
            return AddEditFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_add_edit, container, false)

        val pagerAdapter = DrawablePagerAdapter()
        v.view_pager.adapter = pagerAdapter
        v.page_indicator.setViewPager(v.view_pager)
        v.back.setOnClickListener { activity.onBackPressed() }

        return v
    }

    fun getDrawableResource(pagerItemPosition: Int): Int {
        return when (pagerItemPosition) {
            0 -> R.drawable.red_drawable
            1 -> R.drawable.blue_drawable
            2 -> R.drawable.green_drawable
            3 -> R.drawable.yellow_drawable
            else -> 0
        }
    }

    fun getDate(): String {
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
}
