package com.example.starwarscharacterexplorer.planetDetailsFragment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarscharacterexplorer.planet.model.Planet

class PlanetDetailsViewModel : ViewModel() {

    private val _details = MutableLiveData<Planet>()
    val details: LiveData<Planet> = _details

    fun updateCharacterDetails(planet: Planet){
        _details.value = planet
    }
}