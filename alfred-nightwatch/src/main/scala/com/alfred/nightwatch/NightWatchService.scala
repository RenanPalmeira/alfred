package com.alfred.nightwatch

import java.net.URL
import scala.util.Try
import featherbed.request.ErrorResponse
import com.twitter.finagle.FailedFastException
import com.twitter.logging.Logger
import com.twitter.util.{ Return, Throw }
import com.twitter.finagle.http.Response

import com.alfred.nightwatch.configuration.Configuration.RoutesConfig

class NightWatchService {

  private val log = Logger.get(getClass)

  private val services: List[String] = RoutesConfig.services

  def ping(): Unit = {
    for {
      service <- services
    } yield for {
      link <- Try(new URL(service))
      client <- Try(new featherbed.Client(link))
      request <- Try(client.get(link.getPath).withHeaders("User-Agent" -> "nightwatch-bot").send[Response]())
    } yield request.respond {

      case Return(response) =>
        log.info(s"${link.getHost} - ${response.statusCode}")

      case Throw(ErrorResponse(request, response)) =>
        log.info(s"${link.getHost} - ${response.statusCode}")

      case Throw(e) =>
        NightWatchBot.send(s"${link.getHost} - ***DOWN***", true)
    }
  }

}