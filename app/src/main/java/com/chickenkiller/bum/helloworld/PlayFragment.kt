package com.chickenkiller.bum.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.chickenkiller.bum.helloworld.databinding.FragmentPlayBinding
import kotlin.random.Random
import androidx.core.os.bundleOf


class PlayFragment : Fragment() {
    val max = 10;
    val min = 1;
    val probCount = 12;
    var probCurrent = 0
    lateinit var top: String;
    lateinit var right: String;
    lateinit var left: String;
    private var problems: MutableList<List<Int>>? = null;
    var numCorrect = 0;
    var numWrong = 0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Display remaining questions in title
        (activity as AppCompatActivity).supportActionBar?.title = "Problem "+probCurrent+1 +"/"+probCount

        // Create problem list
        if (problems.isNullOrEmpty()) {
            makeProblemList();
            left = problems?.first()?.get(0).toString()
            right = problems?.first()?.get(1).toString()
            top = "X"
        }

        // Inflate the layout for this fragment
        val binding =  DataBindingUtil.inflate<FragmentPlayBinding>(
            inflater, R.layout.fragment_play, container, false)

        // Bind this fragment class to the layout
        binding.play = this;

        // Set the onClickListener for the submitButton
        binding.playBtn.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            if (probCount > probCurrent) {

                //Increment problem number
                probCurrent += 1;

                // Check answer
                 if (correct(
                         binding.answer.text.toString(),
                         binding.textTop.text.toString(),
                         binding.textLeft.text.toString(),
                         binding.textRight.text.toString())
                 ){
                     numCorrect += 1
                 } else {
                     numWrong += 1
                 }

                // Set values in layout
                problems?.removeAt(0)

                var mask: Int = Random.nextInt(1, 3)

                binding.answer.text.clear()
                left = problems?.first()?.get(0).toString()
                right = problems?.first()?.get(1).toString()
                top = problems?.first()?.get(2).toString()

                // Set problem triangle
                when (mask) {
                    1 -> {
                        left = "X"
                    }
                    2 -> {
                        right = "X"
                    }
                    3 -> {
                        top = "X"
                    }
                }

                // Display remaining questions in title
                (activity as AppCompatActivity).supportActionBar?.title = "Problem $probCurrent/$probCount"

                // Redraw fragment
                binding.invalidateAll();
            } else {
                val bundle = bundleOf("numCorrect" to numCorrect, "numWrong" to numWrong)

                //Pass bundle to fragment
                view.findNavController().navigate(R.id.action_playFragment_to_resultFragment, bundle)
            }
        }
        return binding.root;
    }

    private fun correct(ans: String, top: String, left: String, right: String): Boolean {
        var answer: Int

        if (top == "X") {
            answer = Integer.parseInt(left) * Integer.parseInt(right)
            if (answer == Integer.parseInt(ans)) { return true}
        }
        else if (left == "X") {
            answer = Integer.parseInt(ans) * Integer.parseInt(right)
            if (answer == Integer.parseInt(top)) { return true}
        }
        else if (right == "X") {
            answer = Integer.parseInt(left) * Integer.parseInt(ans)
            if (answer == Integer.parseInt(top)) { return true}
        }
        return false
    }

    private fun makeProblemList() {
        problems = mutableListOf();
        for  (i in  0..probCount) {
            problems!!.add(generateProblem());
        }
    }

    private fun generateProblem(): List<Int> {
        val numLeft = Random.nextInt(max - min + 1) + min;
        val numRight = Random.nextInt(max - min + 1) + min;
        val numTop = numLeft * numRight;

        return listOf(numLeft,numRight,numTop)
    }

}