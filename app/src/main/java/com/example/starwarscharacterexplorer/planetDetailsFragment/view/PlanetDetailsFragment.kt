package com.example.starwarscharacterexplorer.planetDetailsFragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.starwarscharacterexplorer.databinding.FragmentPlanetDetailsBinding
import com.example.starwarscharacterexplorer.planet.model.Planet
import com.example.starwarscharacterexplorer.planetDetailsFragment.viewModel.PlanetDetailsViewModel


class PlanetDetailsFragment : Fragment() {

    private var _binding: FragmentPlanetDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlanetDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlanetDetailsBinding.inflate(inflater, container, false)

        prepareViews()
        observeData()

        return binding.root
    }

    private fun observeData() {
        viewModel.details.observe(viewLifecycleOwner){
            binding.apply {
                textViewClimateValue.text = it.climate
                textViewFullNameValue.text = it.name
                textViewDiameterValue.text = it.diameter
                textViewTerrainValue.text = it.terrain
                textViewGravityValue.text = it.gravity
                textViewPopulationValue.text = it.population
                textViewRotationValue.text = it.rotation_period
                textViewOrbitalValue.text = it.orbital_period
                textViewSurfaceWaterValue.text = it.surface_water
            }
        }
    }

    private fun prepareViews() {
        val planetDetails: Planet? = arguments?.getParcelable("data")
        if (planetDetails != null) {
            viewModel.updateCharacterDetails(planetDetails)
        }
    }

}