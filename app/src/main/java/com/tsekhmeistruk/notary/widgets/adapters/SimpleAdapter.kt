package com.tsekhmeistruk.notary.widgets.adapters

import android.support.v7.widget.RecyclerView
import com.tsekhmeistruk.notary.data.BaseEntity
import java.util.*

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
abstract class SimpleAdapter<T : BaseEntity, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    var list: MutableList<T>? = null

    val data: List<T>?
        get() = list

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    fun getItem(i: Int): T {
        return list!![i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    fun add(vararg items: T) {
        if (list == null) list = ArrayList(items.size)
        Collections.addAll(list!!, *items)
        notifyDataSetChanged()
    }

    fun add(items: List<T>) {
        if (list == null) list = ArrayList(items.size)
        list!!.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (list != null) {
            list!!.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list!!.size)
        }
    }

    fun clearList() {
        if (list != null) {
            list!!.clear()
        }
        notifyDataSetChanged()
    }
}
