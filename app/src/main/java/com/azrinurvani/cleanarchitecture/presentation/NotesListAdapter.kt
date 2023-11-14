package com.azrinurvani.cleanarchitecture.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.azrinurvani.cleanarchitecture.databinding.ItemNoteBinding
import com.azrinurvani.core.data.Note
import java.text.SimpleDateFormat
import java.util.Date

class NotesListAdapter(var notes : ArrayList<Note>,val actions: ListAction) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(note = notes[position])
    }

    fun updateNotes(newNotes: List<Note>){
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }


    inner class NoteViewHolder(private val binding : ItemNoteBinding) : ViewHolder(binding.root){

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(note:Note){
            with(binding){
                tvTitle.text = note.title
                tvContent.text = note.content

                val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
                val resultDate = Date(note.updateTime)

                tvDate.text = "Last updated : ${sdf.format(resultDate)}"
                this.noteLayout.setOnClickListener {
                    actions.onClick(id = note.id)
                }
            }
        }
    }



}