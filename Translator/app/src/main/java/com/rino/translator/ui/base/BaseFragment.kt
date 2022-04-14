package com.rino.translator.ui.base

import androidx.fragment.app.Fragment
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope

open class BaseFragment: Fragment(), KoinScopeComponent {

    override val scope: Scope by getOrCreateScope()

    override fun onDestroy() {
        scope.close()
        super.onDestroy()
    }
}