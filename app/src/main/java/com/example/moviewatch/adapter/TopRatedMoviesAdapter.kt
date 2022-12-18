package com.example.moviewatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.moviewatch.databinding.ItemMoviesPopularBinding
import com.example.moviewatch.response.TopMoviesResponse
import com.example.moviewatch.utils.Constants
import javax.inject.Inject

class TopRatedMoviesAdapter @Inject constructor() : RecyclerView.Adapter<TopRatedMoviesAdapter.ViewHolder>() {

    private lateinit var binding: ItemMoviesPopularBinding
    private var moviesList = emptyList<TopMoviesResponse.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMoviesPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(moviesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = moviesList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: TopMoviesResponse.Result) {
            binding.apply {


                val moviePosterURL = Constants.POSTER_BASE_URL + item?.poster_path
                moviePosterImg.load(moviePosterURL) {
                    crossfade(true)
                    crossfade(800)
                    scale(Scale.FIT)
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener : ((TopMoviesResponse.Result) -> Unit)? = null
    fun setonItemClickListener(listener: (TopMoviesResponse.Result) -> Unit) {
        onItemClickListener=listener
    }

    fun bind(data:List<TopMoviesResponse.Result>){
        val moviesDiffUtils = MoviesDiffUtils(moviesList,data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        moviesList=data
        diffUtils.dispatchUpdatesTo(this)
    }

    //callback
    class MoviesDiffUtils(private val oldItem: List<TopMoviesResponse.Result>, private val newItem:List<TopMoviesResponse.Result>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // === data type is compred here
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }
}