package com.example.starwarscharacterexplorer.planet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.starwarscharacterexplorer.planet.data.PlanetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class PlanetViewModel @Inject constructor(repository: PlanetRepository) : ViewModel() {
    val list = repository.getPlanet().cachedIn(viewModelScope)
}