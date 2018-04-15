lazy val root = (project in file(".")).
  settings(
    organization := "alfred",
    scalaVersion := "2.12.5",
    version      := "0.1.0-SNAPSHOT",
    name := "nightwatch",
    mainClass in Global := Some("com.alfred.nightwatch.Server"),
    assemblyJarName in assembly := s"${name.value}-${version.value}.jar",
    libraryDependencies ++= Seq(
      "org.scalacheck"     %% "scalacheck"    % "1.13.4"  % "test",
      "org.scalatest"      %% "scalatest"     % "3.0.5"   % "test",
      "com.github.finagle" %% "finch-core"    % "0.18.1"          ,
      "com.github.finagle" %% "finch-circe"   % "0.18.1"          ,
      "io.circe"           %% "circe-generic" % "0.9.1"           ,
      "info.mukel"         %% "telegrambot4s" % "3.0.14"          ,
      "com.typesafe"       %  "config"        % "1.3.1"           ,
      "org.slf4j"          %  "slf4j-api"     % "1.7.21"          ,
      "org.slf4j"          %  "slf4j-simple"  % "1.7.21"
    )
  )

assemblyMergeStrategy in assembly ~= (old => {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.first
  case x => old(x)
})
