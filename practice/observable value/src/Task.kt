interface Value<out T> {
    val value: T

    fun observe(subscriber: Callable<T>): Cancellable
}

fun interface Cancellable {
    fun cancel()
}

abstract class AbstractValue<T> : Value<T> {
    val subscribers: MutableSet<Callable<T>> = mutableSetOf()

    override fun observe(subscriber: Callable<T>): CancelHandle<T> {
        subscribers.add(subscriber)
        return CancelHandle(this, subscriber)
    }

    class CancelHandle<T>(val receiver: AbstractValue<T>, val subscriber: Callable<T>) : Cancellable {
        override fun cancel() {
            if (!receiver.subscribers.remove(subscriber)) {
                throw InvalidCancelHandle("Observer is already cancelled")
            }
        }
    }
}

class MutableValue<T>(initial: T) : AbstractValue<T>() {
    override fun observe(subscriber: Callable<T>): CancelHandle<T> {
        subscriber(value)
        return super.observe(subscriber)
    }

    override var value: T = initial
        set(newVal) {
            subscribers.forEach { it(newVal) }
            field = newVal
        }
}

class InvalidCancelHandle(message: String) : IllegalStateException(message)

typealias Callable<T> = (T) -> Unit
