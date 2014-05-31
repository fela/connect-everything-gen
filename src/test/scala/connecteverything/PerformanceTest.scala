package connecteverything

import java.util.Calendar

/**
 * Created by fela on 5/31/14.
 */
object PerformanceTest {

  def time[R](block: => R): Long = {
    val t0 = System.nanoTime() / 1000000
    val result = block    // call-by-name
    val t1 = System.nanoTime() / 1000000
    println("Elapsed time: " + (t1 - t0) + "ms")
    (t1 - t0)
  }

  def main(args: Array[String]) {
    val times = (0 to 35).map(i => time {
      val width = 4+i/4
      val height = 7+(i+1)/4
      println(width, height)
      new Grid(width, height, true).numberOfSolutions
    })
    val sortedTimes = times.sorted
    println("Median: " + sortedTimes(9))
  }
}
