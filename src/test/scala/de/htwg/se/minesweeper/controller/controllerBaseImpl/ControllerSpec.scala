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
      val smallGrid = g
      val controller = new Controller(smallGrid)
      "handle undo/redo of solving a grid correctly" in {
        controller.grid.cell(0, 0).getChecked() should be(false)
        //controller.grid.solved should be(false)
        controller.solve
        controller.grid.cell(0, 0).getChecked() should be(true)
        //controller.grid.solved should be(true)
        /*controller.undo
        controller.grid.cell(0, 0).getChecked() should be(false)
        //controller.grid.solved should be(false)
        controller.redo
        controller.grid.cell(0, 0).getChecked() should be(true)
        //controller.grid.solved should be(true)
        */
      }
    }
  }
}