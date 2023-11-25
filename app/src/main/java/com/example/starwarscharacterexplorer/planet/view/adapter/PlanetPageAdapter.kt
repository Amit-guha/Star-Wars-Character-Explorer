package com.example.starwarscharacterexplorer.planet.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacterexplorer.databinding.ItemCharacterBinding
import com.example.starwarscharacterexplorer.planet.model.Planet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PlanetPageAdapter(private val onClickListener: PlanetPageAdapter.OnClickListener) : PagingDataAdapter<Planet, PlanetPageAdapter.PlanetViewHolder>(
    COMPARATOR
), Filterable {

    private var originalList: MutableList<Planet> = mutableListOf()
    private val adapterScope = CoroutineScope(Dispatchers.Default)


    class PlanetViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(planet : Planet) {
            binding.textViewCharacterName.text = planet.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanetViewHolder(binding)

    }


    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        val planet = getItem(position)
        planet?.let {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(planet)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Planet>() {
            override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
                return oldItem == newItem
            }
        }
    }

    //cancel the coroutine scope to avoid leaks
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        adapterScope.cancel()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString() ?: ""
                if (originalList.isEmpty()) {
                    originalList = snapshot().items.toMutableList()
                }
                val filteredList = if (query.isEmpty()) {
                    originalList.toList()
                } else {
                    snapshot().items.filter { planet ->
                        planet.name.contains(query, ignoreCase = true)
                    }
                }

                return FilterResults().apply {
                    values = filteredList
                    count = filteredList.size
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                val filteredList = results?.values as? List<Planet> ?: emptyList()

                adapterScope.launch {
                    submitData(PagingData.from(filteredList))
                }

            }
        }
    }

    class OnClickListener(val clickListener: (planet: Planet) -> Unit) {
        fun onClick(planet: Planet) = clickListener(planet)
    }

}