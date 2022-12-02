package view

import scala.swing.*
import scala.swing.event.*
import javax.swing.text.View
import view.Toolbin.ColorPallette
import javax.swing.border.Border
import view.Toolbin.ToolPallette
import javax.swing.ImageIcon

class Root extends BorderPanel {
    new javax.swing.Timer(
      50,
      Swing.ActionListener { _ =>
          this.repaint()
      }
    ).start()

    val menubar = new Menubar()
    val editor = new Editor()

    val lefthand = {
        new BoxPanel(Orientation.Vertical) {
            this.background = Colors.RED
            contents += new Toolbin()
            contents += new Minimap()
        }
    }

    val righthand = {
        new BoxPanel(Orientation.Vertical) {
            contents += new Layerstack()
            contents += new Framestack()
        }
    }

    this.layout(menubar) = BorderPanel.Position.North
    this.layout(editor) = BorderPanel.Position.Center
    this.layout(lefthand) = BorderPanel.Position.West
    this.layout(righthand) = BorderPanel.Position.East

    listenTo(keys)
    editor.listenTo(this)
    reactions += { case press: KeyPressed =>
        press.key match
            case Key.W =>
                this.publish(Pan(0, 1))
            case Key.A =>
                this.publish(Pan(1, 0))
            case Key.S =>
                this.publish(Pan(0, -1))
            case Key.D =>
                this.publish(Pan(-1, 0))
    }
}

case class Pan(deltaX: Int, deltaY: Int) extends Event

class Menubar extends BoxPanel(Orientation.Horizontal) {
    this.forceSize(1600, 50)
    this.background = Viewconst.BACKGROUND_DARK
}

class Menubutton(text: String) extends BoxPanel(Orientation.Vertical) {
    this.contents +=
        new TextField(text) {
            this.foreground = Viewconst.FOREGROUND_DARK
        }
}

class Minimap extends BorderPanel {
    this.forceSize(375, 375)
    this.background = Viewconst.BACKGROUND_MEDIUM
    layout(new Header("Mini Map")) = BorderPanel.Position.North
    this.border = Viewconst.Border(true, false, false, true)
    // Add Render Integration
}

class Layerstack extends BorderPanel {
    this.forceSize(375, 425)
    this.background = Viewconst.BACKGROUND_MEDIUM
    this.layout(new Header("Layers")) = BorderPanel.Position.North
    this.border = Viewconst.Border(false, true, true, false)
    val list_view = new BoxPanel(Orientation.Vertical)
    val stack_panes = List.fill(20)(new Stackpane())
    list_view.contents ++= stack_panes
    this.layout(new ScrollPane(list_view)) = {
        BorderPanel.Position.Center
    }
    // Add Component List Integration
}

class Framestack extends BorderPanel {
    this.forceSize(375, 425)
    this.background = Viewconst.BACKGROUND_MEDIUM
    this.layout(new Header("Frames")) = BorderPanel.Position.North

    this.border = Viewconst.Border(true, false, true, false)
    // Add Component List Integration
}

class Stackpane extends BorderPanel {
    // Add Thumbnail
    val visibility_icon = {
        new ImageIcon("/home/bryce/Desktop/pixeldrawer/app/resources/icons/eye.png")
    }

    val button = {
        new Button {
            this.icon = visibility_icon
            this.forceSize(35, 35)
            this.opaque = false
        }
    }

    this.forceSize(200, 30)
    this.layout(button) = BorderPanel.Position.West
}

class Header(string: String) extends BorderPanel {
    this.opaque = false
    val label = {
        new Label(string) {
            this.forceSize(200, 30)
            this.font = new Font("CascadiaCode", 0, 25)
            this.horizontalTextPosition = Alignment.Center
            this.verticalTextPosition = Alignment.Center
            this.foreground = Viewconst.FOREGROUND_DARK
        }
    }
    this.layout(label) = BorderPanel.Position.Center
}
