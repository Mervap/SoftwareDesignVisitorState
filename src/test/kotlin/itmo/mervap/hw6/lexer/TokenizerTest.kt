package itmo.mervap.hw6.lexer

import itmo.mervap.hw6.BaseTokenTest
import itmo.mervap.hw6.token.*
import org.junit.jupiter.api.Test

class TokenizerTest : BaseTokenTest() {

  @Test
  fun expression() = doTest(
    "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2",
    listOf(OpenParenthesis(), NumberToken(23), AddOperator(), NumberToken(10), CloseParenthesis(),
      MulOperator(), NumberToken(5), SubOperator(), NumberToken(3), MulOperator(),
      OpenParenthesis(), NumberToken(32), AddOperator(), NumberToken(5), CloseParenthesis(), MulOperator(),
      OpenParenthesis(), NumberToken(10), SubOperator(), NumberToken(4), MulOperator(), NumberToken(5), CloseParenthesis(),
      AddOperator(), NumberToken(8), DivOperator(), NumberToken(2))
  )

  @Test
  fun longNumber() = doTest("(45567587000)", listOf(OpenParenthesis(), NumberToken(45567587000L), CloseParenthesis()))

  @Test
  fun whitespaces() = doTest(
    "(1+2 /                   5 +                 2)",
    listOf(OpenParenthesis(), NumberToken(1), AddOperator(), NumberToken(2), DivOperator(), NumberToken(5),
      AddOperator(), NumberToken(2), CloseParenthesis())
  )

  @Test
  fun badExpression() = doTest(
    "( ( (1+ - 2 (",
    listOf(OpenParenthesis(), OpenParenthesis(), OpenParenthesis(), NumberToken(1), AddOperator(), SubOperator(), NumberToken(2), OpenParenthesis())
  )

  @Test
  fun numberAfterNumber() = doTest(
    "10 + 20 30 - 40",
    listOf(NumberToken(10), AddOperator(), NumberToken(20), NumberToken(30), SubOperator(), NumberToken(40))
  )

  @Test
  fun expressionWithNl() = doTest(
    "10 + 20\n * 10",
    listOf(NumberToken(10), AddOperator(), NumberToken(20))
  )

  private fun doTest(input: String, expected: List<Token>) {
    val actual = Tokenizer.getAll(input.byteInputStream())
    compareTokenList(expected, actual)
  }
}