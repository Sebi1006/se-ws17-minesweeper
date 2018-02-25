package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.model.gridComponent.GridInterface
import java.awt.Color
import scala.swing.Publisher

/**
  * ControllerInterface to define the controller of a minesweeper game
  * Publishes results of a certain action, to repaint the GUI and the TUI
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
    * Sets a cell status of a specified cell of the grid
    * @param row row of the cell
    * @param col column of the cell
    * @param undo specifies if it should unset it instead
    * @param command specifies if the call comes from a command, to not trigger an endless loop
    * @param dpfs specifies if the call comes from depthFirstSearch, to trigger less publishes
    */
  def setChecked(row: Int, col: Int, undo: Boolean, command: Boolean, dpfs: Boolean): Unit

  /**
    * Returns the cell status of a specified cell of the grid
    * @param row row of the cell
    * @param col column of the cell
    * @return the cell status
    */
  def getChecked(row: Int, col: Int): Boolean

  /**
    * Returns if a specified cell is a mine or not
    * @param row row of the cell
    * @param col column of the cell
    * @return if the cell is a mine
    */
  def getMine(row: Int, col: Int): Boolean

  /**
    * Returns the value of a specified cell
    * @param row row of the cell
    * @param col column of the cell
    * @return the value of the cell
    */
  def getValue(row: Int, col: Int): Int

  /**
    * Sets the color of a specified cell
    * @param row row of the cell
    * @param col column of the cell
    * @param color the color to set
    */
  def setColor(row: Int, col: Int, color: Int): Unit

  /**
    * Returns the color of a specified cell
    * @param row row of the cell
    * @param col column of the cell
    * @return the color
    */
  def getColor(row: Int, col: Int): Int

  /**
    * Height of the grid
    * @return the height of the grid
    */
  def height(): Int

  /**
    * Width of the grid
    * @return the width of the grid
    */
  def width(): Int

  /**
    * Sets the background color of a specified cell
    * @param row row of the cell
    * @param col column of the cell
    * @param color the color to set the background color
    */
  def setColorBack(row: Int, col: Int, color: Color): Unit

  /**
    * Returns the background color of a specified cell
    * @param row row of the cell
    * @param col column of the cell
    * @return the background color of a specified cell
    */
  def getColorBack(row: Int, col: Int): Color

  /**
    * Sets the flag status of a specified cell
    * @param row row of the cell
    * @param col column of the cell
    */
  def setFlag(row: Int, col: Int, undo: Boolean, command: Boolean): Unit

  /**
    * Returns the flag status of a specified cell
    * @param row row of the cell
    * @param col column of the cell
    * @return the flag status
    */
  def getFlag(row: Int, col: Int): Boolean

  /**
    * DepthFirstSearch is called whenever a cell with a value of 0 is checked
    * It checks every cell around that cell and does the same for any of the newly
    * opened cells if they have a value of 0
    * @param rowD row of the cell with value 0
    * @param colD column of the cell with value 0
    */
  def depthFirstSearch(rowD: Int, colD: Int): Unit

  /**
    * Defines if a game is won by comparing checked cells to the maximum
    * number of possible checked cells with no mine
    * Defines if a game is lost when a cell with a mine is checked
    * @param row row of the cell that was checked
    * @param col column of the cell that was checked
    * @param undo defines if the cell was checked or unchecked
    */
  def winner(row: Int, col: Int, undo: Boolean): Unit

  /**
    * Undo the last move
    */
  def undo(): Unit

  /**
    * Redo the last move
    */
  def redo(): Unit

  /**
    * Solves the grid of the controller
    */
  def solve(): Unit

  /**
    * Saves the grid of the controller with a FileIO(see FileIOInterface)
    */
  def save(): Unit

  /**
    * Loads a new grid for the controller with a FileIO(see FileIOInterface)
    */
  def load(): Unit

}

/**
  * An interface which creates a controller with a specified grid
  */
trait ControllerFactory {
  /**
    * Creates a controller with a specified grid
    */
  def create(grid: GridInterface): ControllerInterface
}

import scala.swing.event.Event

/**
  * Publishes whenever a cell changes its checked or flagged status
  */
case class CellChanged() extends Event

/**
  * Publishes whenever a new grid is created
  * @param height height of the grid
  * @param width width of the grid
  * @param mineNumber number of mines in the grid
  */
case class GridSizeChanged(height: Int, width: Int, mineNumber: Int) extends Event

/**
  * Publishes whenever the game is lost or won
  * @param win defines if the game was lost or won
  */
case class Winner(win: Boolean) extends Event
