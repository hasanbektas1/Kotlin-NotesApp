package com.hasanbektas.notesapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.room.Room
import com.hasanbektas.notesapp.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var db : NotesDatabase
    private lateinit var notesDao : NotesDao

    private lateinit var notes : Notes

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    private var noteId : Int = 0

    var selectedCate = ""

    private lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = arrayListOf("No Category","Personal","Work","Private","Travel")

        val adapterList = ArrayAdapter(this, R.layout.simple_spinner_item,category)
        binding.spinner.adapter = adapterList

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCate = category[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        db = Room.databaseBuilder(applicationContext,NotesDatabase::class.java,"dbNotes")
            .allowMainThreadQueries()
            .build()

        notesDao = db.locationDao()

        val intent = intent
        val info = intent.getStringExtra("info")
        noteId = intent.getIntExtra("selectedId",0)

        if (info=="new") {

            binding.deleteButton.visibility = View.INVISIBLE
            binding.updateButton.visibility = View.INVISIBLE

        }else{

            binding.saveButton.visibility = View.INVISIBLE

            notes = intent.getSerializableExtra("selectedNote") as Notes

            val selectedTitle = notes.title
            val selectedNote = notes.note

            binding.detailTitleText.setText(selectedTitle)
            binding.detailNoteText.setText(selectedNote)

        }
    }

    fun save (view:View) {

        val note=  Notes(binding.detailTitleText.text.toString(),binding.detailNoteText.text.toString(),currentDate,selectedCate)

        notesDao.insert(note)

        val intent = Intent(this@DetailActivity,MainActivity::class.java)
        startActivity(intent)
    }

    fun delete (view : View) {

        notes.let {
            notesDao.delete(it) }

        val intent = Intent(this@DetailActivity,MainActivity::class.java)
        startActivity(intent)
    }

    fun update (view:View){

        notes=  Notes(binding.detailTitleText.text.toString(),binding.detailNoteText.text.toString(),currentDate,selectedCate)

        notes.id = noteId

        notes.let {
            notesDao.update(notes) }

        val intent = Intent(this@DetailActivity,MainActivity::class.java)
        startActivity(intent)

    }
}