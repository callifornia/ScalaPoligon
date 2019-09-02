name := "ScalaPoligon"

version := "1.0.3"
scalaVersion := "2.13.0"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.25",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.25" % Test,

  "com.typesafe.akka" %% "akka-actor" % "2.5.25",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.25" % Test,

  "com.typesafe.akka" %% "akka-remote" % "2.5.25",
  "com.typesafe.akka" %% "akka-cluster" % "2.5.25",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.5.25",

  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
  "com.typesafe.akka" %% "akka-distributed-data" % "2.5.25",
//  "com.typesafe.akka" %% "akka-cluster-singleton" % "2.5.25",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.25",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.5.25",
  "org.typelevel" %% "cats-core" % "2.0.0-RC1",
  "org.typelevel" %% "cats-effect" % "2.0.0-RC1",
  "org.typelevel" %% "cats-mtl-core" % "0.6.0",
  "org.typelevel" %% "cats-mtl-laws" % "0.6.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalaz" %% "scalaz-core" % "7.2.28"
)