package com.azrinurvani.cleanarchitecture.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.azrinurvani.cleanarchitecture.databinding.FragmentNoteBinding
import com.azrinurvani.cleanarchitecture.framework.NoteViewModel
import com.azrinurvani.core.data.Note


class NoteFragment : Fragment() {


    private var _binding : FragmentNoteBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel : NoteViewModel
    private var currentNote = Note("","",0L,0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        viewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]

        listener()
        observeViewModel()
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
    }

    private fun hideKeyboard(){
        val imm : InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.etTitle?.windowToken,0)

    }

    companion object {
        private const val TAG = "NoteFragment"
    }
}