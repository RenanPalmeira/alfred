package com.alfred.nightwatch

import com.twitter.conversions.time._
import com.twitter.util.ScheduledThreadPoolTimer

object PingTimer {

  private val timer = new ScheduledThreadPoolTimer

  def run() = timer.schedule(5.seconds) {
    print("clock")
    print(Thread.currentThread().getName())
  }

}