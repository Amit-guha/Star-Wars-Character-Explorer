package com.example.starwarscharacterexplorer.characters.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.starwarscharacterexplorer.characters.view.adapter.CharacterPageAdapter
import com.example.starwarscharacterexplorer.characters.viewModel.CharacterViewModel
import com.example.starwarscharacterexplorer.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels()

    private val adapter: CharacterPageAdapter by lazy { CharacterPageAdapter(
        CharacterPageAdapter.OnClickListener {
            val action =
                CharacterFragmentDirections.actionNavigationCharacterToCharacterDetailsFragment(it)
            findNavController().navigate(action)
        }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)

        prepareViews()
        initListeners()
        observeData()

        return binding.root
    }

    private fun observeData() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)

        }
    }

    private fun initListeners() {
        binding.editTextFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    private fun prepareViews() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

}