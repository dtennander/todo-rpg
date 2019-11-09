name := "todo-rpg"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions ++= Seq(
  "-Ypartial-unification",
  "-language:higherKinds",
)
guardrailTasks in Compile := List(
  ScalaServer(file("api.yml"), pkg="com.github.dtennander.todorpg.endpoints", framework="http4s"),
)

enablePlugins(JavaAppPackaging)

val Http4sVersion = "0.20.0"
val CirceVersion = "0.11.1"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"

libraryDependencies ++= Seq(
  "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s"      %% "http4s-circe"        % Http4sVersion,
  "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
  "io.circe"        %% "circe-generic"       % CirceVersion,
  "io.circe"        %% "circe-java8"         % CirceVersion,
  "org.specs2"      %% "specs2-core"         % Specs2Version % "test",
  "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
  "com.google.api-client" % "google-api-client" % "1.22.0"
)