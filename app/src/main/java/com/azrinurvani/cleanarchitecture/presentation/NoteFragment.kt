package com.azrinurvani.cleanarchitecture.presentation

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.azrinurvani.cleanarchitecture.R
import com.azrinurvani.cleanarchitecture.databinding.FragmentNoteBinding
import com.azrinurvani.cleanarchitecture.framework.NoteViewModel
import com.azrinurvani.core.data.Note


class NoteFragment : Fragment() {


    private var _binding : FragmentNoteBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel : NoteViewModel
    private var currentNote = Note("","",0L,0L)

    private var noteId = 0L

    private lateinit var menuHost : MenuHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuHost = requireActivity()
        viewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        setupMenu()
        getArgs()
        getNoteById()
        listener()
        observeViewModel()
    }


    private fun getArgs(){
        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }
    }

    private fun getNoteById(){
        if (noteId!=0L){
            viewModel.getNote(noteId)
        }
    }

    private fun listener(){
        with(binding){
            this?.fabCheck?.setOnClickListener {
                if (this.etTitle.text.toString() != "" || etContent.text.toString() != ""){
                    val time = System.currentTimeMillis()
                    currentNote.title = etTitle.text.toString()
                    currentNote.content = etContent.text.toString()
                    currentNote.updateTime = time
                    if (currentNote.id == 0L){
                        currentNote.creationTime = time
                    }
                    viewModel.saveNote(currentNote)

                }else{
                    Navigation.findNavController(it).popBackStack()
                }
            }
        }
    }

    private fun observeViewModel(){
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(context, "Note Saved !", Toast.LENGTH_LONG).show()
                hideKeyboard()
                binding?.etTitle?.let { it1 -> Navigation.findNavController(it1).popBackStack() }
            } else {
                Toast.makeText(
                    context,
                    "Something went wrong,please try again !",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        viewModel.currentNote.observe(viewLifecycleOwner){ note ->
            note?.let {
                currentNote = it

                //The best way to change view from EditText to TextView and still CAN 2 modify
                binding?.etTitle?.setText(it.title,TextView.BufferType.EDITABLE)
                binding?.etContent?.setText(it.content,TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun hideKeyboard(){
        val imm : InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.etTitle?.windowToken,0)

    }

    private fun setupMenu(){
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.note_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.delete_note_menu ->{
                        dialogDeleteNote()
                        true
                    }else -> {
                        true
                    }
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }

    private fun dialogDeleteNote(){
        AlertDialog.Builder(context)
            .setTitle("Delete note")
            .setMessage("Are you sure to delete this note ?")
            .setPositiveButton("Yes") { dialogInterface, i ->
                viewModel.deleteNote(currentNote)
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.note_menu,menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when(item.itemId){
//            R.id.delete_note_menu ->{
//                if (context!=null && noteId !=0L){
//                    AlertDialog.Builder(context)
//                        .setTitle("Delete note")
//                        .setMessage("Are you sure to delete this note ?")
//                        .setPositiveButton("Yes") { dialogInterface, i ->
//                            viewModel.deleteNote(currentNote)
//                            dialogInterface.dismiss()
//                        }
//                        .setNegativeButton("Cancel") { dialogInterface, i ->
//                            dialogInterface.dismiss()
//                        }
//                        .create()
//                        .show()
//                }
//            }
//        }
//        return true
//
//    }

    companion object {
        private const val TAG = "NoteFragment"
    }
}