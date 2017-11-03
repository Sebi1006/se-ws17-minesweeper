import de.htwg.se.minesweeper.model.Player

case class Cell(x: Int, y: Int)

val cell1 = Cell(8, 5)
cell1.x
cell1.y

Player("Ich").name

println("Hallo Welt!")

case class Field(cells: Array[Cell])

val field1 = Field(Array.ofDim[Cell](1))
field1.cells(0) = cell1
field1.cells(0).x
field1.cells(0).y