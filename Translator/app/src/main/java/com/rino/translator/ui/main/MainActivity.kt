package com.rino.translator.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rino.translator.R
import com.rino.translator.core.model.ScreenState
import com.rino.translator.core.model.Word
import com.rino.translator.core.repository.WordsRepositoryImpl
import com.rino.translator.databinding.ActivityMainBinding
import com.rino.translator.databinding.ProgressBarAndErrorMsgBinding
import com.rino.translator.network.DictionaryApiHolder
import com.rino.translator.ui.base.GlideImageLoader
import com.rino.translator.ui.main.adapter.WordsAdapter
import com.rino.translator.ui.showToast
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val presenter by moxyPresenter {
        MainPresenter(WordsRepositoryImpl(DictionaryApiHolder.dictionaryApiService))
    }

    private lateinit var binding: ActivityMainBinding

    private var _includeBinding: ProgressBarAndErrorMsgBinding? = null
    private val includeBinding get() = _includeBinding!!

    private val wordsAdapter by lazy {
        WordsAdapter(GlideImageLoader(), presenter::onUserClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _includeBinding = ProgressBarAndErrorMsgBinding.bind(binding.root)

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

    override fun updateList(state: ScreenState<List<Word>>) {
        when (state) {
            is ScreenState.Loading -> {
                wordsAdapter.submitList(null)
                wordsAdapter.notifyDataSetChanged()

                binding.visibilityGroup.isVisible = false
                includeBinding.progressBar.isVisible = true
                includeBinding.errorMsg.isVisible = false
            }

            is ScreenState.Success -> {
                wordsAdapter.submitList(state.data)
                includeBinding.progressBar.isVisible = false
                includeBinding.errorMsg.isVisible = false
                binding.visibilityGroup.isVisible = true
            }

            is ScreenState.Error -> {
                binding.visibilityGroup.isVisible = false

                with(includeBinding) {
                    progressBar.isVisible = false
                    errorMsg.isVisible = true
                    errorMsg.text = state.error.toString()
                }
            }
        }
    }

    override fun showMessage(message: String) {
        showToast(message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val search = menu!!.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(query: String?): Boolean {
                presenter.search(query ?: "")
                return true
            }
        })

        return true
    }
}