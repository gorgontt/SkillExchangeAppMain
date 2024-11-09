package com.example.skillexchange.models

sealed class ListItem {
    data class MainHeaderItem(val text: String) : ListItem()
    data class HeaderItem(val text: String) : ListItem()
    data class TextItem(val text: String) : ListItem()
//    data class ImageItem(val imageUrl: String) : ListItem()
}