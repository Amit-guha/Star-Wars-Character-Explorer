package com.example.starwarscharacterexplorer.characters.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacterexplorer.characters.model.Character
import com.example.starwarscharacterexplorer.databinding.ItemCharacterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import androidx.paging.PagingDataAdapter as PagingDataAdapter1

class CharacterPageAdapter(private val onClickListener: OnClickListener) : PagingDataAdapter1<Character, CharacterPageAdapter.CharacterViewHolder>(
    COMPARATOR
), Filterable {

    private var originalList: MutableList<Character> = mutableListOf()
    private val adapterScope = CoroutineScope(Dispatchers.Default)


    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.textViewCharacterName.text = character.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)

    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        val character = getItem(position)
        character?.let {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(character)
            }
        }

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
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
                val filteredList = results?.values as? List<Character> ?: emptyList()

                adapterScope.launch {
                    submitData(PagingData.from(filteredList))
                }

            }
        }
    }

    class OnClickListener(val clickListener: (character: Character) -> Unit) {
        fun onClick(character: Character) = clickListener(character)
    }

}