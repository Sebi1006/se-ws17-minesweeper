package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import java.awt.Color

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Sudoku. A Grid" when {
    "to be constructed" should {
      "be created with the length of its edges as size. Practically relevant are size 1, 4, and 9" in {
        val tinygrid = new Grid(10, 10, 10)
        val smallGrid = new Grid(12, 12, 12)
        val normalGrid = new Grid(16, 16, 16)
        val awkwardGrid = new Grid(10, 14, 22)
      }
    }
    "created properly but empty" should {
      val tinygrid = new Grid(10, 10, 10)
      val smallGrid = new Grid(12, 12, 12)
      val normalGrid = new Grid(16, 16, 16)
      val awkwardGrid = new Grid(10, 14, 22)
      "give access to its Cells" in {
        tinygrid.cell(0, 0) should be(new Cell(false, 0, 'w', null, false))
        smallGrid.cell(0, 0) should be(new Cell(false, 0, 'w', null, false))
        smallGrid.cell(0, 1) should be(new Cell(false, 0, 'w', null, false))
        smallGrid.cell(1, 0) should be(new Cell(false, 0, 'w', null, false))
        smallGrid.cell(1, 1) should be(new Cell(false, 0, 'w', null, false))
      }
      "allow to set individual Cells and remain immutable" in {
        smallGrid.cell(0, 0).setChecked(true)
        smallGrid.cell(0, 0) should be(new Cell(true, smallGrid.cell(0, 1).getValue(), 'w', null, false))
      }
    }
    "prefilled with values 1 to n" should {
      val tinyGrid = Grid(2, 2, 2)
      "have the right values in the right places" in {
        tinyGrid.cell(0, 0).setChecked(true)
        tinyGrid.cell(0, 0) should be(new Cell(true, 0, 'w', null, false))
        tinyGrid.cell(0, 1) should be(new Cell(false, 0, 'w', null, false))
        tinyGrid.cell(1, 0) should be(new Cell(false, 0, 'w', null, false))
        tinyGrid.cell(1, 1) should be(new Cell(false, 0, 'w', null, false))
      }
    }
  }

}
