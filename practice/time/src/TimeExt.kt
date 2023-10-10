import kotlin.math.absoluteValue

val Long.milliseconds: Time get() = Time(this / 1000, (this % 1000).toInt())
val Long.seconds: Time get() = Time(this, 0)
val Long.minutes: Time get() = Time(this * 60, 0)
val Long.hours: Time get() = Time(this * 60 * 60, 0)

val Int.milliseconds: Time get() = this.toLong().milliseconds
val Int.seconds: Time get() = this.toLong().seconds
val Int.minutes: Time get() = this.toLong().minutes
val Int.hours: Time get() = this.toLong().hours

operator fun Time.plus(other: Time): Time {
    val millisecondsSum = this.milliseconds + other.milliseconds
    return Time(this.seconds + other.seconds + millisecondsSum / 1000, millisecondsSum % 1000)
}

operator fun Time.minus(other: Time): Time {
    val millisecondsDiff = (this.milliseconds - other.milliseconds).absoluteValue
    return Time(this.seconds - other.seconds - millisecondsDiff / 1000, millisecondsDiff % 1000)
}

operator fun Time.times(other: Int): Time {
    val millisecondsProd = this.milliseconds * other
    return Time(this.seconds * other + millisecondsProd / 1000, millisecondsProd % 1000)
}
