package com.example.moviewatch.ui.favorites

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviewatch.R
import com.example.moviewatch.adapter.FavoriteMoviesAdapter
import com.example.moviewatch.databinding.FragmentFavoritesBinding

import com.example.moviewatch.db.MoviesEntity
import com.example.moviewatch.ui.activity.SignInActivity
import com.example.moviewatch.ui.favorites.FavoritesPresenter.Companion.REQUEST_CAMERA
import com.example.moviewatch.ui.home.HomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() , FavoritesContracts.View {


    private lateinit var binding: FragmentFavoritesBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

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
        userProfileAll()
        logoutButton()

    }

    //user profile
    private fun userProfileAll(){
        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser

        if (user!=null){
            if (user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(binding.imgProfile)
            }else{
                Picasso.get().load(R.drawable.ic_person).into(binding.imgProfile)
            }

            binding.txtUsername.setText(user.displayName)
            binding.email.setText(user.email)

        }

        binding.imgProfile.setOnClickListener {
            openCamera()
        }
        binding.saveBtn.setOnClickListener {
            val image = when{
                ::imageUri.isInitialized -> imageUri
                user?.photoUrl == null -> (R.drawable.ic_person)
                else -> user.photoUrl
            }

            val name = binding.txtUsername.text.toString().trim()

            if (name.isEmpty()){
                binding.txtUsername.error = "Username is required"
                binding.txtUsername.requestFocus()
                return@setOnClickListener
            }

            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image as Uri?)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    //profile picture update
    private fun openCamera(){

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap){
        val baos = ByteArrayOutputStream()
        val ref  = FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image).addOnCompleteListener {
            if (it.isSuccessful){
                ref.downloadUrl.addOnCompleteListener {
                    it.result?.let {
                        imageUri = it
                        binding.imgProfile.setImageBitmap(imgBitmap)
                    }
                }
            }
        }
    }

    override fun loadFavoriteMovieList(data: MutableList<MoviesEntity>) {

        favoriteMoviesAdapter.bind(data)

        binding.favoriteRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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