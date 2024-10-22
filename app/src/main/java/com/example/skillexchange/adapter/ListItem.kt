package com.example.skillexchange.adapter

sealed class ListItem {
    data class HeaderItem(val text: String) : ListItem()
    data class TextItem(val text: String) : ListItem()
//    data class ImageItem(val imageUrl: String) : ListItem()
}