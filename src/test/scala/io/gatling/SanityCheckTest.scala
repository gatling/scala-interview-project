package io.gatling

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class SanityCheckTest extends AnyFlatSpec {
  it should "just compile and run sanity test" in {
    "identity" shouldBe "identity"
  }
}
