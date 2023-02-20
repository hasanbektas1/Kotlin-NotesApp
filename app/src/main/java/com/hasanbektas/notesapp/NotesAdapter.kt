package com.hasanbektas.notesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasanbektas.notesapp.databinding.RecyclerRowBinding

class NotesAdapter (val noteList:List<Notes>): RecyclerView.Adapter<NotesAdapter.NoteHolder>() {

    class NoteHolder(val recyclerRowBinding: RecyclerRowBinding): RecyclerView.ViewHolder(recyclerRowBinding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {

        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteHolder(recyclerRowBinding)

    }
    override fun onBindViewHolder(holder: NoteHolder, position: Int) {

        holder.recyclerRowBinding.recyclerRowTitleText.text = noteList.get(position).title
        holder.recyclerRowBinding.recyclerRowNoteText.text = noteList.get(position).note
        holder.recyclerRowBinding.recyclerDateText.text = noteList.get(position).date
        holder.recyclerRowBinding.recyclerctText.text = noteList.get(position).categoryName

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra("selectedNote",noteList.get(position))
            intent.putExtra("selectedId",noteList.get(position).id)
            intent.putExtra("info","old")
            holder.itemView.context.startActivity(intent)

        }
    }
    override fun getItemCount(): Int {
        return noteList.size
    }
}