package com.example.skillexchange.interfaces

import com.example.skillexchange.models.ListItem

interface OnSkillsSelectedListener {
    fun onSkillsSelected(selectedSkills: List<ListItem.TextItem>)
}