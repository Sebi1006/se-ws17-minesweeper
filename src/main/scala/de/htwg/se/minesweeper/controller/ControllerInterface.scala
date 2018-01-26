package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.model.gridComponent.GridInterface
import java.awt.Color
import scala.swing.Publisher

/**
  * ControllerInterface to define the controller of a minesweeper game
  * publishes results of certain action, to repaint the gui and the tui
  */
trait ControllerInterface extends Publisher {

  var grid: GridInterface
  var noMineCount: Int
  var mineFound: Int
  var flag: Boolean

  /**
    * Creates a minesweeper grid with the given parameters
    * @param height height of the grid
    * @param width width of the grid
    * @param numMines number of mines in the grid
    */
  def createGrid(height: Int, width: Int, numMines: Int): Unit

  /**
    * Sets a Cell-Status of a specified Cell of the grid
    * @param row row of the cell
    * @param col column of the cell
    * @param undo specify if it should unset it instead
    * @param command specify if the call comes from a Command, to not trigger an endless loop
    * @param dpfs specify if the call comes from depthFirstSearch, to trigger less publishes
    */
  def setChecked(row: Int, col: Int, undo: Boolean, command: Boolean, dpfs: Boolean): Unit

  /**
    * Returns the Cell-Status of a specified Cell of the grid
    * @param row row of the cell
    * @param col column of the cell
    * @return the Cell-Status
    */

  def getChecked(row: Int, col: Int): Boolean

  /**
    * Returns if a specified Cell is a mine or not
    * @param row row of the cell
    * @param col column of the cell
    * @return returns if the cell is a mine
    */
  def getMine(row: Int, col: Int): Boolean

  /**
    * Returns the value of a specified Cell
    * @param row row of the cell
    * @param col column of the cell
    * @return returns the value of the cell
    */
  def getValue(row: Int, col: Int): Int

  /**
    * Sets the color of a specified Cell
    * @param row row of the cell
    * @param col column of the cell
    * @param color the color to set
    */
  def setColor(row: Int, col: Int, color: Int): Unit

  /**
    * Returns the color of a specified Cell
    * @param row row of the cell
    * @param col column of the cell
    * @return returns the color
    */
  def getColor(row: Int, col: Int): Int

  /**
    *
    * @return the hight of the grid
    */
  def height(): Int

  /**
    *
    * @return the width of the grid
    */
  def width(): Int

  /**
    * set the background color of a specified Cell
    * @param row row of the Cell
    * @param col column of the Cell
    * @param color the color to set the background color
    */
  def setColorBack(row: Int, col: Int, color: Color): Unit

  /**
    * Returns the background color of a specified Cell
    * @param row row of the Cell
    * @param col column of the Cell
    * @return the background color of a specified Cell
    */

  def getColorBack(row: Int, col: Int): Color

  /**
    * set the flag-satus of a specified Cell
    * @param row row of the Cell
    * @param col column of the Cell
    * @param flag the status to set the flag-status
    */

  def setFlag(row: Int, col: Int, undo: Boolean, command: Boolean): Unit

  /**
    * Returns the flag-status of a specified Cell
    * @param row row of the cell
    * @param col column of the cell
    * @return the flag-status
    */
  def getFlag(row: Int, col: Int): Boolean

  /**
    * DepthFirstSearch is called whenever a Cell with a value of 0 is checked.
    * It checks every Cell around that Cell, and does the same for any of the newly
    * opened Cells if the have a value of 0
    * @param rowD row of the Cell with value 0
    * @param colD column of the Cell with value 0
    */
  def depthFirstSearch(rowD: Int, colD: Int): Unit

  /**
    * Defines if a game is won by comparing checked Cells to the maximum
    * number of possible checked Cells with no mine.
    * Defines if a game is lost when a Cell with a mine is checked
    * @param row row of the Cell that was checked
    * @param col column of the cell that was checked
    * @param undo defines if the Cell was checked or unchecked
    */
  def winner(row: Int, col: Int, undo: Boolean): Unit

  /**
    * Undos the last move
    */
  def undo(): Unit

  /**
    * Redos the last move
    */
  def redo(): Unit

  /**
    * Solves the grid of the Coonroller
    */
  def solve(): Unit

  /**
    * saves the grid of the controller with a FileIO(see FileIOInterface)
    */
  def save(): Unit

  /**
    * loads a new grid for the controller with a FileIO(see FileIOInterface)
    */
  def load(): Unit

}

/**
  * Creates a Controller with a specified grid
  */
trait ControllerFactory {
  /**
    * Creates a Controller with a specified grid
    */
  def create(grid: GridInterface): ControllerInterface
}

import scala.swing.event.Event

/**
  * Published whenever a Cell Changes its checked or flaged State
  */
case class CellChanged() extends Event

/**
  * Published whenever a new grid is created
  * @param height height of the grid
  * @param width width of the grid
  * @param mineNumber number of mines in the grid
  */
case class GridSizeChanged(height: Int, width: Int, mineNumber: Int) extends Event

/**
  * Published whenever the game is lost or won
  * @param win defines if the game was lost or won
  */
case class Winner(win: Boolean) extends Event
