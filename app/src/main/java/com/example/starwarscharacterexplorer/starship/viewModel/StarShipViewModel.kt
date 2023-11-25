package com.example.starwarscharacterexplorer.starship.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.starwarscharacterexplorer.starship.data.StarShipRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class StarShipViewModel @Inject constructor(private val repository: StarShipRepository) : ViewModel() {
    val list = repository.getStarShips().cachedIn(viewModelScope)
}