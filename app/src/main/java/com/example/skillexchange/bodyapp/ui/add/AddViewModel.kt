package com.example.skillexchange.bodyapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddViewModel : ViewModel() {

    val words: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    fun addWord(word: String) {
        val currentWords = words.value ?: mutableListOf()
        currentWords.add(word)
        words.value = currentWords
    }


    val mySkills: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    fun addMySkills(mySkill: String) {
        val currentMySkills = mySkills.value ?: mutableListOf()
        currentMySkills.add(mySkill)
        mySkills.value = currentMySkills
    }
}