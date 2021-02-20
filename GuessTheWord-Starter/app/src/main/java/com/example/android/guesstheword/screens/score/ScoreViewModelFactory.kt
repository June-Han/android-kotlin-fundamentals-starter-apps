package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/*
* When the user ends the game, the ScoreFragment does not show the score.
* You want a ViewModel to hold the score to be displayed by the ScoreFragment.
* You'll pass in the score value during the ViewModel initialization using the factory method pattern.
* The factory method pattern is a creational design pattern that uses factory methods to create objects.
* A factory method is a method that returns an instance of the same class.
* In this task, you create a ViewModel with a parameterized constructor for the score fragment and
* a factory method to instantiate the ViewModel.
* */
class ScoreViewModelFactory(private val finalScore: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}