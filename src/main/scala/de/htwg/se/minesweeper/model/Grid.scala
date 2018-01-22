package de.htwg.se.minesweeper.model

import scala.util.Random


case class Grid(height: Int, width: Int, numMines: Int){
  var matrix: Vector[Vector[Cell]] = Vector.tabulate(height, width) { (row, col) => new Cell(false, 0, 'w')}


  def cell(row: Int, col: Int): Cell = {
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
    var row: Array[Int] = Array(-1, -1, -1, 0, 1, 1, 1, 0)
    var col: Array[Int] = Array(-1, 0, 1, 1, 1, 0, -1, -1)
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

  override def toString: String = {
    val lineseparator = ("+-" + ("--" * width)) + "+\n"
    val line = ("| " + ("y " * width)) +"|\n"
    var box = "\n" + (lineseparator + (line * height)) + lineseparator
    var turn = 0
    for {
      row <- 0 until height
      col <- 0 until width
    } if(!cell(row, col).checked) {
      box = box.replaceFirst("y", "x")
    } else {
      if(cell(row,col).getValue() == -1) {
        box = box.replaceFirst("y", "b")
      } else {
        box = box.replaceFirst("y", cell(row, col).getValue().toString)
      }
    }
    box
  }
}
