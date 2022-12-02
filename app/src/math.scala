case class V2D(_1: Float, _2: Float) {
    inline def x: Float = this._1
    inline def y: Float = this._2
    inline def w: Float = this._1
    inline def h: Float = this._2

    inline def x_=(value: Float): V2D = this.copy(_1 = value)
    inline def y_=(value: Float): V2D = this.copy(_2 = value)
    inline def w_=(value: Float): V2D = this.copy(_1 = value)
    inline def h_=(value: Float): V2D = this.copy(_2 = value)

    inline def + (value: Float): V2D = {
        V2D(this.x + value, this.y + value)
    }

    inline def - (value: Float): V2D = {
        V2D(this.x - value, this.y - value)
    }

    inline def * (value: Float): V2D = {
        V2D(this.x * value, this.y * value)
    }

    inline def / (value: Float): V2D = {
        V2D(this.x / value, this.y / value)
    }

    inline def + (that: V2D): V2D = {
        V2D(this.x + that.x, this.y + that.y)
    }

    inline def - (that: V2D): V2D = {
        V2D(this.x - that.x, this.y - that.y)
    }

    inline def * (that: V2D): V2D = {
        V2D(this.x * that.x, this.y * that.y)
    }

    inline def / (that: V2D): V2D = {
        V2D(this.x / that.x, this.y / that.y)
    }
}
