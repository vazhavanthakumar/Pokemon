package com.example.pokemon.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    companion object {
        private val mPending = AtomicBoolean(false)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.e("TAG", "Multiple observers registered but only one will be notified of changes.")
        }
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun postValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    override fun setValue(value: T) {
        mPending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        postValue(null)
    }
}