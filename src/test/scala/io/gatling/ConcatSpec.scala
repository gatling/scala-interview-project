package io.gatling

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ConcatSpec extends AnyWordSpec with Matchers {

  // If both of 2 parameters are defined return concat of both else return None
  def concat(opt1: Option[String], opt2: Option[String]): Option[String] = {
    if (opt1.isDefined && opt2.isDefined) Some(opt1.get + opt2.get) else None
  }

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
