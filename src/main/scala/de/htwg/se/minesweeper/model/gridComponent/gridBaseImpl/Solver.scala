package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import java.awt.Color

import de.htwg.se.minesweeper.model.gridComponent.GridInterface

class Solver(grid: GridInterface) {
  for (i <- 0 until grid.getHeight(); j <- 0 until grid.getWidth()) {
    grid.cell(i, j).setChecked(true)
    grid.cell(i, j).setColor('b')
    grid.cell(i, j).setColorBack(Color.LIGHT_GRAY)
  }

}
