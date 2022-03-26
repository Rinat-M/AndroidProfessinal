package com.rino.translator.ui.main

import com.rino.translator.core.repository.WordsRepository
import moxy.MvpPresenter

class MainPresenter(
    private val wordsRepository: WordsRepository
) : MvpPresenter<MainView>() {



}