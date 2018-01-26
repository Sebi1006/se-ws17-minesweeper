package de.htwg.se.minesweeper.controller.controllerBaseImpl

import com.google.inject.assistedinject.{Assisted, AssistedInject}
import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.Guice
import de.htwg.se.minesweeper.controller.{CellChanged, ControllerInterface, GridSizeChanged, Winner}
import de.htwg.se.minesweeper.model.gridComponent.{GridFactory, GridInterface}
import de.htwg.se.minesweeper.util.UndoManager
import java.awt.Color

import de.htwg.se.minesweeper.MineSweeperModule
import de.htwg.se.minesweeper.model.fileIoComponent.FileIOInterface

import scala.swing.Publisher

class Controller @AssistedInject() (@Assisted var grid: GridInterface) extends ControllerInterface with Publisher {

  publish(new GridSizeChanged(grid.getHeight, grid.getWidth, grid.getNumMines))
  val injector = Guice.createInjector(new MineSweeperModule)
  var noMineCount: Int = (grid.getHeight * grid.getWidth) - grid.getNumMines
  var mineFound: Int = 0
  var flag: Boolean = true
  private var undoManager = new UndoManager
  val fileIo = injector.instance[FileIOInterface]
  var intList: List[(Int, Int)] = Nil
  var status = 0
  var i = 0

  def createGrid(height: Int, width: Int, numMines: Int): Unit = {
    grid = injector.instance[GridFactory].create()
    grid.init(height, width, numMines)
    noMineCount = (height * width) - numMines
    mineFound = numMines
    flag = true
    undoManager = new UndoManager
    intList = Nil
    publish(new GridSizeChanged(height, width, numMines))
  }

  def setChecked(row: Int, col: Int, undo: Boolean, command: Boolean, dpfs: Boolean): Unit = {
    if(status == 0 || command) {
      if (command) {
        undoManager.doStep(new SetCommand(row, col, undo, intList, 1, this))
      }
      if (!undo) {
        if (grid.cell(row, col).getChecked) {
          return
        }
        grid.cell(row, col).setChecked(true)
        if (flag) {
          grid.setMines(row, col)
          grid.setValues()
          flag = false
        }
        if (!dpfs && grid.cell(row, col).getValue() == 0) {
          println("a")
          undoManager.undoStep
          depthFirstSearch(row, col)
          undoManager.doStep(new SetCommand(0, 0, true, intList, 3, this))
          publish(new CellChanged())
        }
        i += 1
        println(i)
        winner(row, col, undo)
      } else {
        if (!grid.cell(row, col).getChecked()) {
          return
        }
        grid.cell(row, col).setChecked(false)
        winner(row, col, undo)
      }
      if (!dpfs) {
        publish(new CellChanged())
      }
    }
  }

  def getChecked(row: Int, col: Int): Boolean = {
    grid.cell(row, col).getChecked()
  }

  def getMine(row: Int, col: Int): Boolean = {
    if (grid.cell(row, col).getValue() == -1) {
      return true
    }
    false
  }

  def getValue(row: Int, col: Int): Int = {
    grid.cell(row, col).getValue()
  }

  def setColor(row: Int, col: Int, color: Int): Unit = {
    grid.cell(row, col).setColor(color)
  }

  def getColor(row: Int, col: Int): Int = {
    grid.cell(row, col).getColor()
  }

  def height(): Int = {
    grid.getHeight
  }

  def width(): Int = {
    grid.getWidth
  }

  def setColorBack(row: Int, col: Int, color: Color): Unit = {
    grid.cell(row, col).setColorBack(color)
  }

  def getColorBack(row: Int, col: Int): Color = {
    grid.cell(row, col).getColorBack()
  }

  def setFlag(row: Int, col: Int, undo: Boolean, command: Boolean): Unit = {
    if (command) {
      undoManager.doStep(new SetCommand(row, col, undo, intList, 2, this))
    }
    grid.cell(row, col).setFlag(!undo)
    if (undo) {
      mineFound += 1
    } else {
      mineFound -= 1
    }
    publish(new CellChanged())
  }

  def getFlag(row: Int, col: Int): Boolean = {
    grid.cell(row, col).getFlag()
  }

  def depthFirstSearch(rowD: Int, colD: Int): Unit = {
    var R: Int = 0
    var C: Int = 0
    if (!getChecked(rowD, colD)) {
      intList = (rowD, colD) :: intList
    }
    setColor(rowD, colD, 'b')
    setColorBack(rowD, colD, Color.LIGHT_GRAY)
    setChecked(rowD, colD, false, false, true)
    for (i <- 0.until(8)) {
      R = rowD + grid.getRow(i)
      C = colD + grid.getCol(i)
      if (R >= 0 && R < height && C >= 0 && C < width &&
        getColor(R, C) == 'w') {
        if (getValue(R, C) == 0) {
          depthFirstSearch(R, C)
        } else {
          if(!getChecked(R, C)) {
            intList = (R, C) :: intList
          }
          setChecked(R, C, false, false, true)
          setColor(R, C, 'b')
        }
      }
    }
  }

  def winner(row: Int, col: Int, undo: Boolean): Unit = {
    if (!undo) {
      if (grid.cell(row, col).getValue() != -1) {
        status = 0
        noMineCount -= 1
      } else {
        for (i <- 0 until grid.getHeight(); j <- 0 until grid.getWidth()) {
          if (grid.cell(i, j).getValue() == -1) {
            grid.cell(i, j).setChecked(true)
          }
        }
        status = 1
        publish(new Winner(false))
      }
      if (noMineCount == 0) {
        status = 2
        publish(new Winner(true))
      }
    } else {
      if (grid.cell(row, col).getValue() == -1) {
        status = 0
        for (i <- 0 until grid.getHeight(); j <- 0 until grid.getWidth()) {
          if (grid.cell(i, j).getValue() == -1) {
            grid.cell(i, j).setChecked(false)
          }
        }
      } else {
        noMineCount += 1
      }
      if (noMineCount == 0) {
        publish(new Winner(true))
      }
    }
  }

  def undo(): Unit = {
    undoManager.undoStep
    publish(new CellChanged())
  }

  def redo(): Unit = {
    undoManager.redoStep
    publish(new CellChanged())
  }

  def solve(): Unit = {
    intList = grid.solve
    undoManager.doStep(new SetCommand(0, 0, true, intList, 4, this))
    publish(new CellChanged())
  }

  def save: Unit = {
    fileIo.save(grid)
    publish(new CellChanged)
  }

  def load: Unit = {
    grid = fileIo.load
    publish(new CellChanged)
  }

  def getAll(row: Int, col: Int):(Boolean, Boolean, Int, Int, Int, Int, Color, Boolean) = {
    (getChecked(row, col), getMine(row, col), getValue(row, col), getColor(row,col), height(), width(), getColorBack(row, col), getFlag(row, col))
  }
}
