package com.example.moviewatch.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.moviewatch.adapter.*
import com.example.moviewatch.databinding.FragmentHomeBinding
import com.example.moviewatch.response.PopularMoviesListResponse
import com.example.moviewatch.response.GenresListResponse
import com.example.moviewatch.response.TopMoviesResponse
import com.example.moviewatch.response.UpcomingMoviesListResponse
import com.example.moviewatch.utils.CheckConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContracts.View {

    lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter

    @Inject
    lateinit var genreMoviesAdapter: GenreMoviesAdapter

    @Inject
    lateinit var popularMoviesAdapter: PopularMoviesAdapter

    @Inject
    lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter

    @Inject
    lateinit var showGenreResultAdapter: ShowGenreResultAdapter

    @Inject
    lateinit var homePresenter: HomePresenter

    private val pagerHelper: PagerSnapHelper by lazy { PagerSnapHelper() }


    private val checkConnection by lazy { CheckConnection(requireActivity().application) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homePresenter.callUpcomingMoviesList(1)
        homePresenter.callGenres()
        homePresenter.callPopularMoviesList(1)
        homePresenter.callTopRatedMoviesList(1)

    }

    override fun loadUpcomingMoviesList(data: UpcomingMoviesListResponse) {
        binding.apply {
            upcomingMoviesAdapter.differ.submitList(data.results)
            upcomingMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingMoviesAdapter
            }
            pagerHelper.attachToRecyclerView(upcomingMoviesRecycler)
            upcomingMoviesIndicator.attachToRecyclerView(upcomingMoviesRecycler, pagerHelper)
        }
        upcomingMoviesAdapter.setonItemClickListener {
            val direction = HomeFragmentDirections.actionToDetailFragment(it.id!!.toInt())
            findNavController().navigate(direction)
        }
    }

    override fun loadGenres(data: GenresListResponse) {
        binding.apply {
            genreMoviesAdapter.differ.submitList(data.genres)
            genresRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = genreMoviesAdapter
            }
            genreMoviesAdapter.setonItemClickListener {
                homePresenter.callMoviesGenres(1, it.id.toString())
            }
        }
    }

    override fun loadMoviesGenres(data: PopularMoviesListResponse) {
        binding.apply {

            lifecycleScope.launchWhenCreated {
                    showGenreResultAdapter.bind(data.results)
            }

            showGenresResultRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = showGenreResultAdapter
            }
        }
        showGenreResultAdapter.setonItemClickListener {
            val direction = HomeFragmentDirections.actionToDetailFragment(it.id)
            findNavController().navigate(direction)
        }
    }


    override fun loadPopularMoviesList(data: PopularMoviesListResponse) {
        binding.apply {

            lifecycleScope.launchWhenCreated {
                popularMoviesAdapter.bind(data.results)
            }

            popularMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = popularMoviesAdapter
            }

        }
        popularMoviesAdapter.setonItemClickListener {
            val direction = HomeFragmentDirections.actionToDetailFragment(it.id)
            findNavController().navigate(direction)
        }
    }

    override fun loadTopRatedMoviesList(data: TopMoviesResponse) {
        binding.apply {

            lifecycleScope.launchWhenCreated {
                topRatedMoviesAdapter.bind(data.results)
            }

            topRatedMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = topRatedMoviesAdapter
            }
            topRatedMoviesAdapter.setonItemClickListener {
                val direction = HomeFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }
        }
    }

    override fun showLoading() {
       binding.apply {
           moviesLoading.visibility = View.VISIBLE
           popularMoviesLayout.visibility = View.GONE
       }
    }

    override fun hideLoading() {
        binding.apply {
            moviesLoading.visibility = View.GONE
            popularMoviesLayout.visibility = View.VISIBLE
        }
    }

    override fun responseError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        homePresenter.onStop()
    }

}