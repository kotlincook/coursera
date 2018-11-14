package nicestring

fun String.isNice(): Boolean {
    val cond1 = !contains("bu") && !contains("ba") && !contains("be")
    val cond2 = count { it in "aeiou" } >= 3
    val cond3 = this.filterIndexed{i, ch -> i+1 < this.length && this[i+1] == ch}.isNotEmpty()

    return (cond1 && cond2) || (cond1 && cond3) || (cond2 && cond3)
}