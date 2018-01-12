package de.htwg.se.minesweeper.model

class GridShow(grid: Grid) {
  def setChecked(row: Int, col: Int): Unit = {
    grid.cell(row, col).setChecked(true)
  }
}
