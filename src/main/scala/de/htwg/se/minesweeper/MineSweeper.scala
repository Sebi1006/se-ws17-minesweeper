package de.htwg.se.minesweeper

import de.htwg.se.minesweeper.aview.gui.Gui
import de.htwg.se.minesweeper.aview.Tui
import de.htwg.se.minesweeper.controller.controllerBaseImpl.Controller
import de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl.Grid
import scala.io.StdIn.readLine

object MineSweeper {

  println("1")
  val controller = new Controller(new Grid(10, 10, 10))
  val tui = new Tui(controller)
  val gui = new Gui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "exit")
  }

}
