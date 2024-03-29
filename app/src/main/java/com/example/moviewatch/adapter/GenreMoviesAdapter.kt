package com.example.moviewatch.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviewatch.R
import com.example.moviewatch.databinding.ItemHomeGenreListBinding
import com.example.moviewatch.response.GenresListResponse.Genre
import javax.inject.Inject

class GenreMoviesAdapter @Inject constructor() : RecyclerView.Adapter<GenreMoviesAdapter.ViewHolder>() {
    private lateinit var binding: ItemHomeGenreListBinding

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemHomeGenreListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.itemView.setBackgroundResource(R.drawable.bg_rounded_dark)
        // Set background color of selected item
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.bg_rounded_light)
        }
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int =differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        fun setData(item : Genre){
            binding.apply {
                nameTxt.text=item.name

                root.setOnClickListener {
                    onItemClickListener?.let {
                        // Save the previous selected position
                        val previousPosition = selectedPosition
                        // Update the selected position
                        selectedPosition = adapterPosition
                        // Notify the adapter of the changes
                        notifyDataSetChanged()
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener : ((Genre) -> Unit)? = null
    fun setonItemClickListener(listener: (Genre) -> Unit) {
        onItemClickListener=listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Genre>(){
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

}