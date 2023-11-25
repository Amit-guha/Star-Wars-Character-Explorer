package com.example.starwarscharacterexplorer.starshipDetailsFragment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarscharacterexplorer.starship.model.Starship

class StarShipDetailsViewModel : ViewModel() {

    private val _details = MutableLiveData<Starship>()
    val details: LiveData<Starship> = _details

    fun updateCharacterDetails(starship: Starship){
        _details.value = starship
    }
}