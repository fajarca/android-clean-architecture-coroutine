package io.fajarca.characters.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.characters.databinding.ItemSeriesBinding
import io.fajarca.characters.domain.entities.MarvelCharacter
import io.fajarca.characters.domain.entities.MarvelCharacterSeriesUiModel

class CharacterSeriesRecyclerAdapter(private val listener: SeriesClickListener) : ListAdapter<MarvelCharacterSeriesUiModel, CharacterSeriesRecyclerAdapter.CharacterViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSeriesBinding.inflate(layoutInflater, parent,false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }


    inner class CharacterViewHolder(val binding: ItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(series: MarvelCharacterSeriesUiModel, listener: SeriesClickListener) {
            binding.series = series
            binding.tvSeriesCharacter.text = series.seriesCharacters
            binding.root.setOnClickListener { listener.onSeriesPressed(series) }
            binding.executePendingBindings()
        }
    }

    interface SeriesClickListener {
        fun onSeriesPressed(character: MarvelCharacterSeriesUiModel)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MarvelCharacterSeriesUiModel>() {
            override fun areItemsTheSame(oldItem: MarvelCharacterSeriesUiModel, newItem: MarvelCharacterSeriesUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MarvelCharacterSeriesUiModel, newItem: MarvelCharacterSeriesUiModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}