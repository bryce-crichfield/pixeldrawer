import mill._, scalalib._
import $ivy.`com.goyeau::mill-scalafix::0.2.11`
import com.goyeau.mill.scalafix.ScalafixModule


object app extends ScalaModule with ScalafixModule {
    def scalaVersion = "3.2.0"
    def ivyDeps = Agg(ivy"org.scala-lang.modules::scala-swing:3.0.0")

    def scalafixIvyDeps = Agg(
      ivy"com.github.liancheng::organize-imports:0.6.0"
    )
}
