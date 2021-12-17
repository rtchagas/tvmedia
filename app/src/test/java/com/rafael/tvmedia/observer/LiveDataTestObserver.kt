package com.rafael.tvmedia.observer

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.CountDownLatch

class LiveDataTestObserver<T : Any?> : Observer<T> {

    private val valueHistory = ArrayList<T>()
    private var valueLatch = CountDownLatch(1)

    override fun onChanged(value: T) {
        valueHistory.add(value)
        valueLatch.countDown()
    }

    /**
     * Returns a last received value. Fails if no value was received yet.
     *
     * @return a last received value
     */
    fun value(): T {
        assertHasValue()
        return valueHistory[valueHistory.size - 1]
    }

    /**
     * Assert that this TestObserver received at least one value.
     *
     * @return this
     */
    fun assertHasValue(): LiveDataTestObserver<T> {
        if (valueHistory.isEmpty()) {
            throw fail("Observer never received any value")
        }

        return this
    }

    /**
     * Assert that this TestObserver never received any value.
     *
     * @return this
     */
    fun assertNoValue(): LiveDataTestObserver<T> {
        if (valueHistory.isNotEmpty()) {
            throw fail("Expected no value, but received: " + value())
        }
        return this
    }

    /**
     * Assert that this TestObserver last received value is equal to
     * the given value.
     *
     * @param expected the value to expect being equal to last value, can be null
     * @return this
     */
    fun assertValue(expected: T): LiveDataTestObserver<T> {
        val value = value()
        if (value != expected) {
            throw fail("Expected: $expected, Actual: $value")
        }
        return this
    }

    /**
     * Asserts that for this TestObserver last received value
     * the provided predicate returns true.
     *
     * @param valuePredicate the predicate that receives the observed value
     * and should return true for the expected value.
     * @return this
     */
    fun assertValue(valuePredicate: Function<T, Boolean>): LiveDataTestObserver<T> {
        val value = value()
        if (!valuePredicate.apply(value)) {
            throw fail(
                "Value $value does not match the predicate $valuePredicate"
            )
        }
        return this
    }


    /**
     * Asserts that the TestObserver received only the specified values in the specified order.
     *
     * @param values the values expected
     * @return this
     */
    fun assertValueHistory(vararg values: T): LiveDataTestObserver<T> {
        val expected = values.asList()
        if (valueHistory != expected) {
            throw fail(
                "History $valueHistory does not match the expected $expected"
            )
        }
        return this
    }

    /**
     * Awaits until this TestObserver has any value.
     *
     * If this TestObserver has already value then this method returns immediately.
     *
     * @return this
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun awaitValue(): LiveDataTestObserver<T> {
        valueLatch.await()
        return this
    }

    /**
     * Awaits until this TestObserver receives next value.
     *
     * If this TestObserver has already value then it awaits for another one.
     *
     * @return this
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Throws(InterruptedException::class)
    fun awaitNextValue(): LiveDataTestObserver<T> {
        return withNewLatch().awaitValue()
    }

    fun finish() {
        // Unit return to break the chain
    }

    private fun withNewLatch(): LiveDataTestObserver<T> {
        valueLatch = CountDownLatch(1)
        return this
    }

    private fun fail(message: String) = AssertionError(message)
}

fun <T : Any?> LiveData<T>.test(): LiveDataTestObserver<T> {
    val observer = LiveDataTestObserver<T>()
    observeForever(observer)
    return observer
}
