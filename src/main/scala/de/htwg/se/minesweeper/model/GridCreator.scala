package de.htwg.se.minesweeper.model

import scala.util.Random

class GridCreator(length: Int, width: Int) {
  def createRandom(mineNumber: Int): Grid = {
    var grid = new Grid(length, width)
    grid = generateMines(mineNumber, grid)
    grid = neighbours(grid)
    grid
  }

  def generateMines(count: Int, grid: Grid): Grid = {
    val rand: Random = new Random()
    for (i <- 0 until count) {
      val row = rand.nextInt(length)
      val col = rand.nextInt(width)
      grid.cell(row, col).setValue(-1)

    }
    grid
  }

  def neighbours(grid: Grid): Grid = {
    for (rowIndex <- 0 until grid.cells.rows.length) {
      for (colIndex <- 0 until grid.cells.rows(0).length) {
        if (grid.cells.rows(rowIndex)(colIndex).getValue() != -1) {
          var value: Int = 0
          if (colIndex > 0 && rowIndex < length - 1 && grid.cells.rows(rowIndex + 1)(colIndex - 1).getValue() < 0) value += 1
          if (rowIndex < length - 1 && grid.cells.rows(rowIndex + 1)(colIndex).getValue() < 0) value += 1
          if (colIndex < length - 1 && rowIndex < length - 1 && grid.cells.rows(rowIndex + 1)(colIndex + 1).getValue() < 0) value += 1
          if (colIndex > 0 && grid.cells.rows(rowIndex)(colIndex - 1).getValue() < 0) value += 1
          if (colIndex < length - 1 && grid.cells.rows(rowIndex)(colIndex + 1).getValue() < 0) value += 1
          if (colIndex > 0 && rowIndex > 0 && grid.cells.rows(rowIndex - 1)(colIndex - 1).getValue() < 0) value += 1
          if (rowIndex > 0 && grid.cells.rows(rowIndex - 1)(colIndex).getValue() < 0) value += 1
          if (colIndex < length - 1 && rowIndex > 0 && grid.cells.rows(rowIndex - 1)(colIndex + 1).getValue() < 0) value += 1
          grid.cell(rowIndex, colIndex).setValue(value)
        }
      }
    }
    grid

  }
}