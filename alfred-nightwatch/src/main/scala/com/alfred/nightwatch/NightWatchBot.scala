package com.alfred.nightwatch

import com.twitter.util.{ Future, FuturePool }

import info.mukel.telegrambot4s._, api._, methods._

import com.alfred.nightwatch.configuration.Configuration.{ BotConfig }

object NightWatchBot extends TelegramBot {

  def token: String = BotConfig.token
  val chatId: String = BotConfig.chatId

  def send(message: String, notify: Boolean = false): Future[Unit] =
    FuturePool.unboundedPool {
      val telegramMessage: SendMessage = SendMessage(
        chatId = chatId,
        text = message,
        parseMode = Some(ParseMode.Markdown),
        disableWebPagePreview = Some(true),
        disableNotification = Some(!notify)
      )

      request(telegramMessage)
    }
}