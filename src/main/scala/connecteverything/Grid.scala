package connecteverything

/*class SpanningTree(grid: Grid) {
  val height = grid.height
  val width = grid.width
  type Tree = List[(Int, Int)]

  completeSpanningTree(singleNode)
  def completeSpanningTree(incompleteTree: Tree): Tree =
    if (incompleteTree.length == width * height)
      return incompleteTree
    else
      completeSpanningTree(addRandomNode(incompleteTree))

  def addRandomNode(incompleteTree: Tree): Tree =  {
    def getNeighbors(vertex : Int) : Set[Int] = {
      val (row,col) = (vertex / width, vertex % width)

      Set(
        if(row == 0) (width-1, col) else (row-1,col),
        if(row == width) (0, col) else (row+1, col),
        if(col == 0) (row, height-1) else (row, col-1),
        if(col == height) (row, 0) else (row, col+1)
      ).map( pair => pair._1 * width + pair._2)

    }
    val vertices = incompleteTree.map(_._1).toSet union
                   incompleteTree.map(_._2).toSet

    val candidates = vertices.map(vertex => getNeighbors(vertex)) - vertices

    candidates.takeRandom
  }



}  */

class Grid(val width: Int, val height: Int,val wrapping: Boolean) { grid =>

  val nCells = width*height
  var cells = Vector[Cell]()
  generateRandomGrid()

  class Cell(dirs: Directions, i: Int) {
    var directions = dirs
    val index = i
    var marked = false

    def row = index / width
    def col = index % width


    def neighbor(dir: Direction): Option[Cell] = dir match {
      case Up => grid.cellAt(row-1, col)
      case Right => grid.cellAt(row, col+1)
      case Down => grid.cellAt(row+1, col)
      case Left => grid.cellAt(row, col-1)
    }

    def isEmpty = directions == Directions(false, false, false, false)

    override def toString = {
      val sym = directions.toString
      if (marked)
        Console.RED_B + sym + Console.RESET
      else
        sym
    }

    def possibleMoves: Seq[Int] = {
      directions.distinctMoves.filter(canMove)
    }

    def canMove(move: Int): Boolean = {
      val originalDirections = directions
      directions = directions.movedDirections(move)

      def validConnection(dir: Direction): Boolean = neighbor(dir) match {
        case None =>
          !directions.contains(dir)
        case Some(cell) =>
          !cell.marked ||
            cell.directions.contains(dir.opposite) == directions.contains(dir)
      }

      val res: Boolean = Direction.allDirections.forall { dir =>
        validConnection(dir)
      }
      directions = originalDirections
      return res
    }

  }

  def cellAt(row: Int, col: Int) : Option[Cell] = {
    if (row >= height || row < 0 || col >= width || col < 0)
      if (wrapping) {
        val rowMod = (row + height) % height
        val colMod = (col + width) % width
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
    }.toVector

    // cells that have been started to which something can still be added
    var frontierCells = IndexedSeq(cells.takeRandom)

    while (!frontierCells.isEmpty) {
      val cell = frontierCells.takeRandom
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
    val dir = dirs.takeRandom
    cell.directions = cell.directions.add(dir)
    val neighbor = cell.neighbor(dir).get
    neighbor.directions = neighbor.directions.add(dir.opposite)
  }

  def updatedFrontierCells: IndexedSeq[Cell] = {
    def hasEmptyNeighbor(cell: Cell) =
      Direction.allDirections.map(dir => cell.neighbor(dir))
      .exists(cellOpt => cellOpt.exists(_.isEmpty))
    cells.filter(!_.isEmpty)
         .filter(hasEmptyNeighbor)
  }

  def hasEasySolution = {
    markCells
    println(cells.count(_.marked))
    cells.forall(_.marked)
  }

  def markCells() {
      var newlyMarked = false
      cells.foreach { cell =>
        if (!cell.marked && cell.possibleMoves.length == 1) {
          cell.marked = true
          newlyMarked = true
        }
      }
      if (newlyMarked) markCells()
  }

  def numberOfSolutions: Int = {
    val unmarkedCell = cells.find(c => !c.marked)

    unmarkedCell match {
      case None => 1
      case Some(cell) => {
        var solutions = 0
        cell.possibleMoves.foreach { move =>
          val originalDirections = cell.directions
          cell.directions = cell.directions.movedDirections(move)
          assert(!cell.marked)
          cell.marked = true
          //Thread.sleep(500)
          //println()
          //println(this)
          solutions += numberOfSolutions
          cell.marked = false
          cell.directions = originalDirections
        }
        solutions
      }
    }
  }

  override def toString =
    cells.grouped(width).toList.map(_ mkString "") mkString "\n"


}
