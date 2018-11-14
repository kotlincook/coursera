package mastermind

fun evaluateGuess1(secret: String, guess: String): Evaluation {

    fun String.replaceHitsWithLowDashs(pattern: String) =
            this.mapIndexed { i, ch -> if (ch == pattern[i]) '_' else ch }

    val guessWithoutHits = guess.replaceHitsWithLowDashs(secret)
    val secretWithoutHits = secret.replaceHitsWithLowDashs(guess)

    val positions = secretWithoutHits.count { it == '_' }
    val letters = secretWithoutHits.flatMap {
        ch -> guessWithoutHits.map { it != '_' && it == ch }
    }.count { it }
    return Evaluation(positions, letters)
}


fun evaluateGuess2(secret: String, guess: String): Evaluation {

    var positions = 0
    var letters = 0

    for (i in (0 until guess.length)) {
        if (guess[i] == secret[i]) positions++
        else if (guess[i] in secret) letters++
    }
    return Evaluation(positions, letters)
}


fun evaluateGuess3(secret: String, guess: String): Evaluation {

    val guessesCandidates = guess.toMutableList().toMutableSet()
    val positionCandidates = guess.toMutableList().toMutableSet()
    var positions = 0
    var letters = 0

    for (i in (0 until guess.length)) {
        val ch = guess[i]
        when {
            ch == secret[i] && (ch in positionCandidates) -> {
                positions++
                positionCandidates.remove(ch)
            }
            ch in secret && (ch in guessesCandidates)-> {
                letters++
                guessesCandidates.remove(ch)
            }
        }
    }
    return Evaluation(positions, letters)
}

fun evaluateGuess4(secret: String, guess: String): Evaluation {

    val alreadyUsedForPositions = mutableSetOf<Char>()
    val alreadyUsedForLetters = mutableSetOf<Char>()
    var positions = 0
    var letters = 0

    for (i in (0 until guess.length)) {
        val ch = guess[i]
        when {
            ch == secret[i] && ch !in alreadyUsedForPositions -> {
                positions++
                alreadyUsedForPositions.add(ch)
            }
            ch in secret && ch !in alreadyUsedForLetters -> {
                letters++
                alreadyUsedForLetters.add(ch)
            }
        }
    }
    return Evaluation(positions, letters)
}

infix fun String.pointwiseLetterMinus(str: String): Pair<String, Int> {
    var counter = 0
    var result = ""
    for (i in (0 until this.length)) {
        result += if (this[i] == str[i]) {
            counter++
            "_"
        } else this[i]
    }
    return result to counter
}


fun evaluateGuess5(secret: String, guess: String): Evaluation {
    val NIL = ' '
    val secretMutable = secret.toMutableList()
    val guessMutable = guess.toMutableList()
    var positions = 0
    var letters = 0

    for (pos in 0 until guessMutable.size) {
        if (guessMutable[pos] == secretMutable[pos]) {
            secretMutable[pos] = NIL
            guessMutable[pos] = NIL
            positions++
        }
    }

    for (pos in 0 until guessMutable.size) {
        val idx = if (guessMutable[pos] == NIL) -1 else secretMutable.indexOf(guessMutable[pos])
        if (idx >= 0) {
            letters++
            secretMutable[idx] = NIL
            guessMutable[pos] = NIL
        }
    }
    return Evaluation(positions, letters)
}
