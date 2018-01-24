package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.model.Grid
import java.awt._
import scala.swing.{Publisher, event}

class Controller(var grid: Grid) extends Publisher {
  publish(new GridSizeChanged(grid.height, grid.width, grid.numMines))
  var noMineCount: Int = grid.height * grid.width - grid.numMines
  var flag: Boolean = true

  def createGrid(height: Int, width: Int, numMines: Int): Unit = {
    grid = new Grid(height, width, numMines)
    noMineCount = height * width - numMines
    flag = true
    publish(new GridSizeChanged(height, width, numMines))
  }

  def setChecked(row: Int, col: Int): Unit = {
    if (grid.cell(row, col).getChecked) {
      return
    }
    grid.cell(row, col).setChecked(true)
    if (flag) {
      grid.setMines(row, col)
      grid.setValues()
      flag = false
    }
    winner(row, col)
    publish(new CellChanged(row, col, false))
  }

  def getChecked(row: Int, col: Int): Boolean = {
    grid.cell(row, col).getChecked()
  }

  def getMine(row: Int, col: Int): Boolean = {
    if (grid.cell(row, col).getValue() == -1) {
      return true
    }
    false
  }

  def getValue(row: Int, col: Int): Int = {
    grid.cell(row, col).getValue()
  }

  def setColor(row: Int, col: Int, color: Int): Unit = {
    grid.cell(row, col).setColor(color)
  }

  def getColor(row: Int, col: Int): Int = {
    grid.cell(row, col).getColor()
  }

  def height(): Int = {
    grid.height
  }

  def width(): Int = {
    grid.width
  }

  def setColorBack(row: Int, col: Int, color: Color): Unit = {
    grid.cell(row, col).setColorBack(color)
  }

  def getColorBack(row: Int, col: Int): Color = {
    grid.cell(row, col).getColorBack()
  }

  def setFlag(row: Int, col: Int): Unit = {
    grid.cell(row, col).setFlag()
    publish(new CellChanged(row, col, true))
  }

  def getFlag(row: Int, col: Int): Boolean = {
    grid.cell(row, col).getFlag()
  }

  def depthFirstSearch(rowD: Int, colD: Int): Unit = {
    var R: Int = 0
    var C: Int = 0
    setColor(rowD, colD, 'b')
    setColorBack(rowD, colD, Color.LIGHT_GRAY)
    setChecked(rowD, colD)
    for (i <- 0.until(8)) {
      R = rowD + grid.row(i)
      C = colD + grid.col(i)
      if (R >= 0 && R < height && C >= 0 && C < width &&
        getColor(R, C) == 'w') {
        if (getValue(R, C) == 0) {
          depthFirstSearch(R, C)
        } else {
          setChecked(R, C)
          // blocks(R)(C).setIcon(ic(getValue(R, C)))
          setColor(R, C, 'b')
        }
      }
    }
  }

  def winner(row: Int, col: Int): Unit = {
    if(grid.cell(row, col).getValue() != -1) {
      noMineCount -= 1
    } else {
      publish(new Winner(false))
    }
    if(noMineCount == 0) {
      publish(new Winner(true))
    }
  }

}
