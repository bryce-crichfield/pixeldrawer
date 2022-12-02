import scala.swing.*
import scala.swing.event.*

object Application extends SwingApplication {
    override def startup(arg: Array[String]): Unit = {
        Logger.info("Application Startup")
        val frame = {
            new MainFrame {
                contents = new view.Root()

            }
        }
        frame.pack()
        frame.visible = true
        frame.resizable = false
        frame.centerOnScreen()
    }

    override def shutdown(): Unit = {
        println("Application Shutdown")
    }
}
