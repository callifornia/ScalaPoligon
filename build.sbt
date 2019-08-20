name := "ScalaPoligon"

version := "1.0.3"
scalaVersion := "2.13.0"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.23" % Test,
  "com.typesafe.akka" %% "akka-actor" % "2.5.23",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.23" % Test,
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
  "org.typelevel" %% "cats-core" % "2.0.0-RC1",
  "org.typelevel" %% "cats-mtl-core" % "0.6.0",
  "org.typelevel" %% "cats-mtl-laws" % "0.6.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalaz" %% "scalaz-core" % "7.2.28"
)