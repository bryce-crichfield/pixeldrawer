object MathTools {
    // Places `value` into the range of 0 to 1000
    def normalize(value: Int, divisor: Int): Int = {
        (1000 * (value / divisor.toFloat)).toInt
    }

    def clamp(value: Float, min: Float, max: Float): Float = {
        if value < min then
            min
        else if value >= max then
            max
        else {
            value
        }
    }
}
