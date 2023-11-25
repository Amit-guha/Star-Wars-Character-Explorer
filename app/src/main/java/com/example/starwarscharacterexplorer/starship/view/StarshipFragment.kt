package com.example.starwarscharacterexplorer.starship.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.starwarscharacterexplorer.databinding.FragmentStarshipBinding
import com.example.starwarscharacterexplorer.starship.view.adapter.StarShipPageAdapter
import com.example.starwarscharacterexplorer.starship.viewModel.StarShipViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class StarshipFragment : Fragment() {
    private var _binding: FragmentStarshipBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StarShipViewModel by viewModels()
    private val adapter: StarShipPageAdapter by lazy {
        StarShipPageAdapter(
            StarShipPageAdapter.OnClickListener {
                val action =
                    StarshipFragmentDirections.actionNavigationStarshipToStarshipDetailsFragment(it)
                findNavController().navigate(action)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStarshipBinding.inflate(inflater, container, false)

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