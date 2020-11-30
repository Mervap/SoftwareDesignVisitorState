package itmo.mervap.hw6

import itmo.mervap.hw6.token.NumberToken
import itmo.mervap.hw6.token.Token
import kotlin.test.junit5.JUnit5Asserter.assertEquals

abstract class BaseTokenTest {

  protected fun compareTokenList(expected: List<Token>, actual: List<Token>) {
    assertEquals("Size not equals", expected.size, actual.size)
    for (i in expected.indices) {
      compareTokens(expected[i], actual[i])
    }
  }

  private fun compareTokens(expected: Token, actual: Token) {
    assertEquals("Different token class", expected::class.java, actual::class.java)
    if (expected is NumberToken) {
      assertEquals("Different numbers", expected.number, (actual as NumberToken).number)
    }
  }
}