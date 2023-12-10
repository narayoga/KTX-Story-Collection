package com.example.talesoftheunknown.data.Model.Story

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stories")
data class ListStoryItem(
	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null
)

//@Entity(tableName = "stories")
data class StoryResponse(
//	@PrimaryKey
//	@field:SerializedName("id")
//	val id: String,

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem?>? = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
