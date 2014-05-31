package connecteverything

object DirectionsTest {
  def main(args: Array[String]) {
    assert(Right.opposite == Left)
    assert(Up.movedDirection(0) == Up)
    assert(Up.movedDirection(1) == Right)
    assert(Up.movedDirection(2) == Down)
    assert(Up.movedDirection(3) == Left)
    assert(Right.movedDirection(1) == Down)
    assert(Right.movedDirection(3) == Up)
    assert(Directions(true, false, true, false).movedDirections(1) ==
           Directions(false, true, false, true))

    assert(Directions(true, false, true, false).distinctMoves.length == 2)

    assert(Directions(false, true, false, true).movedDirections(1) ==
           Directions(true, false, true, false))

    assert(Directions(false, true, false, true).distinctMoves.length == 2)

    assert(Directions(true, true, true, true).toSet == Set(Up, Left, Right, Down))
    assert(Directions(false, false, false, false).toSet == Set())

    println("Tests completed")
  }
}
