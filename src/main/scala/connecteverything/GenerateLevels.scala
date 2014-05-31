package connecteverything

/**
 * Created by fela on 5/31/14.
 */
object GenerateLevels {
  def main(args: Array[String]) {
    val grid = new Grid(5, 5, false)
    println(grid)
    println(grid.hasEasySolution)
  }
}
