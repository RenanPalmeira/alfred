package com.alfred.nightwatch

import java.net.URL
import scala.util.Try
import com.twitter.util.{ Future, Futures, Return, Throw }
import com.twitter.finagle.http.{ Request, Response, Method }

import com.alfred.nightwatch.configuration.Configuration.RoutesConfig

class NightWatchService {
  private val services: List[String] = RoutesConfig.services

  def ping(): Unit = {
    for {
      service <- services
    } yield for {
      link <- Try(new URL(service))
      client <- Try(new featherbed.Client(link))
      request <- Try(client.get(link.getPath).send[Response]())
    } yield request.respond {
      case Return(response) => NightWatchBot.send(s"${link.getHost} - ${response.statusCode}")
      case Throw(throwable) => NightWatchBot.send(s"${link.getHost} - ${throwable.getMessage}")
    }
  }

}