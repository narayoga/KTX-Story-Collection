package com.example.talesoftheunknown.view.Main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.talesoftheunknown.data.Model.Story.ListStoryItem
import com.example.talesoftheunknown.data.Model.Story.StoryResponse
import com.example.talesoftheunknown.databinding.ActivityMainBinding
import com.example.talesoftheunknown.databinding.RowMainBinding

class MainAdapter : PagingDataAdapter<ListStoryItem, MainAdapter.StoriesViewHolder>(DIFF_CALLBACK) {

//    private val list = ArrayList<ListStoryItem>()
    private var onItemClicked: OnItemClickCallback? = null

    fun setOnItemClickFunction(onItemClicked: OnItemClickCallback) {
        this.onItemClicked = onItemClicked
    }

    interface OnItemClickCallback {
        fun onFunctionClicked(data: ListStoryItem)
    }

    @SuppressLint("NotifyDataSetChanged")
//    fun setList(stories: ArrayList<ListStoryItem>) {
//        Log.d("MainAdapter", "setList called with data: $stories")
//        list.clear()
//        list.addAll(stories)
//        notifyDataSetChanged()
//    }

    inner class StoriesViewHolder(private val binding: RowMainBinding) : RecyclerView.ViewHolder(binding.root) {
        private var photoUrl: ImageView = binding.profileImageView
        private var tvName: TextView = binding.nameTextView
        private var tvDescription: TextView = binding.descTextView

        fun bind(story: ListStoryItem) {
            Glide.with(itemView)
                .load(story.photoUrl)
                .into(photoUrl)
            tvName.text = story.name
            tvDescription.text = story.description

            // Set click listener here
            itemView.setOnClickListener {
                onItemClicked?.onFunctionClicked(story)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val binding = RowMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
//        holder.bind(list[position])
        Log.d("mitochondria", "adapter")
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

//    override fun getItemCount(): Int = list.size

    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

//class MainAdapter :
//    PagingDataAdapter<ListStoryItem, MainAdapter.StoriesViewHolder>(DIFF_CALLBACK) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
//        val binding = RowMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return StoriesViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
//        val data = getItem(position)
//        if (data != null) {
//            holder.bind(data)
//        }
//    }
//
//    class StoriesViewHolder(private val binding: RowMainBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: ListStoryItem ) {
//            binding.nameTextView.text = data.name
//            binding.descTextView.text = data.description
//        }
//    }
//
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
//            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
//                return true
//            }
//        }
//    }
//}

//class MainAdapter :
//    PagingDataAdapter<StoryResponse, MainAdapter.StoriesViewHolder>(DIFF_CALLBACK) {
//
//    private val list = ArrayList<ListStoryItem>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
//        val binding = RowMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return StoriesViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
//        val data = getItem(position)
//        if (data != null) {
//            holder.bind(data)
//        }
//    }
//
//    class StoriesViewHolder(private val binding: RowMainBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: ListStoryItem ) {
//            binding.nameTextView.text = data.name
//            binding.descTextView.text = data.description
//        }
//    }
//
//    override fun getItemCount(): Int = list.size
//
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponse>() {
//            override fun areItemsTheSame(oldItem: StoryResponse, newItem: StoryResponse): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: StoryResponse, newItem: StoryResponse): Boolean {
//                return true
//            }
//        }
//    }
//}


//class MainAdapter(emptyList: List<Any>) : RecyclerView.Adapter<MainAdapter.StoriesViewHolder>() {
//
//    private val list = ArrayList<ListStoryItem>()
//    private var onItemClicked: OnItemClickCallback? = null
//
//    fun setOnItemClickFunction(onItemClicked: OnItemClickCallback) {
//        this.onItemClicked = onItemClicked
//    }
//
//    interface OnItemClickCallback {
//        fun onFunctionClicked(data: ListStoryItem)
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setList(stories: ArrayList<ListStoryItem>) {
//        Log.d("MainAdapter", "setList called with data: $stories")
//        list.clear()
//        list.addAll(stories)
//        notifyDataSetChanged()
//    }
//
//    inner class StoriesViewHolder(private val binding: RowMainBinding) : RecyclerView.ViewHolder(binding.root) {
//        private var photoUrl: ImageView = binding.profileImageView
//        private var tvName: TextView = binding.nameTextView
//        private var tvDescription: TextView = binding.descTextView
//
//        fun bind(story: ListStoryItem) {
//            Glide.with(itemView)
//                .load(story.photoUrl)
//                .into(photoUrl)
//            tvName.text = story.name
//            tvDescription.text = story.description
//
//            // Set click listener here
//            itemView.setOnClickListener {
//                onItemClicked?.onFunctionClicked(story)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
//        val binding = RowMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return StoriesViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
//        holder.bind(list[position])
//    }
//
//    override fun getItemCount(): Int = list.size
//}

