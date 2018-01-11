package de.htwg.se.minesweeper.model

case class Grid(cells: Matrix) {
    def this(length: Int, width: Int) = this(new Matrix(length, width, Cell(false, 0)))

    def cell(row: Int, col: Int): Cell = cells.cell(row, col)

    def set(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, 0))


}
