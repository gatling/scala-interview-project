package io.gatling

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ConcatSpec extends AnyWordSpec with Matchers {
  def concat(opt1: Option[String], opt2: Option[String]): Option[String] = ???

  "concat function" should {
    "concat both content when both exist" in {
      concat(Some("foo"), Some("bar")) shouldBe Some("foobar")
    }

    "abort when at least one does not exist" in {
      concat(Some("foo"), None) shouldBe None
      concat(None, Some("bar")) shouldBe None
      concat(None, None) shouldBe None
    }
  }
}
