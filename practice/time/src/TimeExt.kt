val Long.milliseconds: Time get() = Time(this / 1000, (this % 1000).toInt())
val Long.seconds: Time get() = Time(this, 0)
val Long.minutes: Time get() = seconds * 60
val Long.hours: Time get() = minutes * 60

val Int.milliseconds: Time get() = toLong().milliseconds
val Int.seconds: Time get() = toLong().seconds
val Int.minutes: Time get() = toLong().minutes
val Int.hours: Time get() = toLong().hours

operator fun Time.plus(other: Time): Time {
    val millisecondsSum = this.milliseconds + other.milliseconds
    return Time(this.seconds + other.seconds + millisecondsSum / 1000, millisecondsSum % 1000)
}
operator fun Time.minus(other: Time): Time {
    val millisecondsDiff = milliseconds - other.milliseconds
    return Time(this.seconds - other.seconds + if (millisecondsDiff < 0) 1 else 0, millisecondsDiff.mod(1000))
}
operator fun Time.times(other: Int): Time {
    val millisecondsProd = this.milliseconds * other.toLong()
    return Time(this.seconds * other + millisecondsProd / 1000, (millisecondsProd % 1000).toInt())
}
