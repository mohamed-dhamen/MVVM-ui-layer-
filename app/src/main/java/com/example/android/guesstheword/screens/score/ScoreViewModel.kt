package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(score:Int):ViewModel() {

    private  val _score=MutableLiveData<Int>()
    val score :LiveData<Int>
        get()=_score

    private val _eventplayagain=MutableLiveData<Boolean>()
    val eventplayagain:LiveData<Boolean>
            get()=_eventplayagain
    init {
        Log.i("ScoreViewModel",score.toString())
        _score.value=score

    }

    fun eventPlayAgain(){
        _eventplayagain.value=false
    }

    fun eventPlayAgainComplet(){
        _eventplayagain.value=true
    }

}