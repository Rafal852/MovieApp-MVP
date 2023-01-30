package com.example.moviewatch.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.moviewatch.R
import com.example.moviewatch.adapter.DetailsGenreAdapter
import com.example.moviewatch.adapter.ImagesAdapter
import com.example.moviewatch.adapter.TrailerMovieAdapter
import com.example.moviewatch.databinding.FragmentDetailsBinding
import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.response.*
import com.example.moviewatch.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@AndroidEntryPoint
class DetailsFragment : Fragment(), DetailsContracts.View {

    private lateinit var binding: FragmentDetailsBinding

    private var movieId = 0
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var entity: MoviesEntity

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    @Inject
    lateinit var videoAdapter: TrailerMovieAdapter

    @Inject
    lateinit var genreAdapter: DetailsGenreAdapter

    @Inject
    lateinit var detailsPresenter: DetailsPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            movieId = args.movieId
            if (movieId > 0) {
                detailsPresenter.callDetailsMovie(movieId)
                detailsPresenter.callCreditsMovie(movieId)
                detailsPresenter.callVideoMovie(movieId)

            }


            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
        }


    }


    override fun loadDetailsMovie(data: DetailsMovieResponse) {
        binding.apply {
            val moviePosterURL = Constants.POSTER_BASE_URL + data.posterPath
            val backMoviePosterURL = Constants.POSTER_BACKDROP_URL + data.backdropPath
            posterBigImg.load(backMoviePosterURL)
            posterNormalImg.load(moviePosterURL) {
                crossfade(true)
                crossfade(800)
            }
            movieNameTxt.text = data.title
            movieRateTxt.text = data.voteAverage.toBigDecimal().setScale(1, RoundingMode.DOWN).toString()
            movieTimeTxt.text = "${data.runtime} min"
            movieDateTxt.text = data.releaseDate
            movieSummaryInfo.text = data.overview








            entity.id = movieId
            entity.poster = data.posterPath
            entity.lang = data.originalLanguage
            entity.title = data.title
            entity.rate = data.voteAverage.toString()
            entity.year = data.releaseDate

            detailsPresenter.checkFavorite(movieId)

        }

        binding.apply {
            genreAdapter.differ.submitList(data.genres)
            genreDetailsRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = genreAdapter
            }

        }
    }

    override fun loadCreditsMovie(data: CreditsLisResponse) {
        binding.apply {
            imagesAdapter.differ.submitList(data.cast)
            imagesRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = imagesAdapter
            }
        }
    }

    override fun loadVideosMovie(data: VideoListResponse) {
        binding.apply {
           videoAdapter.differ.submitList(data.results)
            trailerRv.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = videoAdapter
            }

        }
    }




    override fun updateFavorite(isAdded: Boolean) {
        binding.apply {
            //Click
            favImg.setOnClickListener {
                if (isAdded) {
                    detailsPresenter.deleteMovie(entity)
                } else {
                    detailsPresenter.saveMovie(entity)
                }
            }
            //Change color
            if (isAdded) {
                favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
            } else {
                favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
            }
        }
    }





    override fun showLoading() {
        binding.apply {
            detailLoading.visibility = View.VISIBLE
            detailScrollView.visibility = View.GONE
        }
    }

    override fun hideLoading() {
        binding.apply {
            detailLoading.visibility = View.GONE
            detailScrollView.visibility = View.VISIBLE
        }
    }

    override fun responseError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsPresenter.onStop()
    }


}