package com.hasanbektas.notesapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class Notes (

    @ColumnInfo(name="title") var title:String,
    @ColumnInfo(name="note") var note : String,
    @ColumnInfo(name="date") var date : String,
    @ColumnInfo(name="categoryName") var categoryName : String

) : java.io.Serializable  {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}