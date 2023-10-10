sealed interface Result<out S, out E> {
    fun getOrNull(): S? = when (this) {
        is Ok -> value
        is Error -> null
    }

    data class Ok<out S>(val value: S) : Result<S, Nothing>
    data class Error<out E>(val error: E) : Result<Nothing, E>
}
