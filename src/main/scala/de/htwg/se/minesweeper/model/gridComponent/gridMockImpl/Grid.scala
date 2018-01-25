package de.htwg.se.minesweeper.model.gridMockImpl

import de.htwg.se.minesweeper.model.gridComponent.{CellInterface, GridInterface}
import java.awt.Color

class Grid(var height: Int, var width: Int, var numMines: Int) extends GridInterface {

  height = 10
  width = 10
  numMines = 10

  def cell(row: Int, col: Int): CellInterface = EmptyCell
  def setMines(rowUsed: Int, colUsed: Int): Unit = {}
  def setValues(): Unit = {}
  def getRow(i: Int): Int = 0
  def getCol(i: Int): Int = 0
  def getHeight(): Int = 0
  def getWidth(): Int = 0
  def getNumMines(): Int = 0
  def solve(): Unit = {}

}

object EmptyCell extends CellInterface {

  def setValue(value: Int): Unit = {}
  def getValue(): Int = 0
  def setChecked(checked: Boolean): Unit = {}
  def getChecked(): Boolean = false
  def setColor(color: Int): Unit = {}
  def getColor(): Int = 0
  def setColorBack(color: Color): Unit = {}
  def getColorBack(): Color = null
  def setFlag(flag: Boolean): Unit = {}
  def getFlag(): Boolean = false

}
