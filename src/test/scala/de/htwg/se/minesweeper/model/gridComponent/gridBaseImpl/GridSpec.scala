package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {

  "A Grid" when {
    "to be constructed" should {
      "be created with the height and width of the given parameters and a given number of mines" in {
        val g = new Grid()
        val tinyGrid = g
        g.init(12, 12, 12)
        val smallGrid = g
        g.init(16, 16, 16)
        val normalGrid = g
        g.init(10, 14, 22)
      }
      "created properly but empty" should {
        val g = new Grid()
        val tinyGrid = g
        g.init(12, 12, 12)
        val smallGrid = g
        g.init(16, 16, 16)
        val normalGrid = g
        g.init(10, 14, 22)
        "give access to its cells" in {
          val c = new Cell()
          c.init(false, 0, 'w', null, false)
          tinyGrid.cell(0, 0).getAll() should be(c.getAll())
          smallGrid.cell(0, 0).getAll() should be(c.getAll())
          smallGrid.cell(0, 1).getAll() should be(c.getAll())
          smallGrid.cell(1, 0).getAll() should be(c.getAll())
          smallGrid.cell(1, 1).getAll() should be(c.getAll())
          g.getCol(2) should be(1)
          g.getRow(2) should be(-1)
        }
        "allow to set individual cells and remain immutable" in {
          smallGrid.cell(0, 0).setChecked(true)
          val c = new Cell()
          c.init(true, smallGrid.cell(0, 1).getValue(), 'w', null, false)
          smallGrid.cell(0, 0).getAll() should be(c.getAll())
        }
      }
      "pre-filled with values 1 to n" should {
        val g = new Grid()
        g.init(2, 2, 2)
        val tinyGrid = g
        "have the right values in the right places" in {
          tinyGrid.cell(0, 0).setChecked(true)
          val c = new Cell()
          c.init(true, 0, 'w', null, false)
          tinyGrid.cell(0, 0).getAll() should be(c.getAll())
          c.init(false, 0, 'w', null, false)
          tinyGrid.cell(0, 1).getAll() should be(c.getAll())
          tinyGrid.cell(1, 0).getAll() should be(c.getAll())
          tinyGrid.cell(1, 1).getAll() should be(c.getAll())
          tinyGrid.cell(0, 1).setChecked(true)
          tinyGrid.cell(1, 0).setChecked(true)
          tinyGrid.cell(1, 1).setChecked(true)
        }
      }
    }
  }

}
