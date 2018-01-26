package de.htwg.se.minesweeper.model.fileIoComponent

import de.htwg.se.minesweeper.model.gridComponent.GridInterface

trait FileIOInterface {

  def load: (Int, Int, Int, List[Int], List[Boolean], List[Boolean], List[Int])
  def save(grid: GridInterface): Unit

}
