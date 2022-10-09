package com.example.pgyebu.event

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

// MARK: - ref: https://deque.tistory.com/113
open class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>){
        // mPending을 true로 바꿔줌으로써 setValue를 한 번 하면
        // observer의 코드도 단 한 번 실행하게 됨
        // setValue 한 번 하고 나면 observer는 두 번 이상 실행할 수 없음
        // => 단 한 번 이벤트에 대한 observing 구현
        if(hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if(mPending.compareAndSet(true, false)){
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    // Used for cases where T is Void, to make calls cleaner.
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private val TAG = "SingleLiveEvent"
    }
}