package com.alfred.nightwatch

import io.finch._
import io.finch.syntax._
import io.finch.circe._
import io.circe.generic.auto._
import com.twitter.util.{ Await }
import com.twitter.finagle.Http

import com.alfred.nightwatch.configuration.Configuration.{ HttpConfig }

object Server {

  implicit val nightWatchService: NightWatchService = new NightWatchService()

  val channel: Endpoint[String] = get("channel") {
    Ok("Hello, World!")
  }

  def main(args: Array[String]): Unit = {
    val timer = PingTimer.run

    val api = (channel)

    val server = Http
      .server
      .serve(s":${HttpConfig.port}", api.toServiceAs[Application.Json])

    Await.ready(server)
  }
}

