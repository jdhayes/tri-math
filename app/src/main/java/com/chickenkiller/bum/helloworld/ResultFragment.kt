package com.chickenkiller.bum.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.chickenkiller.bum.helloworld.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    var numCorrect: String? = "";
    var numWrong: String? = "";

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Clear title bar
        (activity as AppCompatActivity).supportActionBar?.title = ""

        numCorrect = "Crushed problems: " + arguments?.getInt("numCorrect")
        numWrong = "Difficult problems: " + arguments?.getInt("numWrong")

        // Inflate the layout for this fragment
        val binding =  DataBindingUtil.inflate<FragmentResultBinding>(
            inflater, R.layout.fragment_result, container, false)

        // Click Play
        binding.playBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_resultFragment_to_playFragment)
        }

        // Click quit
        binding.quitBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_resultFragment_to_titleFragment)
        }

        // Bind this fragment class to the layout
        binding.result = this;

        return binding.root;
    }
}