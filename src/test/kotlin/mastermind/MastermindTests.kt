package mastermind

import org.junit.Assert
import org.junit.Test

class MastermindTests {

    private fun testEvaluation(secret: String, guess: String, positions: Int, letters: Int) {
        val expected = Evaluation(positions, letters)
        val evaluation = evaluateGuess(secret, guess)
        Assert.assertEquals("Wrong evaluation for secret=$secret, guess=$guess",
                expected, evaluation)
    }

    // simple
    @Test
    fun testEqual() = testEvaluation("ABCD", "ABCD", 4, 0)

    @Test
    fun testOnlyLetters() = testEvaluation("DCBA", "ABCD", 0, 4)

    @Test
    fun testSwap() = testEvaluation("ABCD", "ABDC", 2, 2)

    @Test
    fun testPositions() = testEvaluation("ABCD", "ABCF", 3, 0)

    @Test
    fun testLetters() = testEvaluation("DAEF", "FECA", 0, 3)


    // repeated letters
    @Test
    fun testSample1() = testEvaluation("AABC", "ADFE", 1, 0)

    @Test
    fun testSample2() = testEvaluation("AABC", "ADFA", 1, 1)

    @Test
    fun testSample3() = testEvaluation("AABC", "DEFA", 0, 1)

    @Test
    fun testSample4() = testEvaluation("CFDF", "FCCD", 0, 3)


    // Selbst ausgedachte:
    @Test
    fun testSample5() = testEvaluation("AAAA", "AAAA", 4, 0)

    @Test
    fun testSample6() = testEvaluation("ABAB", "BABA", 0, 4)

    @Test
    fun testSample7() = testEvaluation("ABAB", "BABB", 1, 2)

    @Test
    fun testSample8() = testEvaluation("ABAB", "AABA", 1, 2)

    @Test
    fun testSample9() = testEvaluation("ACAB", "AABB", 2, 1)

    @Test
    fun testSample10() = testEvaluation("ABCB", "CABA", 0, 3)

    @Test
    fun testSample11() = testEvaluation("BCDF", "ACEB", 1, 1)

    @Test
    fun testSample12() = testEvaluation("AAAF", "ABCA", 1, 1)

    @Test
    fun testSample13() = testEvaluation("ABCA", "AAAF", 1, 1)


}
