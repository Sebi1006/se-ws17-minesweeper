package de.htwg.se.minesweeper.model.fileIoComponent

import de.htwg.se.minesweeper.model.gridComponent.GridInterface

trait FileIOInterface {

  def load: GridInterface
  def save(grid: GridInterface): Unit

}
