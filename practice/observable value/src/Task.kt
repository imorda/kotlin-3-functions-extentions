interface Value<out T> {
    val value: T

    fun observe(subscriber: Callable<T>): Cancellable
}

fun interface Cancellable {
    fun cancel()
}

abstract class AbstractValue<T> : Value<T> {
    private val subscribers: MutableSet<Callable<T>> = mutableSetOf()

    override fun observe(subscriber: Callable<T>): Cancellable {
        subscribers.add(subscriber)
        return Cancellable {
            if (!subscribers.remove(subscriber)) {
                throw InvalidCancelHandle("Observer is already cancelled")
            }
        }
    }

    // No logging logic in case of an error, so no action on failure.
    protected fun callEvent(value: T) = subscribers.forEach { it.runCatching { this(value) } }
}

class MutableValue<T>(initial: T) : AbstractValue<T>() {
    override fun observe(subscriber: Callable<T>): Cancellable {
        subscriber(value)
        return super.observe(subscriber)
    }

    override var value: T = initial
        set(newVal) {
            callEvent(newVal)
            field = newVal
        }
}

class InvalidCancelHandle(message: String) : IllegalStateException(message)

typealias Callable<T> = (T) -> Unit
