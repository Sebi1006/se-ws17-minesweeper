/**
  *
  * A scala Interface which defines save and load instructions
  * of Minesweeper grids for File IOs
  * @author da721wol
  * @since 01-25-2018
  */
package de.htwg.se.minesweeper.model.fileIoComponent

import de.htwg.se.minesweeper.model.gridComponent.GridInterface

trait FileIOInterface {

  /** Description of the load method, which loads a certain file
    * as a minesweeper grid. If successful the parameters can be used
    * to create a minesweeper-grid.
    * @return The return parameters are, the height, the width, the number of mines,
    * a list which contains a value for each cell, a list which
    * contains a checked-Status for each cell, a list which contains
    * a flag-status for each Cell and a list which contains a color
    * for each cell
    */
  def load: (Int, Int, Int, List[Int], List[Boolean], List[Boolean], List[Int])

  /**Description of the save method to save a certain minesweeper-grid.
    * @param  grid  the grid the user wants to save
    * @return does not return anything
    */
  def save(grid: GridInterface): Unit

}
