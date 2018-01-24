package de.htwg.se.minesweeper.controller.controllerBaseImpl

import de.htwg.se.minesweeper.util.Command

class SetCommand(row: Int, col: Int, undo: Boolean, controller: Controller) extends Command {
  override def doStep: Unit = controller.setChecked(row, col, undo, false)

  override def undoStep: Unit = controller.setChecked(row, col, true, false)

  override def redoStep: Unit = controller.setChecked(row, col, undo, false)
}
