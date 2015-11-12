package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(true == balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(true == balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("false balance: ':-)' is unbalanced") {
    assert(false == balance("(:-))".toList))
  }

  test("false balance: counting is not enough") {
    assert(false == balance("())(".toList))
  }

  test("balance: empty") {
    assert(true == balance("".toList))
  }

  test("balance: without paranthesis") {
    assert(true == balance("..".toList))
  }
}
