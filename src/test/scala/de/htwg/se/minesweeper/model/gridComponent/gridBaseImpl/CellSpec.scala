package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import java.awt.Color

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class CellSpec extends WordSpec with Matchers {

  "A Cell" when {
    "not set to any value " should {
      val emptyCell = new Cell()
      emptyCell.init(false, 0, 'w', new Color(255, 255, 255), false)
      "have value 0" in {
        emptyCell.value should be(0)
      }
      "not be set" in {
        emptyCell.getChecked() should be(false)
      }
    }
    "set to a specific value" should {
      val nonEmptyCell = new Cell()
      nonEmptyCell.init(true, 5, 'w', new Color(255, 255, 255), false)
      "return that value" in {
        nonEmptyCell.getValue() should be(5)
      }
      "be set" in {
        nonEmptyCell.getChecked() should be(true)
      }
    }
  }

}
