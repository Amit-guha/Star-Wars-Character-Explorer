package com.example.starwarscharacterexplorer.characterDetailsFragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.starwarscharacterexplorer.characters.model.Character
import com.example.starwarscharacterexplorer.characterDetailsFragment.viewModel.CharacterDetailsViewModel
import com.example.starwarscharacterexplorer.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        prepareViews()
        observeData()

        return binding.root
    }

    private fun observeData() {
        viewModel.details.observe(viewLifecycleOwner){
            binding.textViewBirthYearValue.text = it.birth_year
            binding.textViewEyeColorValue.text = it.eye_color
            binding.textViewFullNameValue.text = it.name
            binding.textViewGenderValue.text = it.gender
            binding.textViewHairColorValue.text = it.hair_color
            binding.textViewSkinColorValue.text = it.skin_color
            binding.textViewHeightValue.text = it.height
            binding.textViewMassValue.text = it.mass
            binding.textViewHomeWorldValue.text = it.homeworld
        }
    }

    private fun prepareViews() {
        val characterDetails: Character? = arguments?.getParcelable("data")
        if (characterDetails != null) {
            viewModel.updateCharacterDetails(characterDetails)
        }

    }

}