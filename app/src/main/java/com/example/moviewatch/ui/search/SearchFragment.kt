package com.example.moviewatch.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviewatch.adapter.PopularMoviesAdapter
import com.example.moviewatch.databinding.FragmentSearchBinding
import com.example.moviewatch.response.PopularMoviesListResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchContracts.View {

    private lateinit var binding: FragmentSearchBinding

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
        TODO("Not yet implemented")
    }

}