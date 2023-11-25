package com.example.starwarscharacterexplorer.characterDetailsFragment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarscharacterexplorer.characters.model.Character


class CharacterDetailsViewModel : ViewModel() {

    private val _details = MutableLiveData<Character>()
    val details: LiveData<Character> = _details

    fun updateCharacterDetails(character: Character){
        _details.value = character
    }

}