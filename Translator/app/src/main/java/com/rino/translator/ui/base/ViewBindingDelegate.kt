package com.rino.translator.ui.base

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    ViewBindingDelegate(this, T::class.java)

class ViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    bindingClass: Class<T>
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

    private var binding: T? = null

    private val bindMethod = bindingClass.getMethod("bind", View::class.java)

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding != null) {
            return binding!!
        }

        fragment.lifecycle.addObserver(this)

        val lifeCycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifeCycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Invalid view state: ${lifeCycle.currentState}")
        }

        binding = bindMethod(null, fragment.requireView()) as T
        return binding!!
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    binding = null
                }
            })
        }
    }
}


inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding() =
    ActivityViewBindingDelegate(this, T::class.java)

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val activity: AppCompatActivity,
    bindingClass: Class<T>
) : ReadOnlyProperty<AppCompatActivity, T>, DefaultLifecycleObserver {

    private var binding: T? = null

    private val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        if (binding != null) {
            return binding!!
        }

        activity.lifecycle.addObserver(this)

        val lifeCycle = activity.lifecycle
        if (!lifeCycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Invalid view state: ${lifeCycle.currentState}")
        }

        binding = inflateMethod(null, activity.layoutInflater) as T
        activity.setContentView(binding!!.root)

        return binding!!
    }
}