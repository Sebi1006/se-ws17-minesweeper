package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import java.awt.Color

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Minesweeper. A Grid" when {
    "to be constructed" should {
      "be created with the length and widths of the given parameters and a given number of mines." in {
        var g = new Grid()
        val tinygrid = g
        g.init(12, 12, 12)
        val smallGrid = g
        g.init(16, 16, 16)
        val normalGrid = g
        g.init(10, 14, 22)
        val awkwardGrid = g

        "give access to its Cells" in {
          var c = new Cell()
          c.init(false, 0, 'w', null, false)
          tinygrid.cell(0, 0) should be(c)
          smallGrid.cell(0, 0) should be(c)
          smallGrid.cell(0, 1) should be(c)
          smallGrid.cell(1, 0) should be(c)
          smallGrid.cell(1, 1) should be(c)
        }
        "allow to set individual Cells and remain immutable" in {
          smallGrid.cell(0, 0).setChecked(true)
          var c = new Cell()
          c.init(true, smallGrid.cell(0, 1).getValue(), 'w', null, false)
          smallGrid.cell(0, 0) should be(c)
        }
      }
      "prefilled with values 1 to n" should {
        var g = new Grid()
        g.init(2, 2, 2)
        val tinyGrid = g
        "have the right values in the right places" in {
          tinyGrid.cell(0, 0).setChecked(true)
          var c = new Cell()
          c.init(true, 0, 'w', null, false)
          tinyGrid.cell(0, 0) should be(c)
          c.init(false, 0, 'w', null, false)
          tinyGrid.cell(0, 1) should be(c)
          tinyGrid.cell(1, 0) should be(c)
          tinyGrid.cell(1, 1) should be(c)
        }
      }
    }
  }

}
