package com.presentation.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment<State, Effect, VDB : ViewDataBinding, BVM : BaseViewModel<State, Effect>> : Fragment() {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): BVM

    fun getBinding(): VDB = fragmentBinding

    private lateinit var fragmentBinding: VDB
    private lateinit var fragmentViewModel: BVM

    protected open fun observeState(state: State) {
        // override when observing
        Timber.i(this.javaClass.simpleName,"observeState");
    }

    protected open fun observeEffect(effect: Effect) {
        // override when observing
        Timber.i(this.javaClass.simpleName,"observeEffect");
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        fragmentViewModel = getViewModel()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(fragmentBinding)

        fragmentViewModel.state.observe(viewLifecycleOwner, ::observeState)
        fragmentViewModel.effect.observe(viewLifecycleOwner, ::observeEffect)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewModel.state.removeObservers(viewLifecycleOwner)
        fragmentViewModel.effect.removeObservers(viewLifecycleOwner)
    }

    protected open val bindViews: VDB.() -> Unit = {}

}