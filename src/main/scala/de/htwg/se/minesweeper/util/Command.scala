package de.htwg.se.minesweeper.util

trait Command {

  def doStep: Unit
  def undoStep: Unit
  def redoStep: Unit

}
