package de.htwg.se.minesweeper.controller.controllerMockImpl

import de.htwg.se.minesweeper.controller.ControllerInterface
import de.htwg.se.minesweeper.model.gridComponent.GridInterface
import de.htwg.se.minesweeper.model.gridMockImpl.Grid
import java.awt.Color

class Controller(var grid: GridInterface) extends ControllerInterface {

  grid = new Grid(10, 10, 10)
  var noMineCount: Int = 90
  var mineFound: Int = 0
  var flag: Boolean = true

  def createGrid(height: Int, width: Int, numMines: Int): Unit = {}
  def setChecked(row: Int, col: Int, undo: Boolean, command: Boolean): Unit = {}
  def getChecked(row: Int, col: Int): Boolean = false
  def getMine(row: Int, col: Int): Boolean = false
  def getValue(row: Int, col: Int): Int = 0
  def setColor(row: Int, col: Int, color: Int): Unit = {}
  def getColor(row: Int, col: Int): Int = 0
  def height(): Int = 10
  def width(): Int = 10
  def setColorBack(row: Int, col: Int, color: Color): Unit = {}
  def getColorBack(row: Int, col: Int): Color = null
  def setFlag(row: Int, col: Int): Unit = {}
  def getFlag(row: Int, col: Int): Boolean = false
  def depthFirstSearch(rowD: Int, colD: Int): Unit = {}
  def winner(row: Int, col: Int, undo: Boolean): Unit = {}
  def undo(): Unit = {}
  def redo(): Unit = {}

}
