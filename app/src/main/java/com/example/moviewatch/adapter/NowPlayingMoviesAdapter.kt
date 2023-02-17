package com.example.moviewatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.moviewatch.databinding.NowPlayingMovieBinding
import com.example.moviewatch.response.NowPlayingResponse
import com.example.moviewatch.response.TopMoviesResponse
import com.example.moviewatch.utils.Constants
import javax.inject.Inject

class NowPlayingMoviesAdapter @Inject constructor() : RecyclerView.Adapter<NowPlayingMoviesAdapter.ViewHolder>() {
    private lateinit var binding: NowPlayingMovieBinding

    private var moviesList = emptyList<NowPlayingResponse.Result>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= NowPlayingMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(moviesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = moviesList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: NowPlayingResponse.Result) {
            binding.apply {


                val moviePosterURL = Constants.POSTER_BASE_URL + item?.backdrop_path
                itemImages.load(moviePosterURL) {
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

    private var onItemClickListener : ((NowPlayingResponse.Result) -> Unit)? = null
    fun setonItemClickListener(listener: (NowPlayingResponse.Result) -> Unit) {
        onItemClickListener=listener
    }

    fun bind(data:List<NowPlayingResponse.Result>){
        val moviesDiffUtils = MoviesDiffUtils(moviesList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        moviesList=data
        diffUtils.dispatchUpdatesTo(this)
    }

    //callback
    class MoviesDiffUtils(private val oldItem: List<NowPlayingResponse.Result>, private val newItem:List<NowPlayingResponse.Result>) : DiffUtil.Callback(){
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