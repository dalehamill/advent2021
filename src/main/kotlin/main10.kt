import java.io.File
import kotlin.math.sign

fun main(args: Array<String>) {
  val list = arrayListOf<String>()
  File("src/main/resources/data.10.txt").forEachLine {
    list.add(it)
  }

  println(String.format("Part 1: %d", calcTotalSyntaxScore(list.toList())))
  val scores = calcIncompleteScores(list.toList()).sorted()
  println(String.format("Part 2: %d", scores.get(scores.size / 2)))
}

fun calcIncompleteScores(list: List<String>): List<Long> {
  val scores = arrayListOf<Long>()

  for (str in list) {
    var score = 0L

    // ): 1 point.
    // ]: 2 points.
    // }: 3 points.
    // >: 4 points.
    val corruptResult = findCorruptedStringRemaining(str)
    if (!corruptResult.first) {
      for (c in corruptResult.second.reversed()) {
        score *= 5
        when (c) {
          '(' -> score += 1
          '[' -> score += 2
          '{' -> score += 3
          '<' -> score += 4
        }
      }
      scores.add(score)
    }
  }

  return scores
}

fun calcTotalSyntaxScore(list: List<String>): Long {
  var score = 0L

  for (str in list) {

    // ): 3 points.
    // ]: 57 points.
    // }: 1197 points.
    // >: 25137 points.
    val corruptResult = findCorruptedStringRemaining(str)
    if (corruptResult.first) {
      when (corruptResult.second.lastOrNull()) {
        ')' -> score += 3
        ']' -> score += 57
        '}' -> score += 1197
        '>' -> score += 25137
      }
    }
  }

  return score
}

fun findCorruptedStringRemaining(str: String): Pair<Boolean, List<Char>> {
  val stack = ArrayDeque<Char>()
  for(c in str.toCharArray()) {
    when(c) {
      '(' -> stack.add(c)
      '[' -> stack.add(c)
      '{' -> stack.add(c)
      '<' -> stack.add(c)
      else -> {
        val top = stack.removeLast()
        if (!(top == '(' && c == ')' ||
            top == '[' && c == ']' ||
            top == '{' && c == '}' ||
            top == '<' && c == '>')) return Pair(true, arrayListOf(c));
      }
    }
  }

  return Pair(false, stack.toList())
}