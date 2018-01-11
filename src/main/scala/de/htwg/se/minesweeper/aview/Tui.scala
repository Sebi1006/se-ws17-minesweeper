package de.htwg.se.minesweeper.aview

import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.model.{ Grid, GridCreator }
import de.htwg.se.minesweeper.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)
  val length = 8
  val width = 8
  val mineNumber = 8
  val randomCells: Int = length * width / 4

  def processInputLine(input: String): Unit = {
    input match {
      case "r" => controller.createRandomGrid(length, width, mineNumber)
      case _ =>
    }

  }
  override def update: Unit = println(controller.gridToString)

}
