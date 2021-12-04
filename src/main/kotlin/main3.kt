import java.io.File

fun main(args: Array<String>) {
  val list = arrayListOf<String>()
  File("src/main/resources/data.3.txt").forEachLine {
    list.add(it)
  }

  println(calculatePowerConsumption(list))
  println(calculateLifeSupportRating(list))
}

fun calculatePowerConsumption(list: List<String>): Int {
  var cntOnesList = countOnesInPositionToList(list)

  var gamma = ""
  var epsilon = ""
  // count at each position will be > list.size/2 for gamma, < for epsilon
  for (i in cntOnesList.indices) {
    gamma += if (cntOnesList[i] > list.size / 2) "1" else "0"
    epsilon += if (cntOnesList[i] < list.size / 2) "1" else "0"
  }

  return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
}

fun calculateLifeSupportRating(list: List<String>): Int {
  val co2 = Integer.parseInt(findLifeSupportComponent(list, true), 2)
  val oxy = Integer.parseInt(findLifeSupportComponent(list, false), 2)
  return co2 * oxy
}

private fun findLifeSupportComponent(list: List<String>, less: Boolean): String {
  var sublist = list
  val itemLength = list[0].length

  for(i in 0..itemLength) {
    sublist = filterAtIndex(sublist, i, less)
    if (sublist.size == 1) return sublist[0]
  }

  return "nope"
}

// filter out any strings from list that match less/more status on index provided
private fun filterAtIndex(list: List<String>, index: Int, less: Boolean): List<String> {
  var countOnes = 0
  for (i in list.indices) {
    if (list[i].get(index) == '1') countOnes++
  }

  val mostCommonChar = if (countOnes * 2 >= list.size) '1' else '0'
  return list.filter {
    (!less && it.get(index) == mostCommonChar) || (less && it.get(index) != mostCommonChar)
  }
}

// produces a list of ints, each item is the number of ones found in that position in the input
private fun countOnesInPositionToList(list: List<String>): MutableList<Int> {
  var cntOnesList = MutableList(list[0].length) { 0 }

  // add each digit, in place, to it's index in cntOnesList, which gives us count of 1's per position
  for (i in list.indices) {
    for (j in list[i].indices) {
      cntOnesList[j] += list[i][j].toString().toInt()
    }
  }
  return cntOnesList
}