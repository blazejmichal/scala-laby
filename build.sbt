name := "projekty"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies ++= {
  val akkaVersion = "2.6.10"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    //    "uk.gov.hmrc" %% "hmrctest" % "3.10.0-play-26" % Test
  )
}