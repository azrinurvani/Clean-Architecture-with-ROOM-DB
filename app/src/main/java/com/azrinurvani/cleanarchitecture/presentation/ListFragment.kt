package com.azrinurvani.cleanarchitecture.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.azrinurvani.cleanarchitecture.R
import com.azrinurvani.cleanarchitecture.databinding.FragmentListBinding
import com.azrinurvani.cleanarchitecture.framework.ListViewModel


class ListFragment : Fragment(), ListAction {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding

    private val noteListAdapter = NotesListAdapter(arrayListOf(),this)
    private lateinit var viewModel : ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        viewModel = ViewModelProviders.of(this)[ListViewModel::class.java]

        listener()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.notes.observe(viewLifecycleOwner){ notesList ->
            binding?.pbLoading?.visibility = View.GONE
            binding?.rvList?.visibility = View.VISIBLE
            noteListAdapter.updateNotes(notesList.sortedByDescending { it.updateTime })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun setupAdapter(){
        binding?.rvList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
        }
    }

    private fun listener(){
        with(binding){
            this?.fabAddNote?.setOnClickListener {
                goToNoteDetail()
            }
        }
    }

    private fun goToNoteDetail(id : Long = 0L){
        val action = ListFragmentDirections.actionListFragmentToNoteFragment(id)
        binding?.rvList?.let { Navigation.findNavController(it).navigate(action) }
    }
    override fun onClick(id: Long) {
        goToNoteDetail(id)
    }
}