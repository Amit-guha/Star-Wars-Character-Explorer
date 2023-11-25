package com.example.starwarscharacterexplorer.starship.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacterexplorer.databinding.ItemCharacterBinding
import com.example.starwarscharacterexplorer.starship.model.Starship
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class StarShipPageAdapter(private val onClickListener: StarShipPageAdapter.OnClickListener) : PagingDataAdapter<Starship, StarShipPageAdapter.StarShipViewHolder>(
    COMPARATOR
), Filterable {

    private var originalList: MutableList<Starship> = mutableListOf()
    private val adapterScope = CoroutineScope(Dispatchers.Default)


    class StarShipViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(starship: Starship) {
            binding.textViewCharacterName.text = starship.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarShipViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StarShipViewHolder(binding)

    }


    override fun onBindViewHolder(holder: StarShipViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        val starShip = getItem(position)
        starShip?.let {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(starShip)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Starship>() {
            override fun areItemsTheSame(oldItem: Starship, newItem: Starship): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Starship, newItem: Starship): Boolean {
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
                    snapshot().items.filter { character ->
                        character.name.contains(query, ignoreCase = true)
                    }
                }

                return FilterResults().apply {
                    values = filteredList
                    count = filteredList.size
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                val filteredList = results?.values as? List<Starship> ?: emptyList()

                adapterScope.launch {
                    submitData(PagingData.from(filteredList))
                }

            }
        }
    }

    class OnClickListener(val clickListener: (starship: Starship) -> Unit) {
        fun onClick(starship: Starship) = clickListener(starship)
    }

}