package com.example.talesoftheunknown.view.Main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.databinding.ActivityMainBinding
import com.example.talesoftheunknown.view.Add.AddActivity
import com.example.talesoftheunknown.view.Auth.RegisterActivity
import com.example.talesoftheunknown.view.Story.DetailStoryActivity
import com.example.talesoftheunknown.view.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvStories: RecyclerView
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }

        setupView()
        setupAction()

        adapter = MainAdapter(emptyList())
        setupAdapter()

    }

    private fun setupAdapter() {
        binding.apply {
            rvStories.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStories.setHasFixedSize(true)
            rvStories.adapter = adapter
        }

        viewModel.getStories()
        viewModel.listStoryItem.observe(this) { stories ->
            Log.d("MainActivity", "Observer triggered with data: $stories")
            adapter.setList(stories)
            adapter.setOnItemClickFunction(object : MainAdapter.OnItemClickCallback{
                override fun onFunctionClicked(data: ListStoryItem){
                    Intent(this@MainActivity, DetailStoryActivity::class.java).also {
                        it.putExtra(DetailStoryActivity.EXTRA_USERNAME, data.name)
                        it.putExtra(DetailStoryActivity.EXTRA_ID, data.id)
                        it.putExtra(DetailStoryActivity.EXTRA_IMG, data.photoUrl)
                        it.putExtra(DetailStoryActivity.EXTRA_DESC, data.description)
                        it.putExtra(DetailStoryActivity.EXTRA_LON, data.lon)
                        it.putExtra(DetailStoryActivity.EXTRA_LAT, data.lat)
                        Toast.makeText(baseContext, "active", Toast.LENGTH_SHORT).show()
                        startActivity(it)
                    }
                }
            })

            rvStories.adapter = adapter
        }

        val addStory = binding.addButton
        addStory.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

}