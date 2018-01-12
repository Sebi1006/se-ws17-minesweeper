package de.htwg.se.minesweeper.model

import scala.math.sqrt
import scala.util.Random

case class Grid(cells: Matrix) {
  def this(length: Int, width: Int) = this(new Matrix(length, width))
  val length = 8
  val width = 8
  val size: Int = cells.size
  val blocknum: Int = sqrt(size).toInt

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  def set(row: Int, col: Int, value: Int): Grid = copy(cells.replaceCell(row, col, Cell(false, value)))

  override def toString: String = {
    val lineseparator = ("+-" + ("--" * 8)) + "+\n"
    val line = ("| " + ("y " * 8)) + "|\n"
    var box = "\n" + (lineseparator + (line * 8)) + lineseparator
    var turn = 0
    for {
      row <- 0 until length
      col <- 0 until width
    } if (!cell(row, col).checked) {
      box = box.replaceFirst("y", "x")
    } else {
      if (cell(row, col).getValue() == -1) {
        box = box.replaceFirst("y", cell(row, col).getValue().toString)
      } else {
        box = box.replaceFirst("y", (cell(row, col).getValue().toString + " "))
      }
    }
    box
  }

}
