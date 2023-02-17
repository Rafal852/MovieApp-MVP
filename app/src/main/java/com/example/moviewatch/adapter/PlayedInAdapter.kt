package com.example.moviewatch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviewatch.R
import com.example.moviewatch.databinding.ItemPlayedInMovieBinding

import com.example.moviewatch.response.PlayedInListResponse

import com.example.moviewatch.utils.Constants.POSTER_BASE_URL
import javax.inject.Inject

class PlayedInAdapter @Inject constructor() : RecyclerView.Adapter<PlayedInAdapter.ViewHolder>(){

    private lateinit var binding: ItemPlayedInMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPlayedInMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(item: PlayedInListResponse.Cast) {
            binding.apply {
                val moviePosterURL = POSTER_BASE_URL + item?.poster_path
                itemImages.load(moviePosterURL) {
                    placeholder(R.drawable.ic_baseline_account_circle_24)
                    crossfade(true)
                    crossfade(800)
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener : ((PlayedInListResponse.Cast) -> Unit)? = null
    fun setonItemClickListener(listener: (PlayedInListResponse.Cast) -> Unit) {
        onItemClickListener=listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<PlayedInListResponse.Cast>() {
        override fun areItemsTheSame(oldItem: PlayedInListResponse.Cast, newItem: PlayedInListResponse.Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PlayedInListResponse.Cast, newItem: PlayedInListResponse.Cast): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}