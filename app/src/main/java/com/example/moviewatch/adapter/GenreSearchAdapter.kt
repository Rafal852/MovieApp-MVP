package com.example.moviewatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviewatch.R
import com.example.moviewatch.databinding.ItemGenresSearchBinding
import com.example.moviewatch.response.GenresListResponse
import javax.inject.Inject

class GenreSearchAdapter @Inject constructor() : RecyclerView.Adapter<GenreSearchAdapter.ViewHolder>() {
    private lateinit var binding: ItemGenresSearchBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemGenresSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }


    override fun onBindViewHolder(holder: GenreSearchAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.itemView.setBackgroundResource(R.drawable.bg_rounded_dark)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int =differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){

        fun setData(item : GenresListResponse.Genre){
            binding.apply {
                nameTxt.text=item.name

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener : ((GenresListResponse.Genre) -> Unit)? = null
    fun setonItemClickListener(listener: (GenresListResponse.Genre) -> Unit) {
        onItemClickListener=listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<GenresListResponse.Genre>(){
        override fun areItemsTheSame(oldItem: GenresListResponse.Genre, newItem: GenresListResponse.Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenresListResponse.Genre, newItem: GenresListResponse.Genre): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

}