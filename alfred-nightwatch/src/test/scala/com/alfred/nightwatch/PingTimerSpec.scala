package com.alfred.nightwatch

import com.twitter.finagle.http.Status
import com.twitter.io.Buf
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._
import io.finch.syntax._
import java.nio.charset.StandardCharsets
import java.util.UUID
import org.scalacheck.{ Arbitrary, Gen }
import org.scalatest.{ FlatSpec, Matchers }
import org.scalatest.prop.Checkers

class PingTimerSpec extends FlatSpec with Matchers with Checkers {

  it should "initial timer" in {
    val timer = PingTimer.run()
    Thread.sleep(10000)
    timer.close()
  }

}
