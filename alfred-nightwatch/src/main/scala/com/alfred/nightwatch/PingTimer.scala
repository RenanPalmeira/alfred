package com.alfred.nightwatch

import com.twitter.conversions.time._
import com.twitter.util.ScheduledThreadPoolTimer

object PingTimer {

  private val timer = new ScheduledThreadPoolTimer

  def run(implicit nightWatchService: NightWatchService) =
    timer.schedule(10.minutes) {
      nightWatchService.ping()
    }

}