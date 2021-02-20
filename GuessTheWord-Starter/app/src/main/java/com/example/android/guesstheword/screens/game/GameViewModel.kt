package com.example.android.guesstheword.screens.game

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // The current word with backing property on LiveData. (Encapsulation)
    //_word property is now mutable version of game word to be used internally.
    private val _word = MutableLiveData<String>()
    //public version of the LiveData Word
    val word : LiveData<String>get() = _word
//=================================================================================================

    // The current score with backing property on LiveData. (Encapsulation)
    //_score property is now mutable version of game score to be used internally.
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>get() = _score
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>
//=================================================================================================

    //Event Which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean> get() = _eventGameFinish

    fun onGameFinished() {
        _eventGameFinish.value = true
    }

    /**
     * Method for Game Completed Event
     * */
    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    init{
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()
        Log.i("GameViewModel", "GameViewModel created!")
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (!wordList.isEmpty()) {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }else{
            onGameFinished()
        }
    }

    //OnSkip button in the game
    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    //OnCorrect button in the game
    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    /*
    * The ViewModel is destroyed when the associated fragment is detached,
    * or when the activity is finished. Right before the ViewModel is destroyed,
    * the onCleared() callback is called to clean up the resources.
    * 1. In the GameViewModel class, override the onCleared() method.
    * 2. Add a log statement inside onCleared() to track the GameViewModel lifecycle.
    * */
    override fun onCleared()
    {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}