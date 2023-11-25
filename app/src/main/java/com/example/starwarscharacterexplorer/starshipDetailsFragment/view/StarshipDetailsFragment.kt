package com.example.starwarscharacterexplorer.starshipDetailsFragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.starwarscharacterexplorer.databinding.FragmentStarshipDetailsBinding
import com.example.starwarscharacterexplorer.starship.model.Starship
import com.example.starwarscharacterexplorer.starshipDetailsFragment.viewModel.StarShipDetailsViewModel


class StarshipDetailsFragment : Fragment() {
    private var _binding: FragmentStarshipDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StarShipDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentStarshipDetailsBinding.inflate(inflater, container, false)

        prepareViews()
        observeData()

        return binding.root
    }

    private fun observeData() {
        viewModel.details.observe(viewLifecycleOwner){
            binding.apply {
                textViewCargoCapacityValue.text = it.cargo_capacity
                textViewConsumablesValue.text = it.consumables
                textViewCrewValue.text = it.crew
                textViewFullNameValue.text = it.name
                textViewLengthValue.text = it.length
                textViewManufacturerValue.text = it.manufacturer
                textViewModelValue.text = it.model
                textViewPassengersValue.text = it.passengers
                textViewStarshipClassValue.text = it.starship_class
            }
        }
    }

    private fun prepareViews() {
        val starShipDetails: Starship? = arguments?.getParcelable("data")
        if (starShipDetails != null) {
            viewModel.updateCharacterDetails(starShipDetails)
        }

    }

}