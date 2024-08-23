package com.presentation.base

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.base.BaseUseCase
import com.domain.base.CompletionBlock
import com.presentation.helper.SingleLiveEvent
import kotlinx.coroutines.launch

open class BaseViewModel<State, Effect> : ViewModel() {

    private val _state = MutableLiveData<State>()
    private val _effect = SingleLiveEvent<Effect>()

    val state: LiveData<State> get() = _state
    val effect: SingleLiveEvent<Effect> get() = _effect

    @SuppressLint("NullSafeMutableLiveData")
    protected fun postState(state: State) {
        _state.value = state
    }

    @SuppressLint("NullSafeMutableLiveData")
    protected fun postEffect(effect: Effect) {
        _effect.postValue(effect)
    }

    protected fun <P, R, T, U : BaseUseCase<P, R, T>> U.launch(
        param: P,
        block: CompletionBlock<T> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<T>().apply(block)

            val proxy: CompletionBlock<T> = {
                onSuccess = {
                    actualRequest.onSuccess(it)
                }
                onError = {
                    actualRequest.onError?.invoke(it)
                }
            }
            execute(param, proxy)
        }
    }

}