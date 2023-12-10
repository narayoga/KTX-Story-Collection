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
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talesoftheunknown.Injection
import com.example.talesoftheunknown.R
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.databinding.ActivityMainBinding
import com.example.talesoftheunknown.view.Add.AddActivity
import com.example.talesoftheunknown.view.Auth.RegisterActivity
import com.example.talesoftheunknown.view.Maps.MapsActivity
import com.example.talesoftheunknown.view.Story.DetailStoryActivity
import com.example.talesoftheunknown.view.ViewModelFactory
import okhttp3.internal.notify

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this, Injection.providePagingRepository(this))
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvStories: RecyclerView
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvStories = binding.rvStories
        viewModel.getSession().observe(this) { user ->
            Log.d("mito", user.toString())
            if (!user.isLogin) {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }


        setupView()
        setupAdapter()
        setupAction()
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

    private fun setupAdapter() {
        adapter = MainAdapter()
        Log.d("mitochondria","masuk setupAdapter")
        rvStories.layoutManager = LinearLayoutManager(this@MainActivity)
        rvStories.setHasFixedSize(true)
//            rvStories.adapter = adapter
        rvStories.adapter = adapter.withLoadStateFooter(
            footer = MainAdapterLoading {
                adapter.retry()
            }
        )


        viewModel.story.observe(this) {
            Log.d("mitochondria","masuk mainActivity ${it.toString()}")
            adapter.submitData(lifecycle, it)
            adapter.notifyDataSetChanged()
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
        }
//        viewModel.listStoryItem.observe(this) { stories ->
//            Log.d("MainActivity", "Observer triggered with data: $stories")
//            adapter.setList(stories)
//
//
//            rvStories.adapter = adapter
//        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }


//    private fun setupAdapter() {
//        adapter = MainAdapter(emptyList())
//        binding.rvStories.adapter = adapter.withLoadStateFooter(
//            footer = MainAdapterLoading {
//                adapter.retry()
//            }
//        )
//        viewModel.story.observe(this) {
//            println("masuk mainActivity")
//            adapter.submitData(lifecycle, it)
//        }
//    }

//    private fun setupAdapter() {
//        binding.apply {
//            rvStories.layoutManager = LinearLayoutManager(this@MainActivity)
//            rvStories.setHasFixedSize(true)
//            rvStories.adapter = adapter
//        }
//
//        viewModel.getStories()
//        viewModel.listStoryItem.observe(this) { stories ->
//            Log.d("MainActivity", "Observer triggered with data: $stories")
//            adapter.setList(stories)
//            adapter.setOnItemClickFunction(object : MainAdapter.OnItemClickCallback{
//                override fun onFunctionClicked(data: ListStoryItem){
//                    Intent(this@MainActivity, DetailStoryActivity::class.java).also {
//                        it.putExtra(DetailStoryActivity.EXTRA_USERNAME, data.name)
//                        it.putExtra(DetailStoryActivity.EXTRA_ID, data.id)
//                        it.putExtra(DetailStoryActivity.EXTRA_IMG, data.photoUrl)
//                        it.putExtra(DetailStoryActivity.EXTRA_DESC, data.description)
//                        it.putExtra(DetailStoryActivity.EXTRA_LON, data.lon)
//                        it.putExtra(DetailStoryActivity.EXTRA_LAT, data.lat)
//                        Toast.makeText(baseContext, "active", Toast.LENGTH_SHORT).show()
//                        startActivity(it)
//                    }
//                }
//            })
//
//            rvStories.adapter = adapter
//        }
//    }

    private fun setupAction() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add -> {
                    val intent = Intent(this, AddActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.map -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

}