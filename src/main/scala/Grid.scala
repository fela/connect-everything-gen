import scala.util.Random.nextInt

class Grid(width: Int, height: Int, wrapping: Boolean) { grid =>

  val nCells = width*height
  var cells = Array[Cell]()
  generateRandomGrid()

  class Cell(dirs: Directions, i: Int) {
    var directions = dirs
    val index = i

    def row = index / width
    def col = index % width

    def neighbor(dir: Direction): Option[Cell] = dir match {
      case Up => grid.cellAt(row-1, col)
      case Right => grid.cellAt(row, col+1)
      case Down => grid.cellAt(row+1, col)
      case Left => grid.cellAt(row, col-1)
    }

    def isEmpty = directions == Directions(false, false, false, false)

    override def toString = directions.toString
  }

  def cellAt(row: Int, col: Int) : Option[Cell] = {
    if (row >= height || row < 0 || col >= width || col < 0)
      if (wrapping) {
        val rowMod = row % height
        val colMod = col % width
        Some(cells(rowMod * width + colMod))
      }
      else
        None
    else
      Some(cells(row * width + col))
  }

  private def generateRandomGrid() = {
    // generate empty grid
    cells = (0 until nCells).map { i =>
      new Cell(Directions(false, false, false, false), i)
    }.toArray

    val initialPosition = nextInt(nCells)
    // cells that have been started to which something can still be added
    var frontierCells = IndexedSeq(cells(initialPosition))
    while (!frontierCells.isEmpty) {
      val cell = frontierCells(nextInt(frontierCells.length))
      addRandomCable(cell)
      frontierCells = updatedFrontierCells
    }
  }

  private def addRandomCable(cell: Cell) {
    def canAdd(dir: Direction) = {
      val neighbor = cell.neighbor(dir)
      neighbor match {
        case Some(c) => c.isEmpty
        case None => false
      }
    }
    val dirs = Direction.allDirections.filter(canAdd)
    val dir = dirs(nextInt(dirs.length))
    cell.directions = cell.directions.add(dir)
    val neighbor = cell.neighbor(dir).get
    neighbor.directions = neighbor.directions.add(dir.opposite)
  }

  def updatedFrontierCells: IndexedSeq[Cell] = {
    def hasEmptyNeighbor(cell: Cell) =
      Direction.allDirections.map(dir => cell.neighbor(dir))
      .exists(cellOpt => cellOpt.map(_.isEmpty).getOrElse(false))
    cells.filter(!_.isEmpty)
         .filter(hasEmptyNeighbor(_))
  }

  /*
  def updatedCanAdd: IndexedSeq =
    (for (
      c <- cells;
      dir <- Direction.allDirections
      if canAddCable(c, dir)
    ) yield c).unique

  def canAddCable(cell: Cell, dir: Direction): Boolean = {
    ???
  }

  def neighbor(cell: Int, dir: Direction) : Option[Directions] = {
    val row = cell / width
    val col = cell % width
  }

  def cellAt(row: Int, col: Int) {

  }         */

  def addRandomCable: Int = ???

  override def toString =
    cells.grouped(width).toList.map(_ mkString "") mkString "\n"


}
