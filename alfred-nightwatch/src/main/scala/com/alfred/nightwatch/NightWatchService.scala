package com.alfred.nightwatch

import java.net.URL
import scala.util.Try
import featherbed.request.ErrorResponse
import com.twitter.finagle.FailedFastException
import com.twitter.logging.Logger
import com.twitter.util.{ Future, Return, Throw }
import com.twitter.finagle.http.Response

import com.alfred.nightwatch.configuration.Configuration.RoutesConfig

class NightWatchService {

  private val log = Logger.get(getClass)

  private val services: List[String] = RoutesConfig.services

  def fetchService(service: String): Future[Response] = {
    val link = new URL(service)
    val client = new featherbed.Client(link)
    client
      .get(link.getPath)
      .withHeaders("User-Agent" -> "nightwatch-bot")
      .send[Response]()
      .onSuccess {
        response => log.info(s"${link.getHost} - ${response.statusCode}")
      }
      .onFailure {
        case ErrorResponse(request, response) =>
          log.info(s"${link.getHost} - ${response.statusCode}")

        case e: Throwable => {
          log.error(s"${link.getHost} - ${e.getMessage}")
          NightWatchBot.send(s"${link.getHost} - ***DOWN***", true)
        }
      }
  }

  def ping(): Unit = {
    for {
      service <- services
    } yield fetchService(service)
  }

}