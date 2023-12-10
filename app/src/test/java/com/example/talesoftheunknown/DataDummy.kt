package com.example.talesoftheunknown

import com.example.talesoftheunknown.data.Model.Story.ListStoryItem

object DataDummy {
    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                i.toString(),
                "name + $i",
                "description $i",
                -83.9882238,
                42.4344529

            )
            items.add(quote)
        }
        return items
    }
}