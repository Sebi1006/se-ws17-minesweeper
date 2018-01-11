package de.htwg.se.minesweeper.model

import scala.util.Random

case class Matrix(rows: Vector[Vector[Cell]]) {
    def this(length: Int, width: Int, fill: Cell) = this(Vector.tabulate(length, width) {(row, col) => fill })

    val size: Int = rows.size

    def cell(row: Int, col: Int): Cell = rows(row)(col)

    def replaceCell(row: Int, col: Int, value: Int): Matrix = {
        rows(row)(col).setValue(value)
        return Matrix(rows)
    }

    def generateMines(count: Int) {
        val rand: Random = new Random()
        for(i <- 0 until count) {
            val row = rand.nextInt(rows.length)
            val col = rand.nextInt(rows(0).length)
            replaceCell(row, col, -1)
        }
    }
    def neighbours(cell: Cell) {
        var value: Int = 0
        for (rowIndex <- 0 until rows.size) {
            for(colIndex <- 0 until rows.size) {
                if (rows(rowIndex)(colIndex).getValue() != -1) {
                    if (rows(rowIndex + 1)(colIndex - 1).getValue() < 0) value += 1
                    if (rows(rowIndex + 1)(colIndex).getValue() < 0) value += 1
                    if (rows(rowIndex + 1)(colIndex + 1).getValue() < 0) value += 1
                    if (rows(rowIndex)(colIndex - 1).getValue() < 0) value += 1
                    if (rows(rowIndex)(colIndex + 1).getValue() < 0) value += 1
                    if (rows(rowIndex - 1)(colIndex - 1).getValue() < 0) value += 1
                    if (rows(rowIndex - 1)(colIndex).getValue() < 0) value += 1
                    if (rows(rowIndex - 1)(colIndex + 1).getValue() < 0) value += 1
                }
            }
        }
    }
}

