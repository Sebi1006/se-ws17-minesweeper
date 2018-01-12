package de.htwg.se.minesweeper.aview

import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.model.{ Grid, GridCreator }
import de.htwg.se.minesweeper.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)
  val length = 8
  val width = 8
  val mineNumber = 8

  def processInputLine(input: String): Unit = {
    input match {
      case "r" => controller.createRandomGrid(length, width, mineNumber)
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
        case row :: col :: Nil => {
          controller.setChecked(row - 1, col - 1)
        }
        case _ =>
      }
    }

  }
  override def update: Unit = println(controller.gridToString)

}
