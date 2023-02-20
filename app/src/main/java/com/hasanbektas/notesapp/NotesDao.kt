package com.hasanbektas.notesapp

import androidx.room.*

@Dao
interface  NotesDao {

    @Query("SELECT *  FROM notes_table ORDER BY id ASC")

    fun getAll(): List<Notes>

    @Insert
    fun insert(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Update
    fun update(notes: Notes)

    @Query("SELECT * FROM notes_table WHERE categoryName LIKE :query ")
    fun categoryNote (query: String ) : List<Notes>

}