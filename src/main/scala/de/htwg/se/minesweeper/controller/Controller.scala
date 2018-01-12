package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.controller.GameStatus._
import de.htwg.se.minesweeper.model.{ Grid, GridCreator, GridShow }
import scala.swing.Publisher

class Controller(var grid: Grid) extends Publisher {

  var gameStatus: GameStatus = IDLE
  def createRandomGrid(length: Int, width: Int, mineNumber: Int): Unit = {
    grid = new GridCreator(length, width).createRandom(mineNumber)
    publish(new CellChanged)
  }

  def setChecked(row: Int, col: Int): Unit = {
    var gridShow = new GridShow(grid)
    gridShow.setChecked(row, col)
  }

  def gridToString: String = grid.toString
  def gridSize: Int = grid.size
  def blockSize: Int = Math.sqrt(grid.size).toInt
  def statusText: String = GameStatus.message(gameStatus)
}