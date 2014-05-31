package connecteverything

/**
 * Created by fela on 5/31/14.
 */
object GenerateLevels {
  def main(args: Array[String]) {
    val grid = new Grid(11, 11, true)
    println(grid)
    println(grid.hasEasySolution)
  }
}
