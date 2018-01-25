/*package de.htwg.se.minesweeper.model.gridComponent.gridAdvancedImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.minesweeper.model.gridComponent.GridInterface
import de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl.{Solver, Grid => BaseGrid}

class Grid @Inject() ( @Named("DefaultSize") height: Int, width: Int, numMines: Int) extends BaseGrid(height, width, numMines) {

  def createNewGrid: GridInterface = new BaseGrid(height, width, numMines)

}
*/