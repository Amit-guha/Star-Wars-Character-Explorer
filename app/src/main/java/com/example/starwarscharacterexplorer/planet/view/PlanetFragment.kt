package com.example.starwarscharacterexplorer.planet.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.starwarscharacterexplorer.databinding.FragmentPlanetBinding
import com.example.starwarscharacterexplorer.planet.view.adapter.PlanetPageAdapter
import com.example.starwarscharacterexplorer.planet.viewModel.PlanetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class PlanetFragment : Fragment() {
    private var _binding: FragmentPlanetBinding? = null
    private val binding get() = _binding!!


    private val viewModel: PlanetViewModel by viewModels()
    private val adapter: PlanetPageAdapter by lazy { PlanetPageAdapter(
        PlanetPageAdapter.OnClickListener{
            val action =
                PlanetFragmentDirections.actionNavigationPlanetToPlanetDetailsFragment(it)
            findNavController().navigate(action)
        }
    ) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlanetBinding.inflate(inflater, container, false)

        prepareViews()
        observeData()

        return binding.root
    }

    private fun observeData() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)

        }
    }


    private fun prepareViews() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

}