package com.rino.translator.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rino.translator.R
import com.rino.translator.core.model.ScreenState
import com.rino.translator.core.model.Word
import com.rino.translator.databinding.ActivityMainBinding
import com.rino.translator.databinding.ProgressBarAndErrorMsgBinding
import com.rino.translator.network.isOnline
import com.rino.translator.ui.base.ImageLoader
import com.rino.translator.ui.main.adapter.WordsAdapter
import com.rino.translator.ui.showToast
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class MainActivity : AppCompatActivity() {

    private val imageLoader: ImageLoader<ImageView> by inject()
    private val viewModel: MainViewModel by stateViewModel()

    private lateinit var binding: ActivityMainBinding
    private lateinit var includeBinding: ProgressBarAndErrorMsgBinding

    private val wordsAdapter by lazy {
        WordsAdapter(imageLoader, viewModel::onUserClicked)
    }

    private val noInternetDialog: AlertDialog by lazy {
        AlertDialog.Builder(this@MainActivity)
            .setTitle(R.string.dialog_title_device_is_offline)
            .setMessage(R.string.dialog_message_device_is_offline)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyTheme()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        includeBinding = ProgressBarAndErrorMsgBinding.bind(binding.root)

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

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.words.observe(this) { state ->
            state?.let { data ->
                viewModel.viewModelScope.launch {
                    updateList(data)
                }
            }
        }

        viewModel.message.observe(this) { messageEvent ->
            messageEvent.getContentIfNotHandled()?.let { showToast(it) }
        }

        viewModel.themeChanged.observe(this) { themeChangedEvent ->
            themeChangedEvent.getContentIfNotHandled()?.let { recreate() }
        }

        viewModel.showNoInternetDialog.observe(this) { event ->
            event.getContentIfNotHandled()?.let { noInternetDialog.show() }
        }
    }

    private fun applyTheme() {
        if (viewModel.isNightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun updateList(state: ScreenState<List<Word>>) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(query: String?): Boolean {
                if (isOnline(this@MainActivity)) {
                    viewModel.search(query ?: "")
                } else {
                    viewModel.showNoInternetDialog()
                }

                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_lamp) {
            viewModel.changeDayNightMode()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}