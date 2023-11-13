package com.azrinurvani.cleanarchitecture.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.azrinurvani.cleanarchitecture.R
import com.azrinurvani.cleanarchitecture.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding

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
        listener()
    }

    private fun listener(){
        with(binding){
            this?.fabAddNote?.setOnClickListener {
                goToNoteDetail()
            }
        }
    }

    private fun goToNoteDetail(id : Long = 0){
        val action = ListFragmentDirections.actionListFragmentToNoteFragment()
        binding?.rvList?.let { Navigation.findNavController(it).navigate(action) }
    }

    companion object {
        private const val TAG = "ListFragment"
    }
}