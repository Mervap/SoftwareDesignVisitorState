package itmo.mervap.hw6.visitor

import itmo.mervap.hw6.BaseTokenTest
import itmo.mervap.hw6.lexer.Tokenizer
import itmo.mervap.hw6.token.*
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CalcVisitorTest : BaseTokenTest() {

  @Test
  fun expression() = doTest("(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2", 1279)

  @Test
  fun numberInParenthesis() = doTest("(20) + (22)", 42)

  @Test
  fun parenthesisInParenthesis() = doTest("((20) + ((12 / 6) - (16+40 * (12)))) - 10", -484)

  private fun doTest(input: String, expected: Long) {
    val tokens = Tokenizer.getAll(input.byteInputStream())
    val revPolNotation = ParserVisitor(tokens).getRevPolNotation()
    val actual = CalcVisitor(revPolNotation).calc()
    assertEquals(expected, actual)
  }
}