package com.example.mvp_mvvm.utils

import android.os.Handler

data class Subscriber<T>(private val handler: Handler, private val callback: (T?) -> Unit) {
    fun invoke(value: T?) {
        handler.post {
            callback.invoke(value)
        }
    }
}

class Publisher<T>(private val isSingle: Boolean = false) {
    private var subscribers: MutableSet<Subscriber<T?>> = mutableSetOf()
    var value: T? = null
        private set
    private var hasFirstValue = false

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)
        if (hasFirstValue) {
            subscriber.invoke(value)
        }
    }

    fun post(value: T) {
        if (!isSingle) {
            hasFirstValue = true
            this.value = value
        }
        subscribers.forEach { it.invoke(value) }
    }

    fun unsubscribe(subscriber: Subscriber<T?>) {
        subscribers.remove(subscriber)
    }

    fun unsubscribeAll() {
        subscribers.clear()
    }
}