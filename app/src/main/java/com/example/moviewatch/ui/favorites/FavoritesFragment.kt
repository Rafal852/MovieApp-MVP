package com.example.moviewatch.ui.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviewatch.adapter.FavoriteMoviesAdapter
import com.example.moviewatch.databinding.FragmentFavoritesBinding

import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.ui.activity.SignInActivity
import com.example.moviewatch.ui.home.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() , FavoritesContracts.View {


    private lateinit var binding: FragmentFavoritesBinding

    @Inject
    lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    @Inject
    lateinit var favoritesPresenter: FavoritesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesPresenter.callFavoritesList()

        logoutButton()

    }

    override fun loadFavoriteMovieList(data: MutableList<MoviesEntity>) {

        favoriteMoviesAdapter.bind(data)

        binding.favoriteRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteMoviesAdapter
        }

        favoriteMoviesAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionToDetailFragment(it.id)
            findNavController().navigate(direction)
        }

    }

    override fun showEmpty() {
        binding.apply {
            emptyItemsLay.visibility = View.VISIBLE
            favoriteRecycler.visibility = View.GONE
        }
    }

    override fun showLoading() {
        binding.apply {
            favLoading.visibility = View.VISIBLE
            favoriteRecycler.visibility = View.GONE
        }
    }

    override fun hideLoading() {
        binding.apply {
            favLoading.visibility = View.GONE
            favoriteRecycler.visibility = View.VISIBLE
        }
    }

    private fun logoutButton(){
        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@FavoritesFragment.context, SignInActivity::class.java)
            startActivity(intent)

        }
    }

    override fun responseError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()

    }


    override fun onDestroy() {
        super.onDestroy()
        favoritesPresenter.onStop()
    }


}