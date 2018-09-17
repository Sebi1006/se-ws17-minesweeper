package de.htwg.se.minesweeper.view

import de.htwg.se.minesweeper.controller.controllerbaseimpl.Controller
import de.htwg.se.minesweeper.model.gridcomponent.gridbaseimpl.Grid

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers {

  "A Minesweeper TUI" should {
    val grid = Grid()
    grid.init(10, 10, 10)
    val controller = new Controller(grid)
    val tui = new Tui(controller)

    "create an empty minesweeper on input '1'" in {
      tui.processInputLine("1")
      grid.init(10, 10, 10)
      controller.grid should be(grid)
    }

    "create an empty minesweeper on input 'new'" in {
      tui.processInputLine("new")
      grid.init(10, 10, 10)
      controller.grid should be(grid)
    }

    "create an empty minesweeper on input '2'" in {
      tui.processInputLine("2")
      grid.init(16, 16, 70)
      controller.grid should be(grid)
    }

    "create an empty minesweeper on input 'new'" in {
      tui.processInputLine("new")
      grid.init(16, 16, 70)
      controller.grid should be(grid)
    }

    "create an empty minesweeper on input '3'" in {
      tui.processInputLine("3")
      grid.init(20, 20, 150)
      controller.grid should be(grid)
    }

    "create an empty minesweeper on input 'new'" in {
      tui.processInputLine("new")
      grid.init(20, 20, 150)
      controller.grid should be(grid)
    }

    "set a cell on input '2 2'" in {
      val input = "2 2"
      tui.processInputLine(input)
      controller.grid.cell(1, 1).getChecked() should be(true)
    }

    "set a flag on input 'f 2 3'" in {
      val input = "f 2 3"
      tui.processInputLine(input)
      val inputFalse = "4 2 3"
      tui.processInputLine(inputFalse)
      controller.grid.cell(1, 2).getFlag() should be(false)
    }

    "solve a minesweeper game on input 's'" in {
      tui.processInputLine("s")
      controller.getChecked(9, 9) should be(true)
    }

    "save and load a minesweeper game on input 'save' and 'load'" in {
      tui.processInputLine("1")
      val input = "2 2"
      tui.processInputLine(input)
      tui.processInputLine("save")
      tui.processInputLine("1")
      tui.processInputLine("load")
      controller.getChecked(1, 1) should be(true)
      controller.flag should be(true)
    }

    "create an empty minesweeper on input '4'" in {
      val input: String = "4\n20 20 20"
      tui.processInputLine(input)
      grid.init(20, 20, 20)
      controller.grid should be(grid)
    }

    "create an empty minesweeper on input 'new'" in {
      tui.processInputLine("new")
      grid.init(20, 20, 20)
      controller.grid should be(grid)
    }
  }

}
