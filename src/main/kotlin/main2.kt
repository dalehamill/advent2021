import java.io.File

fun main(args: Array<String>) {
  val list = arrayListOf<String>()
  File("src/main/resources/data.2.txt").forEachLine {
    list.add(it)
  }
  println(calculatePositionFactor(list))
  println(calculatePositionFactor2(list))
}

fun calculatePositionFactor(list: List<String>): Int {
  var hor = 0
  var depth = 0

  for (i in list.indices) {
    val command = list[i].split(" ")
    when (command[0]) {
      "forward" -> hor += command[1].toInt()
      "down" -> depth += command[1].toInt()
      "up" -> depth -= command[1].toInt()
    }
  }

  return hor * depth
}

fun calculatePositionFactor2(list: List<String>): Long {
  var hor = 0L
  var depth = 0L
  var aim = 0L

  for (i in list.indices) {
    val command = list[i].split(" ")
    when (command[0]) {
      "forward" -> {
        hor += command[1].toInt()
        depth += aim * command[1].toInt()
      }
      "down" -> aim += command[1].toInt()
      "up" -> aim -= command[1].toInt()
    }
  }

  return hor * depth
}