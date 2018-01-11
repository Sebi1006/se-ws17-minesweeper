package de.htwg.se.minesweeper.model

import scala.util.Random

case class Matrix(rows: Vector[Vector[Cell]]) {
  def this(length: Int, width: Int, fill: Cell) = this(Vector.tabulate(length, width) { (row, col) => fill })

  val size: Int = rows.size

  def cell(row: Int, col: Int): Cell = rows(row)(col)

  def replaceCell(row: Int, col: Int, cell: Cell): Matrix =
    copy(rows.updated(row, rows(row).updated(col, cell)))

}

