package com.rino.translator.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rino.translator.core.toString
import com.rino.translator.database.entity.Word
import com.rino.translator.databinding.HistoryItemBinding

class HistoryAdapter(
    private val onItemClick: (Word) -> Unit
) : ListAdapter<Word, HistoryAdapter.HistoryViewHolder>(HistoryItemCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(currentList[position])

    inner class HistoryViewHolder(
        private val binding: HistoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            with(binding) {
                root.setOnClickListener { onItemClick(word) }
                header.text = word.text
                viewingDate.text = word.viewingDate.toString("yyyy-MM-dd HH:mm:ss")
            }
        }
    }

}

class HistoryItemCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}