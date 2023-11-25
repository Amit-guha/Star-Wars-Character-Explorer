package com.example.starwarscharacterexplorer.characters.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.starwarscharacterexplorer.characters.data.datasource.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@ExperimentalPagingApi
@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {
    val list = repository.getCharacters().cachedIn(viewModelScope)
}