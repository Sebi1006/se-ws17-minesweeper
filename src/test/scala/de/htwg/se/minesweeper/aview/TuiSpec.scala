package de.htwg.se.minesweeper.aview

import de.htwg.se.minesweeper.controller.controllerBaseImpl.Controller
import de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl.Grid
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TuiSpec  extends WordSpec with Matchers{

  "A Sudoku Tui" should {
    val controller = new Controller(new Grid(10, 10, 10))
    val tui = new Tui(controller)
    "create an empty minesweeper on input '1'" in {
      tui.processInputLine("1\n")
      controller.grid should be(new Grid(10, 10, 10))
    }
    "create an empty minesweeper on input '2'" in {
      tui.processInputLine("2\n")
      controller.grid should be(new Grid(16, 16, 70))
    }
    "create an empty minesweeper on input '3'" in {
      tui.processInputLine("3\n")
      controller.grid should be(new Grid(20, 20, 150))
    }
    "create an empty minesweeper on input '4'" in {
      tui.processInputLine("4\n")
      var input = "10 20 20\n"
      tui.processInputLine(input)
      controller.grid should be(new Grid(10, 20, 20))
    }
    "set a cell on input '2 2'" in {
      tui.processInputLine("2 2\n")
      controller.grid.cell(2,2).getChecked() should be(true)
    }
    "set flag on input 'f 2 3'" in {
      tui.processInputLine("f 2 3\n")
      controller.grid.cell(2,3).getFlag() should be(true)
    }
    "create a new minesweeper on input 'new'" in {
      tui.processInputLine("new\n")
      controller.grid should be(new Grid(10, 20, 20))
    }
    /*"solve a Sudoku on input 's'" in {
      tui.processInputLine("n")
      tui.processInputLine("s")
      controller.grid.solved should be(true)
    }*/
  }

}

