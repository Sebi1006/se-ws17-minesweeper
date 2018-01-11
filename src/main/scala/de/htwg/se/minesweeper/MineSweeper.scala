package de.htwg.se.minesweeper

import de.htwg.se.minesweeper.model.{ Grid, GridCreator, Player }
import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.aview.Tui

import scala.io.StdIn.readLine

object MineSweeper {
  val controller = new Controller(new Grid(8, 8))
  controller.createRandomGrid(8, 8, 1)
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
