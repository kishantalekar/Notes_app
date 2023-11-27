package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes:List<Note>,context: Context):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private  var db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textTitleView: TextView = itemView.findViewById(R.id.titleTextView)
        var contentTextView :TextView = itemView.findViewById(R.id.contentTextView)
        var updateButton :ImageView= itemView.findViewById(R.id.updateButton)
        var deleteButton :ImageView = itemView.findViewById(R.id.deleteButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NoteViewHolder{

        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        var note = notes[position]
        holder.textTitleView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateNoteActivity::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNoteById(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "note deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int=notes.size

    fun refreshData(newNotes:List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }


}