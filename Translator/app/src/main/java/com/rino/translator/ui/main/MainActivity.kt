package com.rino.translator.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rino.translator.core.model.Word
import com.rino.translator.core.repository.WordsRepositoryImpl
import com.rino.translator.databinding.ActivityMainBinding
import com.rino.translator.network.DictionaryApiHolder
import com.rino.translator.ui.base.GlideImageLoader
import com.rino.translator.ui.main.adapter.WordsAdapter
import com.rino.translator.ui.showToast
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"
    }

    private lateinit var binding: ActivityMainBinding

    private val presenter by moxyPresenter {
        MainPresenter(WordsRepositoryImpl(DictionaryApiHolder.dictionaryApiService))
    }

    private val wordsAdapter by lazy {
        WordsAdapter(GlideImageLoader(), presenter::onUserClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.search(searchWord)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        with(binding.wordsRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = wordsAdapter
        }
    }

    override fun updateList(words: List<Word>) {
        wordsAdapter.submitList(words)
    }

    override fun showMessage(message: String) {
        showToast(message)
    }

}