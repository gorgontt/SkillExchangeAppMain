package com.example.skillexchange.interfaces

import com.example.skillexchange.adapter.ListItem

interface OnSkillsSelectedListener {
    fun onSkillsSelected(selectedSkills: List<ListItem.TextItem>)
}