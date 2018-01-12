package de.htwg.se.minesweeper.controller

import de.htwg.se.minesweeper.controller.GameStatus.{ NOT_SOLVABLE, SOLVED }
import de.htwg.se.minesweeper.model.{ Grid }
import de.htwg.se.minesweeper.util.Command

class SolveCommand(controller: Controller) extends Command {
  var memento: Grid = controller.grid
  override def doStep: Unit = {
    memento = controller.grid
    //val (success, newgrid) = new Solver(controller.grid).solve
    //if (success) controller.gameStatus = SOLVED else controller.gameStatus = NOT_SOLVABLE
    //controller.grid = newgrid
  }

  override def undoStep: Unit = {
    val new_memento = controller.grid
    controller.grid = memento
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = controller.grid
    controller.grid = memento
    memento = new_memento
  }

}
