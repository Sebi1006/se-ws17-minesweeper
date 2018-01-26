package de.htwg.se.minesweeper.model.gridComponent
/**
  *
  * Two scala Interface for minesweeper which define
  * the grid and each individual Cell
  * @author da721wol
  * @since 01-25-2018
  */

import java.awt.Color

/**
  *
  * Grid Interface to define the grid of a minesweeper board
  */
trait GridInterface {

  /**initialization of the grid with different values than
    * the default parameters
    * @param  height  the height of the grid
    * @param width the width of the grid
    * @param numMines the number of Mines to place in the grid
    * @return does not return anything
    */
  def init(height: Int, width: Int, numMines: Int): Unit

  /**Returns a specified cell of the given Grid
    * @param  row  the row of the cell
    * @param col the colum of the cell
    * @return the Cell specified by the given parameters
    */
  def cell(row: Int, col: Int): CellInterface

  /**Initialization of the grid with a pregiven number of mines.
    * Never places a mine on the cell of the first click
    * Doesnt run until the first click was done
    * @param rowUsed  the row of the cell which was first clicked on
    * @param colUsed the column of the cell which was first clicked on
    * @return does not return anything
    */
  def setMines(rowUsed: Int, colUsed: Int): Unit

  /**Calculates the values of each cell of the grid, depending
    * on the number of neighbouring mines, cells with mines
    * have a value of -1.
    * @return does not return anything
    */
  def setValues(): Unit

  /**Returns the given index of the Array(-1, -1, -1, 0, 1, 1, 1, 0).
    * Is used to make it easier to check for every neighbour.
    * @param i  index of the Array
    * @return returns the value behind the index
    */
  def getRow(i: Int): Int

  /**Returns the given index of the Array(-1, -1, -1, 0, 1, 1, 1, 0).
    * Is used to make it easier to check for every neighbour.
    * @param i  index of the Array
    * @return returns the value behind the index
    */
  def getCol(i: Int): Int

  /**Returns the height of the grid
    * @return returns the height
    */
  def getHeight(): Int

  /**Returns the width of the grid
    * @return returns the width
    */
  def getWidth(): Int

  /**Returns the number of mines in the grid
    * @return returns the number of mines
    */
  def getNumMines(): Int

  /**Solves the grid by opening setting every Cell on chcked
    *
    * @return returns a List of row- and col-index for every
    *         Cell that was checked that way
    */
  def solve(): List[(Int, Int)]
}

/**Creates a Grid with default values
  *
  */
trait GridFactory {
  /**Creates a grid with default values
    * @return does not return anything
    */
  def create(): GridInterface
}

/**
  *
  * Cell Interface to define the cells of a minesweeper board
  */
trait CellInterface {

  /**initialization of the cell with different values than
    * the default parameters
    * @param  checked  specify if the cell is checked
    * @param value the value of the cell
    * @param color the color of the cell
    * @param colorBack a background color for the JButtons of the gui
    * @param flag specify if the cell is flaged, can not be flaged when checked
    * @return does not return anything
    */
  def init(checked: Boolean, value: Int, color: Int, colorBack: Color, flag: Boolean): Unit

  /**Returns every value of the cell as a tupel
    *
    * @return The return parameters are the value as Int, if its checked as a Boolean,
    *         its color as an Int, the backgroundColor as a java.awt.Color and
    *         if its flaged as a Boolean
    */
  def getAll(): (Int, Boolean, Int, Color, Boolean)

  /** Sets the value of the Cell
    *
    * @param value the value to set
    */
  def setValue(value: Int): Unit

  /**
    * Returns the value of the Cell
    * @return the value
    */
  def getValue(): Int

  /**
    * Sets the checked-status of the Cell
    * @param checked the status to set
    */
  def setChecked(checked: Boolean): Unit

  /**
    * Returns the checked-status of the cell
    * @return returns the checked-status
    */
  def getChecked(): Boolean

  /**
    * Sets the color of the cell
    * @param color the color to set
    */
  def setColor(color: Int): Unit

  /**
    * Returns the color of the cell
    * @return returns the color
    */
  def getColor(): Int

  /**
    * Sets the background color of the Cell
    * @param color the color to set
    */
  def setColorBack(color: Color): Unit

  /**
    * Returns the background color of the cell
    * @return the background color
    */
  def getColorBack(): Color

  /**
    * Sets the flag-status of the cell
    * @param undo specify if it should unset it instead
    */
  def setFlag(undo: Boolean): Unit

  /**
    * Returns the flag-status of theCell
    * @return the flag-status
    */
  def getFlag(): Boolean

}

/**
  * Creates a Cell with default parameters
  */
trait CellFactory {
  /**
    * Creates a Cell with default parameters
    * @return the created Cell
    */
  def create(): CellInterface
}