val Long.milliseconds: Time get() = Time(this / 1000, (this % 1000).toInt())
val Long.seconds: Time get() = Time(this, 0)
val Long.minutes: Time get() = seconds * 60
val Long.hours: Time get() = minutes * 60

val Int.milliseconds: Time get() = toLong().milliseconds
val Int.seconds: Time get() = toLong().seconds
val Int.minutes: Time get() = toLong().minutes
val Int.hours: Time get() = toLong().hours

private fun Time.toMilliseconds(): Long = seconds * 1000 + milliseconds

operator fun Time.plus(other: Time): Time = (this.toMilliseconds() + other.toMilliseconds()).milliseconds
operator fun Time.minus(other: Time): Time = (this.toMilliseconds() - other.toMilliseconds()).milliseconds
operator fun Time.times(other: Int): Time = (this.toMilliseconds() * other).milliseconds
