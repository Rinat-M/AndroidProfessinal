package com.rino.translator.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rino.core.model.Word
import com.rino.translator.databinding.WordItemBinding
import com.rino.translator.ui.base.ImageLoader

class WordsAdapter(
    private val imageLoader: ImageLoader<ImageView>,
    private val itemClickListener: (Word) -> Unit
) : ListAdapter<Word, WordsAdapter.WordViewHolder>(WordItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) =
        holder.bind(currentList[position])

    inner class WordViewHolder(
        private val binding: WordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            with(binding) {
                root.setOnClickListener { itemClickListener(word) }
                header.text = word.text

                word.meanings.firstOrNull()?.let { meaning ->
                    description.text = meaning.translation?.text
                    meaning.previewUrl?.let { imageLoader.loadInto("https:$it", binding.previewImage) }
                }
            }
        }
    }

}

class WordItemCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}