package com.rino.translator.ui.main

import com.rino.translator.core.model.Word
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun updateList(words: List<Word>)

    @Skip
    fun showMessage(message: String)

}