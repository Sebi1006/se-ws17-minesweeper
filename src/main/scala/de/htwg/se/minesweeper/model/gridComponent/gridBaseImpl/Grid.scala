package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import com.google.inject.assistedinject.{Assisted, AssistedInject}
import de.htwg.se.minesweeper.model.gridComponent.{CellInterface, GridInterface}
import scala.util.Random

case class Grid @AssistedInject() () extends GridInterface {

  var height: Int = 10
  var width: Int = 10
  var numMines: Int = 10
  var matrix: Vector[Vector[Cell]] = Vector.tabulate(height, width) { (row, col) => new Cell() }
  var row: Array[Int] = Array(-1, -1, -1, 0, 1, 1, 1, 0)
  var col: Array[Int] = Array(-1, 0, 1, 1, 1, 0, -1, -1)


  def init(height: Int, width: Int, numMines: Int): Unit = {
    this.height = height
    this.width = width
    this.numMines = numMines
    matrix = Vector.tabulate(height, width) { (row, col) => new Cell() }
  }

  def cell(row: Int, col: Int): CellInterface = {
    matrix(row)(col)
  }

  def setMines(rowUsed: Int, colUsed: Int): Unit = {
    val rand: Random = new Random()
    var row: Int = 0
    var col: Int = 0
    var i: Int = 0
    while (i < numMines) {
      row = rand.nextInt(height)
      col = rand.nextInt(width)
      if (cell(row, col).getValue() != -1 && !(row == rowUsed && col == colUsed)) {
        cell(row, col).setValue(-1)
        cell(row, col).setColor('b')
      } else {
        { i -= 1; i + 1 }
      }
      i += 1
    }
  }

  def setValues(): Unit = {
    var rowC: Int = 0
    var colC: Int = 0
    for (i <- 0 until height; j <- 0 until width) {
      var value: Int = 0
      var R: Int = 0
      var C: Int = 0
      rowC = i
      colC = j
      if (cell(rowC, colC).getValue() != -1) {
        for (k <- 0.until(8)) {
          R = rowC + row(k)
          C = colC + col(k)
          if (R >= 0 && C >= 0 && R < height && C < width) {
            if (cell(R, C).getValue() == -1) {
              { value += 1; value - 1 }
            }
          }
        }
        cell(rowC, colC).setValue(value)
      }
    }
  }

  def getRow(i: Int): Int = {
    row(i)
  }

  def getCol(i: Int): Int = {
    col(i)
  }

  def getHeight(): Int = {
    height
  }

  def getWidth(): Int = {
    width
  }

  def getNumMines(): Int = {
    numMines
  }

  override def toString: String = {
    val lineseparator = ("+-" + ("--" * width)) + "+\n"
    val line = ("| " + ("y " * width)) + "|\n"
    var box = "\n" + (lineseparator + (line * height)) + lineseparator
    var turn = 0
    for {
      row <- 0 until height
      col <- 0 until width
    } if (!cell(row, col).getChecked()) {
        if (!cell(row, col).getFlag()) {
          box = box.replaceFirst("y", "x")
        } else {
          box = box.replaceFirst("y", "f")
        }
    } else {
      if (cell(row,col).getValue() == -1) {
        box = box.replaceFirst("y", "b")
      } else {
        box = box.replaceFirst("y", cell(row, col).getValue().toString)
      }
    }
    box
  }

  def solve: Unit = {
    new Solver(this)
  }

}
