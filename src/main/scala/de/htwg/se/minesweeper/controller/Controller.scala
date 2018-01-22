package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.model.Grid

class Controller(var grid: Grid) {
  var flag: Boolean = true
  def createGrid(length: Int, width: Int, numMines: Int): Unit = {
    grid = new Grid(length, width, numMines)
    println(grid.toString)
  }

  def setChecked(row: Int, col: Int): Boolean = {
    if (grid.cell(row, col).getChecked) {
      return false
    }
    grid.cell(row, col).setChecked(true)
    if(flag) {
      grid.setMines(row, col)
      grid.setValues()
      flag = false
    }
    println(grid.toString)
    true
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

  def getColor(row: Int, col: Int): Int = {
    grid.cell(row, col).getColor()
  }

  def setColor(row: Int, col: Int, color: Int): Unit = {
    grid.cell(row, col).setColor(color)
  }

  def height(): Int = {
    grid.height
  }

  def width(): Int = {
    grid.width
  }
}
