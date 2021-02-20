/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {
    /*=================================================================================================================
    Explanation for Viewmodel Instances
    * During configuration changes such as screen rotations, UI controllers such as fragments are re-created.
    * However, ViewModel instances survive. If you create the ViewModel instance using the ViewModel
    * class, a new object is created every time the fragment is re-created. Instead, create the ViewModel
    * instance using a ViewModelProvider.
    *
    * Important: Always use ViewModelProvider to create ViewModel objects rather than directly
    * instantiating an instance of ViewModel.
    * =================================================================================================================
    * How ViewModelProvider works:
    *
    * ViewModelProvider returns an existing ViewModel if one exists, or it creates a new one if it
    * does not already exist.
    *
    * ViewModelProvider creates a ViewModel instance in association with the given scope (an activity or a fragment).
    *
    * The created ViewModel is retained as long as the scope is alive. For example, if the scope is a fragment,
    * the ViewModel is retained until the fragment is detached.
    * */
    //=================================================================================================================
    //Create a reference to the GameViewModel inside the corresponding UI controller, which is gameFragment
    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProvider.get")

        /**
         * ViewModelProvider will get the viewmodel for this fragment is it exists, else it
         * will create a new one
         */
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        //========================================================================================================================
        //Attach an Observer object to the LiveData object for the current score, viewModel.score.
        /*viewModel.score.observe(viewLifecycleOwner, Observer{newScore ->
            binding.scoreText.text = newScore.toString()
        })*/
        //========================================================================================================================

        //Attach an Observer object to the current word LiveData object, Do it the same way you
        //attached an Observer object to the current score.
        //viewModel.word.observe(viewLifecycleOwner, Observer{newWord -> binding.wordText.text = newWord})
        //======================================================================================================================

        /*resetList() will be called from view model in init
        nextWord() will be called from view model in init*/

        //======================================================================================================================
        //Attach an observer object to the LiveData eventGameFinish boolean variable from GameViewModel
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean>{ hasFinished ->
            if(hasFinished == true){
                gameFinished()
            }
        })
        //========================================================================================================================

        /* // Remove click listeners as bind view to viewmodel======================================================
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }*/
        //===========================================================================================================

        /**
         * Binding of view directly to viewmodel, instead of using UI controllers to communicate,
         * which is through click listeners
         * e.g. The Button view and the GameViewModel don't communicate directly—they need the click
         * listener that's in the GameFragment.
         *
         * // Set the viewmodel for databinding - this allows the bound layout access
         * to all the data in the ViewModel then set the onclick in XML, then no need click listeners
        * */
        binding.gameViewModel = viewModel

        /*
        * // Specify the fragment view as the lifecycle owner of the binding.
        * // This is used so that the binding can observe LiveData updates
        * */
        binding.lifecycleOwner = viewLifecycleOwner //For binding to listen to livedata changes
        //===========================================================================================================

        return binding.root
    }

    //OnEndGame Button in the game
    private fun gameFinished()
    {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishComplete()
    }

    /*/** Methods for buttons presses- Remove as bind view to viewmodel**/
    private fun onSkip() {
        viewModel.onSkip()
        //updateWordText() //replaced by LiveData
        //updateScoreText() //replaced by LiveData
    }

    private fun onCorrect() {
        viewModel.onCorrect()
        //updateScoreText() //replaced by LiveData
        //updateWordText() //replaced by LiveData
    }

    private fun onEndGame()
    {
        gameFinished()
    }*/

    /*/** Methods for updating the UI - Removed as add in LiveData and LiveData Observers **/
    private fun updateWordText() {
        binding.wordText.text = viewModel.word.value.toString()
    }
    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.value.toString()
    }*/
}


/*
LiveData is an observable data holder class that is lifecycle-aware. For example,
you can wrap a LiveData around the current score in the GuessTheWord app. In this codelab,
you learn about several characteristics of LiveData:

LiveData is observable, which means that an observer is notified when the data held by the LiveData
object changes.

LiveData holds data; LiveData is a wrapper that can be used with any data

LiveData is lifecycle-aware. When you attach an observer to the LiveData, the observer is
associated with a LifecycleOwner (usually an Activity or Fragment). The LiveData only updates
observers that are in an active lifecycle state such as STARTED or RESUMED.
You can read more about LiveData and observation here.

In this task, you learn how to wrap any data type into LiveData objects by converting
the current score and current word data in the GameViewModel to LiveData.
 In a later task, you add an observer to these LiveData objects and learn how to observe the LiveData
 */