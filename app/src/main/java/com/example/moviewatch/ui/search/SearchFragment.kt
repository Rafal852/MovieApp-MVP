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
import com.example.moviewatch.adapter.NowPlayingMoviesAdapter
import com.example.moviewatch.adapter.PopularMoviesAdapter
import com.example.moviewatch.databinding.FragmentSearchBinding
import com.example.moviewatch.layoutManager.ScaleCenterItemLayoutManager
import com.example.moviewatch.response.NowPlayingResponse
import com.example.moviewatch.response.PopularMoviesListResponse
import com.example.moviewatch.response.TopMoviesResponse
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

        binding.apply {
            searchEdt.addTextChangedListener {
                val search = it.toString()
                if (search.isNotEmpty()) {
                    searchPresenter.callSearchMoviesList(1,search)
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
            nowPlayingMoviesAdapter.setonItemClickListener {
                val direction = HomeFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }
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