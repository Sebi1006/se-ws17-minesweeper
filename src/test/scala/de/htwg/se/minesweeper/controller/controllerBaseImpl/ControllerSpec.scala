package de.htwg.se.minesweeper.controller.controllerBaseImpl

import de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl.Grid
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

import scala.language.reflectiveCalls

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {

  "A Controller" when {
    "empty" should {
      var g = new Grid()
      val controller = new Controller(g)
      "handle undo/redo of solving a grid correctly" in {
        controller.getAll(1, 1) should be((false, false, 0, 'w', 10, 10, null, false))
        controller.grid.cell(0, 0).getChecked() should be(false)
        controller.solve
        controller.grid.cell(0, 0).getChecked() should be(true)
        controller.undo
        controller.grid.cell(0, 0).getChecked() should be(false)
        controller.redo
        controller.grid.cell(0, 0).getChecked() should be(false)
      }
      "create a grid" in {
        controller.createGrid(15, 15, 15)
        controller.grid.getHeight() should be(15)
        controller.grid.getWidth() should be(15)
        controller.grid.getNumMines() should be(15)
      }
    }
    "without mines" should {
      var g = new Grid()
      g.init(10, 10, 0)
      val controller = new Controller(g)
      controller.setChecked(0, 0, false, false, false)
      "solve with depthFirstSearch everything" in {
        controller.depthFirstSearch(0, 0)
        controller.grid.cell(9, 9).getChecked() should be(true)
      }
      "after save and load" in {
        controller.save
        controller.createGrid(10, 10, 10)
        controller.load
        controller.grid.cell(9, 9).getChecked() should be(true)
      }
    }
  }
}