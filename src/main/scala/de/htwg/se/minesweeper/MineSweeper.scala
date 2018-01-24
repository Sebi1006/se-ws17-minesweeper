package de.htwg.se.minesweeper

import de.htwg.se.minesweeper.aview.gui.Gui
import de.htwg.se.minesweeper.controller.Controller
import de.htwg.se.minesweeper.model.Grid
import de.htwg.se.minesweeper.aview.Tui

import scala.io.StdIn.readLine

object MineSweeper {

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
