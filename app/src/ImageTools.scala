import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object ImageTools {
    def create(width: Int, height: Int): BufferedImage = {
        new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    }

    def load(path: String, width: Int, height: Int): BufferedImage = {
        val source = ImageIO.read(new java.io.File(path))
        val result = this.create(width, height)
        val graphics = result.createGraphics()
        graphics.drawImage(source, 0, 0, width, height, null)
        graphics.dispose()
        result
    }
}
