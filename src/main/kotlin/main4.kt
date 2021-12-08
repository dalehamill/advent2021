import java.io.File

fun main(args: Array<String>) {
  var inputs = ""
  var subList = arrayListOf<String>()
  val list = arrayListOf<List<String>>()
  File("src/main/resources/data.4.txt").forEachLine {
    if (inputs.isEmpty()) {
      inputs = it
    } else {
      val line = it.trim()
      if (line.isNotEmpty()) subList.add(line)
    }

    if (subList.size == 5) {
      list.add(subList)
      subList = arrayListOf<String>()
    }
  }

  println(calculateWinningCardScore(list, inputs))
  println(calculateLastWinningCardScore(list, inputs))
}

fun calculateLastWinningCardScore(list: List<List<String>>, rawInputs: String): Int {
  val inputs = rawInputs.split(",")

  var lowest = -1
  var result = 0
  for (b in list.indices) {
    val board = Board(list[b].map { it.split("\\s+".toRegex()).toMutableList() }.toMutableList())
    val pair = board.findWinningIndexAndScore(inputs, -1)
    if (pair.first > 0 && (lowest == -1 || pair.first > lowest)) {
      lowest = pair.first
      result = pair.second
    }
  }

  return result
}

fun calculateWinningCardScore(list: List<List<String>>, rawInputs: String): Int {
  val inputs = rawInputs.split(",")

  var lowest = -1
  var result = 0
  for (b in list.indices) {
    val board = Board(list[b].map { it.split("\\s+".toRegex()).toMutableList() }.toMutableList())
    val pair = board.findWinningIndexAndScore(inputs, lowest)
    if (pair.first > 0 && (lowest == -1 || pair.first < lowest)) {
      lowest = pair.first
      result = pair.second
    }
  }

  return result
}

class Board(var card: MutableList<MutableList<String>>) {
  // keep track of actual card state as numbers are called
  private val state = card.toMutableList()

  // first five positions; sum of numbers in row that were matched so far (row 0-4). second five for columns.
  private var tally = IntArray(10)

  // return which index/score this board wins on, or -1 if not before cutoff index. -1 cutoff means test all numbers.
  fun findWinningIndexAndScore(inputs: List<String>, cutoff: Int): Pair<Int, Int> {
    for (i in inputs.indices) {
      if (cutoff in 0 until i) break; // ran out of time, fail out
      for (r in state.indices) {
        for (c in state[r].indices) {
          val cell = state[r][c]
          if (cell.equals(inputs[i])) {
            state[r][c] += "x"

            tally[r]++
            if (tally[r] == 5) return Pair(i, getScore(cell.toInt()))

            tally[5 + c]++
            if (tally[5 + c] == 5) return Pair(i, getScore(cell.toInt()))
          }
        }
      }
    }

    return Pair(-1, 0);
  }

  //The score of the winning board can now be calculated.
  // Start by finding the sum of all unmarked numbers on that board;
  // in this case, the sum is 188. Then, multiply that sum by the number that was just called when the board won,
  // 24, to get the final score, 188 * 24 = 4512.
  private fun getScore(lastCall: Int): Int {
    var result = 0
    for (r in state.indices) {
      for (c in state[r].indices) {
        val cell = state[r][c]
        if (!cell.endsWith("x")) result += cell.toInt()
      }
    }
    return result * lastCall
  }
}