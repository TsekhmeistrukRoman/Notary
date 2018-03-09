package com.tsekhmeistruk.notary.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
@Entity(tableName = "notes")
class Note constructor(
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "date") var date: String = "",
        @ColumnInfo(name = "colorRes") var colorResource: Int = 0)
