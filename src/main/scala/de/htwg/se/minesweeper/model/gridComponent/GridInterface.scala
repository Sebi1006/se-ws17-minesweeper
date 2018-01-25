package de.htwg.se.minesweeper.model.gridComponent

import java.awt.Color

trait GridInterface {

  def cell(row: Int, col: Int): CellInterface
  def setMines(rowUsed: Int, colUsed: Int): Unit
  def setValues(): Unit
  def getRow(i: Int): Int
  def getCol(i: Int): Int
  def getHeight(): Int
  def getWidth(): Int
  def getNumMines(): Int
  def solve(): Unit

}

trait CellInterface {

  var checked: Boolean
  var value: Int
  var color: Int
  var colorBack: Color
  var flag: Boolean

  def setValue(value: Int): Unit
  def getValue(): Int
  def setChecked(checked: Boolean): Unit
  def getChecked(): Boolean
  def setColor(color: Int): Unit
  def getColor(): Int
  def setColorBack(color: Color): Unit
  def getColorBack(): Color
  def setFlag(): Unit
  def getFlag(): Boolean

}
