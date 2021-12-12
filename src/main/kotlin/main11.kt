import java.io.File

fun main(args: Array<String>) {
  val list = arrayListOf<MutableList<Int>>()
  File("src/main/resources/data.11.txt").forEachLine {
    val line = it.map { ch -> ch.toString().toInt() }
    list.add(line.toMutableList())
  }

  println(String.format("before: %s", list))
//  println(findTotalFlashes(list, 100))
  println(findWhenAllFlash(list))
}

fun findWhenAllFlash(list: List<MutableList<Int>>): Int {
  var cnt = 0

  while (true) {
    cnt++
    if (findFlashes(list) == 100) return cnt
  }
}

fun findTotalFlashes(list: List<MutableList<Int>>, iters: Int): Int {
  var result = 0

  for (i in 0 until iters) {
    result += findFlashes(list)
    println(String.format("%d : %s\nscore: %d\n-----\n", i, list, result))
  }

  return result
}

fun findFlashes(list: List<MutableList<Int>>): Int {
  var result = 0

  // reset counts that flashed last time
  for (r in list.indices) {
    for (c in list[r].indices) {
      if (list[r][c] > 9) list[r][c] = 0
    }
  }
  for (r in list.indices) {
    for (c in list[r].indices) {
      result += incrementAndGetResults(list, r, c)
    }
  }

  return result
}

private fun incrementAndGetResults(
  list: List<MutableList<Int>>,
  r: Int,
  c: Int
): Int {
  var result = 0
  val indicesToCheck = arrayListOf<Pair<Int, Int>>()
  list[r][c]++

  if (list[r][c] == 10) {
    result++
    // add adjacents to our check-list.
    for (i in r - 1..r + 1) {
      for (j in c - 1..c + 1) {
        if ((i >= 0 && i < list.size) && (j >= 0 && j < list.size) && !(i == r && j == c)) {
          indicesToCheck.add(Pair(i, j))
        }
      }
    }
  }

  for (index in indicesToCheck) {
    result += incrementAndGetResults(list, index.first, index.second)
  }
  return result
}