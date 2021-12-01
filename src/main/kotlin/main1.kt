import java.io.File

fun main(args: Array<String>) {
  val nlist = arrayListOf<Int>()
  File("src/main/resources/data.1.txt").forEachLine {
    nlist.add(it.substringBefore(" ").toInt())
  }
  println(countSlidingWindowIncreases(nlist, 1))
  println(countSlidingWindowIncreases(nlist, 3))
}

//fun countIncreases(list: List<Int>): Int {
//  var result = 0
//  list.forEachIndexed { index, i ->
//    if (index > 0 && list[index] > list[index - 1]) result++
//  }
//
//  return result
//}

fun countSlidingWindowIncreases(nlist: List<Int>, windowSize: Int): Int {
  var result = 0
  val slist = arrayListOf<Int>()

  nlist.forEachIndexed { index, i ->
    if (index >= windowSize - 1) {
      var windowSum = 0
      for (j in index - windowSize + 1..index) windowSum += nlist[j]
      slist.add(windowSum)
    }
  }
//  println(" -- slist -> $slist")

  slist.forEachIndexed { index, i ->
    if (index > 0 && i > slist[index - 1]) result++
  }

  return result
}