package de.htwg.se.minesweeper.aview

import de.htwg.se.minesweeper.controller.controllerBaseImpl.Controller
import de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl.Grid
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TuiSpec  extends WordSpec with Matchers{

  "A Sudoku Tui" should {
    var g = new Grid()
    g.init(10,10,10)
    val controller = new Controller(g)
    val tui = new Tui(controller)
    "create an empty minesweeper on input '1'" in {
      tui.processInputLine("1")
      g.init(10,10,10)
      controller.grid should be(g)
    }
    "create an empty minesweeper on input 'new1'" in {
      tui.processInputLine("new")
      g.init(10,10,10)
      controller.grid should be(g)
    }
    "create an empty minesweeper on input '2'" in {
      tui.processInputLine("2")
      g.init(16,16,70)
      controller.grid should be(g)
    }
    "create an empty minesweeper on input 'new2'" in {
      tui.processInputLine("new")
      g.init(16,16,70)
      controller.grid should be(g)
    }
    "create an empty minesweeper on input '3'" in {
      tui.processInputLine("3")
      g.init(20,20,150)
      controller.grid should be(g)
    }
    "create an empty minesweeper on input 'new3'" in {
      tui.processInputLine("new")
      g.init(20,20,150)
      controller.grid should be(g)
    }
    "set a cell on input '2 2'" in {
      val input = "2 2"
      tui.processInputLine(input)
      controller.grid.cell(1,1).getChecked() should be(true)
    }
    "set flag on input 'f 2 3'" in {
      val input = "f 2 3"
      tui.processInputLine(input)
      val inputFalse = "4 2 3"
      tui.processInputLine(inputFalse)
      controller.grid.cell(1,2).getFlag() should be(true)
    }
    "solve a MineSweeper on input 's'" in {
      tui.processInputLine("s")
      controller.getChecked(9, 9) should be(true)
    }
    "save and load a MineSweeper on input 'save" in {
      tui.processInputLine("1")
      val input = "2 2"
      tui.processInputLine(input)
      tui.processInputLine("save")
      tui.processInputLine("1")
      tui.processInputLine("load")
      controller.getChecked(1, 1) should be(true)
      controller.flag should be(false)

    }
    "create an empty minesweeper on input '4'" in {
      val input: String = "4\n20 20 20"
      tui.processInputLine(input)
      g.init(20,20,20)
      controller.grid should be(g)
    }

    "create an empty minesweeper on input 'new4'" in {
      tui.processInputLine("new")
      g.init(20,20,20)
      controller.grid should be(g)
    }
  }

}

