package connecteverything

import scala.util.Random.nextInt

object Direction {
  val allDirections = IndexedSeq(Up, Right, Down, Left)
  def randomDirection(up:Int=1, right:Int=1, down:Int=1, left:Int=1) = {
    allDirections(nextInt(allDirections.length))
  }
}

sealed abstract class Direction {
  def opposite: Direction
}

case object Up extends Direction {
  def opposite: Direction = Down
}

case object Right extends Direction{
  def opposite: Direction = Left
}

case object Down extends Direction{
  def opposite: Direction = Up
}

case object Left extends Direction{
  def opposite: Direction = Right
}