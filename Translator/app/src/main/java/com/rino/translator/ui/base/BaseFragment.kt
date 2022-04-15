package com.rino.translator.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope

open class BaseFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), KoinScopeComponent {

    override val scope: Scope by getOrCreateScope()

    override fun onDestroy() {
        scope.close()
        super.onDestroy()
    }
}