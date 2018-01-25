package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.model.gridComponent.GridInterface
import java.awt.Color
import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  var grid: GridInterface
  var noMineCount: Int
  var mineFound: Int
  var flag: Boolean

  def createGrid(height: Int, width: Int, numMines: Int): Unit

  def setChecked(row: Int, col: Int, undo: Boolean, command: Boolean): Unit

  def getChecked(row: Int, col: Int): Boolean

  def getMine(row: Int, col: Int): Boolean

  def getValue(row: Int, col: Int): Int

  def setColor(row: Int, col: Int, color: Int): Unit

  def getColor(row: Int, col: Int): Int

  def height(): Int

  def width(): Int

  def setColorBack(row: Int, col: Int, color: Color): Unit

  def getColorBack(row: Int, col: Int): Color

  def setFlag(row: Int, col: Int, undo: Boolean): Unit

  def getFlag(row: Int, col: Int): Boolean

  def depthFirstSearch(rowD: Int, colD: Int): Unit

  def winner(row: Int, col: Int, undo: Boolean): Unit

  def undo(): Unit

  def redo(): Unit

  def solve(): Unit

  def save(): Unit

  def load(): Unit

}

trait ControllerFactory {
  def create(grid: GridInterface): ControllerInterface
}

trait ControllerIoInterface  {}

import scala.swing.event.Event

case class CellChanged() extends Event
case class GridSizeChanged(height: Int, width: Int, mineNumber: Int) extends Event
case class Winner(win: Boolean) extends Event
