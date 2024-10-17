package com.example.skillexchange.bodyapp.ui.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    val newSkills: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    fun addWord(newSkill: String) {
        val currentNewSkills = newSkills.value ?: mutableListOf()
        currentNewSkills.add(newSkill)
        newSkills.value = currentNewSkills
    }


    val mySkills: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    fun addMySkills(mySkill: String) {
        val currentMySkills = mySkills.value ?: mutableListOf()
        currentMySkills.add(mySkill)
        mySkills.value = currentMySkills
    }
}