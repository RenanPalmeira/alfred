package com.alfred.nightwatch.configuration

import com.typesafe.config.ConfigFactory

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
}