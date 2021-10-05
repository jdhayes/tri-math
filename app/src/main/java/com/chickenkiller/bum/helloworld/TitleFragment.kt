package com.chickenkiller.bum.helloworld

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.chickenkiller.bum.helloworld.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Set title
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container,false)
        //The complete onClickListener with Navigation
        binding.startBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_playFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }
}