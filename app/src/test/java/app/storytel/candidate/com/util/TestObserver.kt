package app.storytel.candidate.com.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    val observedValue = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValue.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}