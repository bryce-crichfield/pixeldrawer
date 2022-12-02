import scala.swing.*
import scala.swing.event.*
import java.awt.image.BufferedImage
import java.awt.BasicStroke
import java.awt.geom.Line2D
import java.awt.event.InputEvent
// def scale(delta: Int): Unit = {
//   pixel_width += 1 * delta
//   pixel_height += 1 * delta
// }

// listenTo(mouse.wheel)
// listenTo(mouse.moves)
// listenTo(mouse.clicks)

// var mouse_pos_x = 0
// var mouse_pos_y = 0

// reactions += {
// case MouseWheelMoved(_, _, _, rotation) =>
//   if pixel_width > 0 && rotation == -1 then
//     scale(-1)
//   if pixel_width < this.size.width && rotation == 1 then
//     scale(1)

// case MouseMoved(_, loc: Point, modifiers) =>
//   if (modifiers & InputEvent.SHIFT_DOWN_MASK) != InputEvent.SHIFT_DOWN_MASK then ()
//   else
//     val delta_x = loc.x - mouse_pos_x
//     val delta_y = loc.y - mouse_pos_y
//     mouse_pos_x = loc.x
//     mouse_pos_y = loc.y
//     pan_x += Math.signum(delta_x).toInt
//     pan_y += Math.signum(delta_y).toInt

//   case MouseClicked(_, loc: Point, _, _, _) =>
//     // TODO: Weird Clamping Issues
//     // The clicks dont resolve to the nearest pixel properly
//
// }

// Encapsulates a in-memory raster that defines the working image
// that the editor is editing
class EditorModel(rasterBuffer: BufferedImage) {

    def this(bufferWidth: Int, bufferHeight: Int) = {
        this(
          ImageTools.load(
            "/home/bryce/Desktop/pixeldrawer/app/skull.png",
            bufferWidth,
            bufferHeight
          )
        )
    }

    private var pixelScale = 1
    private var panX = 0
    private var panY = 0
    private var nPixelsX = rasterBuffer.getWidth() / pixelScale
    private var nPixelsY = rasterBuffer.getHeight() / pixelScale

    // Clips the area of the rasterBuffer that we are wishing to display
    // Calculates the number of 'virtual' pixels based on pixel scale
    def clip(): BufferedImage = {
        // if pixelScale == 1 then rasterBuffer
        // else
        println("HERE")
        nPixelsX = rasterBuffer.getWidth() / pixelScale
        nPixelsY = rasterBuffer.getHeight() / pixelScale
        rasterBuffer.getSubimage(panX, panY, nPixelsX, nPixelsY)
    }

    // Expects the pixel position in the normal range of 0 to 1000
    def setPixel(percentX: Float, percentY: Float, color: Color) = {
        val image_x = (panX + (percentX * nPixelsX)).toInt
        val image_y = (panY + (percentY * nPixelsY)).toInt
        rasterBuffer.setRGB(image_x, image_y, 0)
    }

    // FIXME
    def zoom(delta: Int): Unit = {
        if delta < 0 && pixelScale == 1 then
            println(f"cant go lower $pixelScale")
        else if delta > 0 && pixelScale == 100 then
            println(f"cant go higher $pixelScale")
        else {
            println(pixelScale)
            pixelScale += delta
        }
    }

    // FIXME
    def pan(deltaX: Float, deltaY: Float): Unit = {
        panX += deltaX.toInt
        panY += deltaY.toInt
        MathTools.clamp(panX, 0, rasterBuffer.getWidth())
        MathTools.clamp(panY, 0, rasterBuffer.getHeight())

        println(panX + ", " + panY)
    }
}

class EditorView() extends Panel {
    preferredSize = new Dimension(850, 850)

    // We assume that the framebuffer must follow an invariant
    // such that the framebuffer's real pixel size is equal to this
    // panel's real pixel size
    private val frameBuffer = {
        new BufferedImage(850, 850, BufferedImage.TYPE_INT_RGB)
    }

    private var lineGridWidth = 0.0f
    private var lineGridHeight = 0.0f

    def render(clip: BufferedImage): Unit = {
        val graphics = frameBuffer.createGraphics()
        graphics.drawImage(
          clip,
          0,
          0,
          frameBuffer.getWidth(),
          frameBuffer.getHeight(),
          null
        )
        graphics.dispose()
        lineGridWidth = {
            frameBuffer.getWidth() / clip.getWidth().toFloat
        }
        lineGridHeight = {
            frameBuffer.getHeight() / clip.getHeight().toFloat
        }
        this.repaint()
    }

    override protected def paintComponent(g: Graphics2D): Unit = {
        super.paintComponent(g)
        g.drawImage(frameBuffer, 0, 0, null)
        // this.drawGrid(g)
    }

    private def drawGrid(graphics: Graphics2D): Unit = {
        graphics.setColor(new Color(255, 255, 255))
        val stroke = new BasicStroke(2)
        graphics.setStroke(stroke)

        for (x <- 0 until frameBuffer.getWidth()) {
            graphics.draw(
              new Line2D.Float(
                x * lineGridWidth,
                0,
                x * lineGridWidth,
                frameBuffer.getHeight()
              )
            )
        }

        for (y <- 0 until frameBuffer.getHeight()) {
            graphics.draw(
              new Line2D.Float(
                0,
                y * lineGridHeight,
                this.size.width,
                y * lineGridHeight
              )
            )
        }
    }

    // Per MVC the view forwards all input to the view
    val events = new Publisher {}
    listenTo(mouse.clicks)
    listenTo(mouse.wheel)
    listenTo(mouse.moves)

    reactions += { case click: MouseClicked =>
        val normalizedLocation = {
            new Point(
              MathTools.normalize(click.point.x, frameBuffer.getWidth()),
              MathTools.normalize(click.point.y, frameBuffer.getHeight())
            )
        }
        val event = {
            click.copy(point = normalizedLocation)(click.peer)
        }
        events.publish(event)
    }

    reactions += { case scroll: MouseWheelMoved =>
        events.publish(scroll)
    }

    reactions += { case dragged: MouseDragged =>
        val normalizedLocation = {
            new Point(
              MathTools.normalize(dragged.point.x, frameBuffer.getWidth()),
              MathTools.normalize(dragged.point.y, frameBuffer.getHeight())
            )
        }
        val event = {
            dragged.copy(point = normalizedLocation)(dragged.peer)
        }
        events.publish(event)
    }
}

class EditorController(model: EditorModel, view: EditorView) extends Reactor {
    this.listenTo(view.events)

    private var mouseX = 0
    private var mouseY = 0

    def update(): Unit = {
        view.render(model.clip())
    }

    // Delegate Mouse Click to Model (Expects CLICK TO BE NORMALIZED 0 to 1000!)
    reactions += { case event: MouseClicked =>
        model.setPixel(
          event.point.x / 1000f,
          event.point.y / 1000f,
          new Color(0, 0, 0)
        )
    }

    reactions += { case event: MouseWheelMoved =>
        model.zoom(event.rotation)
    // if (mods & InputEvent.SHIFT_DOWN_MASK) != InputEvent.SHIFT_DOWN_MASK then ()
    // else model.zoom(delta)
    }

    reactions += { case event: MouseDragged =>
        val deltaX = event.point.x - mouseX
        val deltaY = event.point.y - mouseY
        mouseX = event.point.x
        mouseY = event.point.y
        model.pan(deltaX, deltaY)
    }
}
