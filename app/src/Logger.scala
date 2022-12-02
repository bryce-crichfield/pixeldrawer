import scala.swing.*
import scala.swing.event.*

object Logger {
    def info(string: String): Unit = {
        println(f"<---$string--->")
    }
}
