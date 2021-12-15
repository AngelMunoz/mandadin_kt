package me.tunaxor.apps.mandadin

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * This is a simple observable store to share data between different parts of the code base
 * ```kotlin
 * val store = Store.useStore(10)
 * // do an atomic update
 * store.update { it + 1 } // 11
 * // or replace the content
 * store.set(0) // 0
 * ```
 */
class Store<TType> internal constructor(initial: TType) {
    private val _subject = BehaviorSubject.createDefault(initial)

    val observe get() = _subject as Observable<TType>

    val value get() = _subject.value!!


    fun update(updater: (TType) -> TType) {
        _subject.onNext(updater(_subject.value!!))
    }

    fun set(value: TType) {
        _subject.onNext(value);
    }

    companion object {
        fun <TType> useStore(initial: TType): Store<TType> {
            return Store(initial)
        }
    }
}
