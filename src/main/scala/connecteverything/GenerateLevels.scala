package connecteverything

/**
 * Created by fela on 5/31/14.
 */
object GenerateLevels {
  def main(args: Array[String]) {
    val grid = new Grid(13, 13, true)
    //println(grid.hasEasySolution)
    println(grid)
    println(grid.numberOfSolutions)
  }
}
