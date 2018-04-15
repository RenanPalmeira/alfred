package com.alfred.nightwatch

import com.twitter.util.{ Future, FuturePool }

import info.mukel.telegrambot4s._, api._, methods._

import com.alfred.nightwatch.configuration.Configuration.{ BotConfig }

object NightWatchBot extends TelegramBot {

  def token = BotConfig.token
  def chatId = BotConfig.chatId

  def send(message: String): Future[Unit] =
    FuturePool.unboundedPool(request(SendMessage(chatId, message)))
}