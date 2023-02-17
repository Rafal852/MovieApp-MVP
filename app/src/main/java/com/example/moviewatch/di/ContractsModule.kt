package com.example.moviewatch.di


import androidx.fragment.app.Fragment
import com.example.moviewatch.ui.credits.PersonContracts
import com.example.moviewatch.ui.details.DetailsContracts
import com.example.moviewatch.ui.favorites.FavoritesContracts
import com.example.moviewatch.ui.home.HomeContracts
import com.example.moviewatch.ui.search.SearchContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ContractsModule {

    @Provides
    fun homeView(fragment: Fragment): HomeContracts.View {
        return fragment as HomeContracts.View
    }

    @Provides
    fun detailView(fragment: Fragment): DetailsContracts.View {
        return fragment as DetailsContracts.View
    }

    @Provides
    fun personView(fragment: Fragment) : PersonContracts.View{
        return fragment as PersonContracts.View
    }

    @Provides
    fun searchView(fragment: Fragment): SearchContracts.View {
        return fragment as SearchContracts.View
    }

    @Provides
    fun favoriteView(fragment: Fragment): FavoritesContracts.View {
        return fragment as FavoritesContracts.View
    }

}