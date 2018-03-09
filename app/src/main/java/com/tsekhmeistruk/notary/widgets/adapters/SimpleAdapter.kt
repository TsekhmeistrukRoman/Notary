package com.tsekhmeistruk.notary.widgets.adapters

import android.support.v7.widget.RecyclerView
import com.tsekhmeistruk.notary.data.BaseEntity
import java.util.*

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
abstract class SimpleAdapter<T : BaseEntity, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private var mList: MutableList<T>? = null

    val data: List<T>?
        get() = mList

    override fun getItemCount(): Int {
        return if (mList != null) mList!!.size else 0
    }

    fun getItem(i: Int): T {
        return mList!![i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    fun add(vararg items: T) {
        if (mList == null) mList = ArrayList(items.size)
        Collections.addAll(mList!!, *items)
        notifyDataSetChanged()
    }

    fun add(items: List<T>) {
        if (mList == null) mList = ArrayList(items.size)
        mList!!.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (mList != null) {
            mList!!.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mList!!.size)
        }
    }

    fun clearList() {
        if (mList != null) {
            mList!!.clear()
        }
        notifyDataSetChanged()
    }
}
