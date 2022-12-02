// import java.awt.Font
// import java.awt.GraphicsEnvironment
// import scala.collection.mutable.HashMap
// import scala.io.Source
// import scala.util.Using
// import java.io.ByteArrayInputStream
// import scala.jdk.StreamConverters.*
// import scala.util.Try
// import java.awt.image.BufferedImage
// import javax.imageio.*
// import java.io.File

// object Resources {
//   val root = "/home/bryce/Desktop/pixeldrawer/app/resources/"
//   def loadFont(path: String): Unit = {
//     val ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//     val file = new java.io.File(directory + path)
//     val font = Font.createFont(Font.TRUETYPE_FONT, file)
//     ge.registerFont(font)
//   }
// }

// sealed trait Loadable[A] {
//   def apply(path: String): Option[A]
// }
// object Loadable {
//   def apply[A](
//       operation: String => Option[A]
//   )(extensions: String*): Loadable[A] = {
//     new Loadable[A] {
//       override def apply(path: String): Option[A] = {
//         if !extensions.exists(ext => path.endsWith(ext)) then None
//         else operation(path)
//       }
//     }
//   }
// }

// class ReadonlyCache[A: Loadable](directory: String) {
//   val data = {
//     val load = summon[Loadable[A]]
//     val children = {
//       new File(directory)
//         .list()
//         .toList
//         .map(path => new File(path))
//         .filterNot(_.isDirectory())
//     }
//     val list = for {
//       child <- children
//       value <- load(s"$directory/${child.getName()}")
//     } yield {
//       val name = child.getName().reverse.dropWhile(_ != '.').tail
//       name -> value
//     }
//     list.toMap
//   }
// }

// given Loadable[String] = Loadable.apply { path =>
//   Option {
//     val lines = Source.fromFile(path).getLines()
//     lines.map(_ + '\n').mkString
//   }
// }("txt")
