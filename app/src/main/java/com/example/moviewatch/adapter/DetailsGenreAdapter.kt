package com.example.moviewatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviewatch.R
import com.example.moviewatch.databinding.ItemHomeGenreListBinding
import com.example.moviewatch.response.DetailsMovieResponse
import com.example.moviewatch.response.GenresListResponse
import javax.inject.Inject

class DetailsGenreAdapter @Inject constructor() : RecyclerView.Adapter<DetailsGenreAdapter.ViewHolder>() {
    private lateinit var binding: ItemHomeGenreListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemHomeGenreListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.itemView.setBackgroundResource(R.drawable.bg_rounded_dark)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int =differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){
        fun setData(item : DetailsMovieResponse.Genre){
            binding.apply {
                nameTxt.text=item.name
            }
        }
    }


    private val diffCallback = object : DiffUtil.ItemCallback<DetailsMovieResponse.Genre>(){
        override fun areItemsTheSame(oldItem: DetailsMovieResponse.Genre, newItem: DetailsMovieResponse.Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailsMovieResponse.Genre, newItem: DetailsMovieResponse.Genre): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

}