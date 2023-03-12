package com.example.moviewatch.ui.genres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviewatch.R
import com.example.moviewatch.adapter.ShowGenreResultAdapter
import com.example.moviewatch.databinding.FragmentGenresBinding
import com.example.moviewatch.response.PopularMoviesListResponse
import com.example.moviewatch.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GenresFragment : Fragment() {

    lateinit var binding: FragmentGenresBinding

    @Inject
    lateinit var showGenreResultAdapter: ShowGenreResultAdapter

    @Inject
    lateinit var genresPresenter: GenresPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genresPresenter.callMoviesGenres(1, id.toString())
    }



//    override fun loadMoviesGenres(data: PopularMoviesListResponse) {
//        binding.apply {
//
//            lifecycleScope.launchWhenCreated {
//                showGenreResultAdapter.bind(data.results)
//            }
//
//            moviesRecycler.apply {
//                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                adapter = showGenreResultAdapter
//            }
//        }
//        showGenreResultAdapter.setonItemClickListener {
//            val direction = GenresFragmentDirections.actionToDetailFragment(it.id)
//            findNavController().navigate(direction)
//        }
//    }






}