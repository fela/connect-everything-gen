package connecteverything

object Directions {
  val StringRepresentations = collection.immutable.HashMap(
    Directions(false, false, false, false) -> " ",
    Directions(true,  false, false, false) -> "╵",
    Directions(false, true,  false, false) -> "╶",
    Directions(false, false, true,  false) -> "╷",
    Directions(false, false, false, true ) -> "╴",
    Directions(true,  true,  false, false) -> "└",
    Directions(false, true,  true,  false) -> "┌",
    Directions(false, false, true,  true ) -> "┐",
    Directions(true,  false, false, true ) -> "┘",
    Directions(true,  false, true,  false) -> "│",
    Directions(false, true,  false, true ) -> "─",
    Directions(true,  true,  true,  false) -> "├",
    Directions(false, true,  true,  true ) -> "┬",
    Directions(true,  false, true,  true ) -> "┤",
    Directions(true,  true,  false, true ) -> "┴",
    Directions(true,  true,  true,  true ) -> "┼"
  )
}

case class Directions(up: Boolean, right: Boolean, down: Boolean, left: Boolean) {
  override def toString(): String =
    Directions.StringRepresentations(this)

  def add(dir: Direction) = dir match {
    case Up => Directions(true, right, down, left)
    case Right => Directions(up, true, down, left)
    case Down => Directions(up, right, true, left)
    case Left => Directions(up, right, down, true)
  }
}
