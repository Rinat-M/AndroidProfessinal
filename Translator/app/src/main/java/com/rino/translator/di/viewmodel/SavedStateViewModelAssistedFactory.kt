package com.rino.translator.di.viewmodel

import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.AssistedFactory

@AssistedFactory
interface SavedStateViewModelAssistedFactory {

    fun create(owner: SavedStateRegistryOwner): SavedStateViewModelFactory

}