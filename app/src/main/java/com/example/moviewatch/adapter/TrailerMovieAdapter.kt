package com.example.moviewatch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviewatch.R
import com.example.moviewatch.databinding.ItemDetailImagesBinding
import com.example.moviewatch.databinding.ItemVideoBinding
import com.example.moviewatch.response.CreditsLisResponse
import com.example.moviewatch.response.VideoListResponse
import com.example.moviewatch.utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import javax.inject.Inject

class TrailerMovieAdapter @Inject constructor() : RecyclerView.Adapter<TrailerMovieAdapter.ViewHolder>(){

    private lateinit var binding: ItemVideoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    //gets the last 5 items in reverse order
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[differ.currentList.size - 1 - position])
        holder.setIsRecyclable(false)
    }

    //checks if the size of the list is greater than 5. If it is, then it returns only the last 5
    override fun getItemCount() = if (differ.currentList.size > 5) 5 else differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(item: VideoListResponse.Result) {
            binding.apply {
                trailerThumbnailIv.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        item.key.let { youTubePlayer.cueVideo(it, 0f)}
                    }
                })
                tvVideoTitle.text = item.name
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<VideoListResponse.Result>() {
        override fun areItemsTheSame(oldItem: VideoListResponse.Result, newItem: VideoListResponse.Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: VideoListResponse.Result, newItem: VideoListResponse.Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}