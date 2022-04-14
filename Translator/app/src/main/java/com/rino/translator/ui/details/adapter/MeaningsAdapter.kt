package com.rino.translator.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rino.database.entity.Meaning
import com.rino.translator.databinding.MeaningItemBinding
import com.rino.translator.ui.base.ImageLoader

class MeaningsAdapter(
    private val imageLoader: ImageLoader<ImageView>,
) : ListAdapter<Meaning, MeaningsAdapter.MeaningViewHolder>(MeaningItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) =
        holder.bind(currentList[position])

    inner class MeaningViewHolder(
        private val binding: MeaningItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meaning: Meaning) {
            with(binding) {
                header.text = meaning.text
                description.text = meaning.note
                transcription.text = meaning.transcription
                imageLoader.loadInto("https:${meaning.previewUrl}", previewImage)
            }
        }
    }

}

class MeaningItemCallback : DiffUtil.ItemCallback<Meaning>() {
    override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
        return oldItem == newItem
    }
}