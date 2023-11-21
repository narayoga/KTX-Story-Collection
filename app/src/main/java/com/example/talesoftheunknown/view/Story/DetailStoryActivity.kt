package com.example.talesoftheunknown.view.Story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.talesoftheunknown.R
import com.example.talesoftheunknown.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_IMG = "extra_img"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_LON = "extra_lon"
        const val EXTRA_LAT = "extra_lat"
    }

    private lateinit var binding: ActivityDetailStoryBinding // Update the binding type to the generated type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent extras
        val userId = intent.getIntExtra(EXTRA_ID, 0) // Assuming ID is an Int, replace with the appropriate type
        val userImgUrl = intent.getStringExtra(EXTRA_IMG)
        val userName = intent.getStringExtra(EXTRA_USERNAME)
        val userDesc = intent.getStringExtra(EXTRA_DESC)
        val userLon = intent.getDoubleExtra(EXTRA_LON, 0.0) // Assuming Lon is a Double, replace with the appropriate type
        val userLat = intent.getDoubleExtra(EXTRA_LAT, 0.0) // Assuming Lat is a Double, replace with the appropriate type

        // Populate UI components with data
        Glide.with(this)
            .load(userImgUrl)
            .into(binding.ivProfileImage)

        binding.tvName.text = userName
        binding.tvDescription.text = userDesc
        binding.tvLat.text = userLat.toString()
        binding.tvLon.text = userLon.toString()
    }
}
