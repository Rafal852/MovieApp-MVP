package com.example.moviewatch.ui.credits

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
import com.example.moviewatch.adapter.*
import com.example.moviewatch.databinding.FragmentDetailsBinding
import com.example.moviewatch.databinding.FragmentPersonBinding
import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.response.*
import com.example.moviewatch.ui.credits.PersonFragmentArgs
import com.example.moviewatch.ui.home.HomeFragmentDirections
import com.example.moviewatch.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PersonFragment : Fragment(), PersonContracts.View {

    private lateinit var binding: FragmentPersonBinding

    private var movieId = 0
    private val args: PersonFragmentArgs by navArgs()

    @Inject
    lateinit var personPresenter: PersonPresenter

    @Inject
    lateinit var entity: MoviesEntity

    @Inject
    lateinit var playedInAdapter: PlayedInAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            movieId = args.movieId
            if (movieId > 0) {
                personPresenter.callPerson(movieId)
                personPresenter.callPlayedIn(movieId)



            }

            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }


    override fun loadPersonMovie(data: PersonListResponse) {
        binding.apply {
            val moviePosterURL = Constants.POSTER_BASE_URL + data.profile_path
            posterNormalImg.load(moviePosterURL) {
                crossfade(true)
                crossfade(800)
            }
            actorNameTxt.text = data.name
            biographyInfo.text = data.biography
            birthday.text = "Born: ${data.birthday}"
            knownForDepartment.text = "Known for: ${data.known_for_department}"
            placeOfBirth.text = "Place of Birth: ${data.place_of_birth}"



        }

    }

    override fun loadPlayedIn(data: PlayedInListResponse) {
        binding.apply {
            playedInAdapter.differ.submitList(data.cast)
            alsoPlatedInRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = playedInAdapter
            }
            playedInAdapter.setonItemClickListener {
                val direction = it.id?.let { it1 ->
                    PersonFragmentDirections.actionToDetailFragment(
                        it1
                    )
                }
                if (direction != null) {
                    findNavController().navigate(direction)
                }
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
        personPresenter.onStop()
    }

}