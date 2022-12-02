package view

import scala.swing.*
import scala.swing.event.*
import javax.swing.text.View
import javax.swing.border.Border
import javax.swing.ImageIcon
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

def clamp(value: Float, min: Float, max: Float): Float = {
    if value < min then
        min
    else if value >= max then
        max
    else {
        value
    }
}

class Editor extends Panel {
    this.forceSize(850, 850)
    this.background = Viewconst.BACKGROUND_LIGHT
    this.border = Viewconst.Border(false, false, true, true)

    val rasterSize = 128

    val rasterBuffer = {
        val image = {
            new BufferedImage(rasterSize, rasterSize, BufferedImage.TYPE_INT_RGB)
        }
        for (x <- 0 until rasterSize) {
            for (y <- 0 until rasterSize) {
                val r = scala.util.Random.between(0, 255)
                val g = scala.util.Random.between(0, 255)
                val b = scala.util.Random.between(0, 255)
                val color = new Color(r, g, b)
                image.setRGB(x, y, color.getRGB())
            }
        }
        image
    }

    val maxScale = rasterSize * 850f
    var scale = 1f
    var offsetX = 0
    var offsetY = 0
    override protected def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        val dim = (rasterSize * scale).toInt
        val pan = (this.size.width / 2) - (dim / 2)
        val offX = pan + (offsetX * scale)
        val offY = pan + (offsetY * scale)
        g.setColor(Colors.RED)
        g.drawImage(rasterBuffer, offX.toInt, offY.toInt, dim, dim, null)
    }

    this.listenTo(mouse.wheel)
    this.listenTo(mouse.clicks)
    reactions += { case wheel: MouseWheelMoved =>
        val delta = 0.05f * scale
        scale += delta * wheel.rotation
        scale = clamp(scale, .25, maxScale * .5f)
    }

    reactions += { case Pan(deltaX, deltaY) =>
        offsetX += deltaX
        offsetY += deltaY
    }

}
