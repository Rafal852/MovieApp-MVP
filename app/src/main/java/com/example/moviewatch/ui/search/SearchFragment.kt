package com.example.moviewatch.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviewatch.adapter.GenreSearchAdapter

import com.example.moviewatch.adapter.NowPlayingMoviesAdapter
import com.example.moviewatch.adapter.PopularMoviesAdapter
import com.example.moviewatch.databinding.FragmentSearchBinding
import com.example.moviewatch.layoutManager.ScaleCenterItemLayoutManager
import com.example.moviewatch.response.*
import com.example.moviewatch.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchContracts.View {

    private lateinit var binding: FragmentSearchBinding



    @Inject
    lateinit var nowPlayingMoviesAdapter: NowPlayingMoviesAdapter

    @Inject
    lateinit var commonMoviesAdapter: PopularMoviesAdapter

    @Inject
    lateinit var searchPresenter: SearchPresenter

    @Inject
    lateinit var genreSearchAdapter: GenreSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchPresenter.callNowPlayingMovieList(1)
        searchPresenter.callGenres()

        binding.apply {
            searchEdt.addTextChangedListener {
                val search = it.toString()
                if (search.isEmpty()) {
                    nowPlayingTxt.visibility = View.VISIBLE
                    nowPlayingRecycler.visibility = View.VISIBLE
                    genresSearchRecycler.visibility = View.VISIBLE
                    seeAllTxt.visibility = View.VISIBLE
                    moviesRecycler.visibility = View.GONE
                }else{
                    moviesRecycler.visibility = View.VISIBLE
                    searchPresenter.callSearchMoviesList(1,search)
                    nowPlayingTxt.visibility = View.GONE
                    nowPlayingRecycler.visibility = View.GONE
                    genresSearchRecycler.visibility = View.GONE
                    seeAllTxt.visibility = View.GONE
                }
            }
        }
    }

    override fun loadSearchMoviesList(data: PopularMoviesListResponse) {
        binding.apply {

            commonMoviesAdapter.bind(data.results)

            moviesRecycler.apply {
                layoutManager = GridLayoutManager(requireContext(),3 )
                adapter = commonMoviesAdapter
            }

            commonMoviesAdapter.setonItemClickListener {
                val direction = SearchFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }

        }
    }

    override fun loadNowPlayingMoviesList(data: NowPlayingResponse) {
        binding.apply {

            lifecycleScope.launchWhenCreated {
                nowPlayingMoviesAdapter.bind(data.results)
            }

            nowPlayingRecycler.apply {
                layoutManager = ScaleCenterItemLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = nowPlayingMoviesAdapter




            }
            // Scroll to the middle position
            nowPlayingRecycler.smoothScrollToPosition(11)

            nowPlayingMoviesAdapter.setonItemClickListener {
                val direction = HomeFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }
        }
    }

    override fun loadGenres(data: GenresListResponse) {
        binding.apply {
            genreSearchAdapter.differ.submitList(data.genres)
            genresSearchRecycler.apply {
                layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                adapter = genreSearchAdapter
            }
            genreSearchAdapter.setonItemClickListener {
                searchPresenter.callGenres()
            }
        }
        genreSearchAdapter.setonItemClickListener {
            val direction = SearchFragmentDirections.actionSearchFragmentToGenresFragment2()
            findNavController().navigate(direction)
        }
    }

    override fun showLoading() {
        binding.apply {
            searchLoading.visibility = View.VISIBLE
            moviesRecycler.visibility = View.GONE
        }
    }

    override fun hideLoading() {
        binding.apply {
            searchLoading.visibility = View.GONE
            moviesRecycler.visibility = View.VISIBLE
        }
    }

    override fun responseError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

}