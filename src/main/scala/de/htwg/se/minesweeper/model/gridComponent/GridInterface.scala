package de.htwg.se.minesweeper.model.gridComponent

import java.awt.Color

trait GridInterface {

  def init(height: Int, width: Int, numMines: Int): Unit
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

trait GridFactory {
  def create(): GridInterface
}

trait CellInterface {

  def init(checked: Boolean, value: Int, color: Int, colorBack: Color, flag: Boolean): Unit
  def getAll(): (Int, Boolean, Int, Color, Boolean)
  def setValue(value: Int): Unit
  def getValue(): Int
  def setChecked(checked: Boolean): Unit
  def getChecked(): Boolean
  def setColor(color: Int): Unit
  def getColor(): Int
  def setColorBack(color: Color): Unit
  def getColorBack(): Color
  def setFlag(undo: Boolean): Unit
  def getFlag(): Boolean

}

trait CellFactory {
  def create(): CellInterface
}