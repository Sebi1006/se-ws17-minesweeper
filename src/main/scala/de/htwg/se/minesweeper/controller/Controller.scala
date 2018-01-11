package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.model.{ Grid, GridCreator }
import de.htwg.se.minesweeper.util.Observable

class Controller(var grid: Grid) extends Observable {

  def createRandomGrid(length: Int, width: Int, mineNumber: Int): Unit = {
    grid = new GridCreator(length, width).createRandom(mineNumber)
    notifyObservers
  }

  def gridToString: String = grid.toString

  def set(row: Int, col: Int): Unit = {
    grid.set(row, col, 5)
    notifyObservers
  }

}