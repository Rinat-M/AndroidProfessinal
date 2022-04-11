package com.rino.translator.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rino.core.model.ScreenState
import com.rino.database.entity.WordWithMeanings
import com.rino.translator.databinding.FragmentWordDetailsBinding
import com.rino.translator.databinding.ProgressBarAndErrorMsgBinding
import com.rino.translator.ui.base.ImageLoader
import com.rino.translator.ui.details.adapter.MeaningsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WordDetailsFragment : Fragment() {

    companion object {
        fun newInstance(wordId: Long) =
            WordDetailsFragment().apply {
                arguments = WordDetailsFragmentArgs(wordId).toBundle()
            }
    }

    private val imageLoader: ImageLoader<ImageView> by inject()
    private val viewModel: WordDetailsViewModel by viewModel { parametersOf(args.wordId) }

    private val args: WordDetailsFragmentArgs by navArgs()

    private var _binding: FragmentWordDetailsBinding? = null
    private val binding get() = _binding!!

    private var _includeBinding: ProgressBarAndErrorMsgBinding? = null
    private val includeBinding get() = _includeBinding!!

    private val meaningsAdapter by lazy {
        MeaningsAdapter(imageLoader)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordDetailsBinding.inflate(inflater, container, false)
        _includeBinding = ProgressBarAndErrorMsgBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.meaningsRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = meaningsAdapter
        }

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.wordWithMeanings.observe(viewLifecycleOwner) { state ->
            state?.let { data -> updateList(data) }
        }
    }

    private fun updateList(state: ScreenState<WordWithMeanings>) {
        when (state) {
            is ScreenState.Loading -> {
                binding.visibilityGroup.isVisible = false
                includeBinding.progressBar.isVisible = true
                includeBinding.errorMsg.isVisible = false
            }

            is ScreenState.Success -> {
                val wordWithMeanings = state.data
                binding.word.text = wordWithMeanings.word.text
                meaningsAdapter.submitList(wordWithMeanings.meanings)

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

    override fun onDestroy() {
        _binding = null
        _includeBinding = null
        super.onDestroy()
    }
}