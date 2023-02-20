package com.hasanbektas.notesapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hasanbektas.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var noteAdapter : NotesAdapter

    private lateinit var db : NotesDatabase
    private lateinit var notesDao : NotesDao

    var selectedCate = ""

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = Room.databaseBuilder(applicationContext,NotesDatabase::class.java,"dbNotes")
            .allowMainThreadQueries()
            .build()
        notesDao = db.locationDao()

        val noteList = notesDao.getAll()

        val category = arrayListOf("All List","No Category","Personal","Work","Private","Travel")

        val adapterList = ArrayAdapter(this, R.layout.simple_spinner_item,category)
        binding.spinnerMain.adapter = adapterList

        binding.spinnerMain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCate = category[position]

                if (selectedCate == "All List"){

                    noteAdapter = NotesAdapter(noteList.reversed())
                    binding.recyclerViewList.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.recyclerViewList.adapter = noteAdapter

                }else{
                    val cateList = notesDao.categoryNote(selectedCate)

                    noteAdapter = NotesAdapter(cateList)
                    binding.recyclerViewList.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.recyclerViewList.adapter = noteAdapter
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this@MainActivity,DetailActivity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
    }
}
