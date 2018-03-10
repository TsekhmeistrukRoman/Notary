package com.tsekhmeistruk.notary.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
@Entity(tableName = "notes")
class Note constructor(
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "date") var date: String = "",
        @ColumnInfo(name = "colorRes") var colorResource: Int = 0,
        @ColumnInfo(name = "pagerPosition") var pagerPosition: Int = 0,
        @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()) : BaseEntity() {

    fun copy() = Note(title, date, colorResource, pagerPosition, id)

    fun update(note: Note) {
        title = note.title
        date = note.date
        colorResource = note.colorResource
        pagerPosition = note.pagerPosition
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (title != other.title) return false
        if (date != other.date) return false
        if (colorResource != other.colorResource) return false
        if (pagerPosition != other.pagerPosition) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + colorResource
        result = 31 * result + pagerPosition
        result = 31 * result + id.hashCode()
        return result
    }
}
