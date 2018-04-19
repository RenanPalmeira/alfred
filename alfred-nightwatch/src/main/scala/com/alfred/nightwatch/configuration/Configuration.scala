package com.alfred.nightwatch.configuration

import com.typesafe.config.ConfigFactory
import collection.JavaConverters._

object Configuration {

  private val config = ConfigFactory.load()

  object HttpConfig {
    private val httpConfig = config.getConfig("http")

    lazy val port = httpConfig.getString("port")
  }

  object BotConfig {
    private val httpConfig = config.getConfig("bot")

    lazy val token = httpConfig.getString("token")
    lazy val chatId = httpConfig.getString("chatId")
  }

  object RoutesConfig {
    private val httpConfig = config.getConfig("routes")

    lazy val services: List[String] = httpConfig.getStringList("services").asScala.toList
  }
}