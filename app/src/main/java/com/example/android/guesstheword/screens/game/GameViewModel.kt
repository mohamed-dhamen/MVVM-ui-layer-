/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.sql.Time

// TODO (02) Create the GameViewModel class, extending ViewModel
// TODO (03) Add init and override onCleared; Add log statements to both


class GameViewModel:ViewModel(){
    // The current word
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }
    private  val _Timer: CountDownTimer

    private val _courentTime=MutableLiveData<Long>()
    val courentTimer:LiveData<Long>
        get()= _courentTime

    val courentTimleString =Transformations.map(courentTimer){newTime->
        DateUtils.formatElapsedTime(newTime)
    }

    private val _eventGameFinish=MutableLiveData<Boolean>()
    val eventGameFinish :LiveData<Boolean>
        get()=_eventGameFinish

    private val _word = MutableLiveData<String>()
    val word:LiveData<String>
        get()=_word

    // The current score
     private val _score =MutableLiveData<Int>()
    val score : LiveData<Int>
            get()=_score

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

        init {
            Log.i("GameViewModel","Game View Model Created")
            _eventGameFinish.value=false
            resetList()
            nextWord()
            _score.value=0
            _word.value=""
            _Timer= object : CountDownTimer(COUNTDOWN_TIME,ONE_SECOND) {

                override fun onTick(millisUntilFinished: Long) {
                    _courentTime.value=(millisUntilFinished/ ONE_SECOND)
                    Log.i("GameViewModel",millisUntilFinished.toString())

                }
                override fun onFinish() {
                    _courentTime.value= DONE
                    _eventGameFinish.value=true

                }
            }
            _Timer.start()

        }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","Game View Model Destroyed")
        _Timer.cancel()
    }


    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _eventGameFinish.value=true
        }else{
            _word.value = wordList.removeAt(0)
        }


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

     fun onSkip() {
        _score.value=(score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value=(score.value)?.plus(1)
        nextWord()
    }
 fun onGameFinishComplete(){
     _eventGameFinish.value=false
 }


}