package itmo.mervap.hw6.visitor

import itmo.mervap.hw6.BaseTokenTest
import itmo.mervap.hw6.lexer.Tokenizer
import itmo.mervap.hw6.token.*
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParserVisitorTest : BaseTokenTest() {

  @Test
  fun expression() = doTest(
    "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2",
    listOf(
      NumberToken(23), NumberToken(10), AddOperator(), NumberToken(5), MulOperator(),
      NumberToken(3), NumberToken(32), NumberToken(5), AddOperator(),
      NumberToken(10), NumberToken(4), NumberToken(5), MulOperator(), SubOperator(),
      MulOperator(), MulOperator(), SubOperator(), NumberToken(8), NumberToken(2), DivOperator(),
      AddOperator()
    )
  )

  @Test
  fun numberInParenthesis() = doTest(
    "(20) + (22)",
    listOf(NumberToken(20), NumberToken(22), AddOperator())
  )

  @Test
  fun parenthesisInParenthesis() = doTest(
    "((20) + ((12 / 6) - (16+40 * (12)))) - 10",
      listOf(
        NumberToken(20), NumberToken(12), NumberToken(6), DivOperator(),
        NumberToken(16), NumberToken(40), NumberToken(12), MulOperator(), AddOperator(), SubOperator(),
        AddOperator(), NumberToken(10), SubOperator()
      )
  )

  @Test
  fun emptyParenthesis() = doExceptionTest(
    "10 + ()",
    "Empty expression into parenthesis"
  )

  @Test
  fun nonClosedParenthesis() = doExceptionTest(
    "(10 + 20 - (40 + 10)",
    "Non-closed opening parenthesis"
  )

  @Test
  fun noOpenParenthesis() = doExceptionTest(
    "10 + 20 ) - 10",
    "Unexpected closing parenthesis"
  )

  @Test
  fun operatorAfterParenthesis() = doExceptionTest(
    "10 + ( - 10)",
    "Unexpected operator after open parenthesis: SubOperator"
  )

  @Test
  fun closingParenthesisAfterOperator() = doExceptionTest(
    "10 + (10 - )",
    "Unexpected closing parenthesis after the operator SubOperator"
  )

  @Test
  fun operatorAfterOperator() = doExceptionTest(
    "10 + * 10",
    "Unexpected operator after operator: MulOperator after AddOperator"
  )

  @Test
  fun numberAfterNumber() = doExceptionTest(
    "10 + (20 10)",
    "Unexpected number: 10"
  )

  @Test
  fun numberAfterCloseParenthesis() = doExceptionTest(
    "10 + (20 * 10) 15",
    "Unexpected number: 15"
  )

  @Test
  fun noOperand() = doExceptionTest(
    "10 + 20 / ",
    "Expected operand after DivOperator"
  )

  private fun doTest(input: String, expected: List<Token>) {
    val tokens = Tokenizer.getAll(input.byteInputStream())
    val actual = ParserVisitor(tokens).getRevPolNotation()
    compareTokenList(expected, actual)
  }

  private fun doExceptionTest(input: String, message: String) {
    val e = assertThrows(ParserException::class.java) { doTest(input, listOf()) }
    assertEquals(message, e.message)
  }

}