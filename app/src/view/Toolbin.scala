package view

import scala.swing.*
import scala.swing.event.*

class Toolbin extends BorderPanel {
    this.background = Viewconst.BACKGROUND_MEDIUM
    layout(new Header("Tool Bin")) = BorderPanel.Position.North

    val center = {
        new BoxPanel(Orientation.Vertical) {
            this.opaque = false
            contents += new Toolbin.ToolPallette(3, 4, 6)
            contents += new Toolbin.ColorPallette(5, 9, 6)
        }
    }

    layout(center) = BorderPanel.Position.Center
    this.border = Viewconst.Border(false, true, false, true)
}

object Toolbin {
    class ToolPallette(val rows: Int, val cols: Int, borderWidth: Int)
        extends GridBagPanel {

        private class ToolBox(color: Color, dimension: Int) extends Button {
            this.focusable = false
            this.forceSize(dimension, dimension)
            this.background = color
        }

        private def initBoxes(): Unit = {
            val constraints = new Constraints()
            constraints.insets = new Insets(3, 10, 3, 10)
            val boxes = {
                List.fill(rows * cols)(new ToolBox(Viewconst.BACKGROUND_LIGHT, 65))
            }
            // boxes(0).icon = {
            //     new ImageIcon(
            //       "/home/bryce/Desktop/pixeldrawer/app/resources/icons/pencil.png"
            //     )
            // }
            // boxes(3).icon = {
            //     new ImageIcon(
            //       "/home/bryce/Desktop/pixeldrawer/app/resources/icons/eraser.png"
            //     )
            // }
            // boxes(6).icon = {
            //     new ImageIcon(
            //       "/home/bryce/Desktop/pixeldrawer/app/resources/icons/bucket.png"
            //     )
            // }
            for (col <- 0 until cols) {
                for (row <- 0 until rows) {
                    val box = boxes(row + rows * col)
                    box.border = Swing.MatteBorder(
                      borderWidth,
                      borderWidth,
                      borderWidth,
                      borderWidth,
                      Colors.BLACK
                    )
                    constraints.gridx = col
                    constraints.gridy = row
                    layout(box) = constraints
                }
            }
        }

        this.initBoxes()
        this.opaque = false
    }

    class ColorPallette(val rows: Int, val cols: Int, borderWidth: Int)
        extends GridBagPanel {

        private class ColorBox(color: Color, dimension: Int) extends Button {
            this.forceSize(dimension, dimension)
            this.background = color
        }

        private def initBoxes(): Unit = {
            val constraints = new Constraints()
            val boxes = Colors.values.map(color => new ColorBox(color, 38))
            for (col <- 0 until cols) {
                for (row <- 0 until rows) {
                    val box = boxes(row + rows * col)
                    val halfWidth = borderWidth / 2
                    val top = {
                        if row == 0 then
                            borderWidth
                        else {
                            halfWidth
                        }
                    }
                    val bot = {
                        if row == rows - 1 then
                            6
                        else {
                            halfWidth
                        }
                    }
                    val lft = {
                        if col == 0 then
                            borderWidth
                        else {
                            halfWidth
                        }
                    }
                    val rht = {
                        if col == cols - 1 then
                            6
                        else {
                            halfWidth
                        }
                    }
                    box.border = Swing.MatteBorder(top, lft, bot, rht, Colors.BLACK)
                    constraints.gridx = col
                    constraints.gridy = row
                    layout(box) = constraints
                }
            }
        }

        this.initBoxes()
        this.opaque = false
    }

}
