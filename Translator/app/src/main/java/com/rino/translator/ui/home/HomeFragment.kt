package com.rino.translator.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat.recreate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rino.translator.R
import com.rino.translator.core.model.ScreenState
import com.rino.translator.core.model.Word
import com.rino.translator.databinding.FragmentHomeBinding
import com.rino.translator.databinding.ProgressBarAndErrorMsgBinding
import com.rino.translator.network.isOnline
import com.rino.translator.ui.base.ImageLoader
import com.rino.translator.ui.details.WordDetailsFragmentArgs
import com.rino.translator.ui.home.adapter.WordsAdapter
import com.rino.translator.ui.showToast
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _includeBinding: ProgressBarAndErrorMsgBinding? = null
    private val includeBinding get() = _includeBinding!!

    private val imageLoader: ImageLoader<ImageView> by inject()
    private val viewModel: HomeViewModel by stateViewModel()

    private val wordsAdapter by lazy {
        WordsAdapter(imageLoader, viewModel::onUserClicked)
    }

    private val noInternetDialog: AlertDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title_device_is_offline)
            .setMessage(R.string.dialog_message_device_is_offline)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _includeBinding = ProgressBarAndErrorMsgBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        viewModel.words.observe(viewLifecycleOwner) { state ->
            state?.let { data ->
                viewModel.viewModelScope.launch {
                    updateList(data)
                }
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { messageEvent ->
            messageEvent.getContentIfNotHandled()?.let { requireContext().showToast(it) }
        }

        viewModel.themeChanged.observe(viewLifecycleOwner) { themeChangedEvent ->
            themeChangedEvent.getContentIfNotHandled()?.let { recreate(requireActivity()) }
        }

        viewModel.showNoInternetDialog.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { noInternetDialog.show() }
        }

        viewModel.showWordDetails.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { wordId ->
                val bundle = WordDetailsFragmentArgs(wordId).toBundle()
                findNavController().navigate(
                    R.id.action_navigation_home_to_word_details,
                    bundle
                )
            }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(query: String?): Boolean {
                if (isOnline(requireContext())) {
                    viewModel.search(query ?: "")
                } else {
                    viewModel.showNoInternetDialog()
                }

                return true
            }
        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_lamp) {
            viewModel.changeDayNightMode()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        _binding = null
        _includeBinding = null
        super.onDestroyView()
    }
}