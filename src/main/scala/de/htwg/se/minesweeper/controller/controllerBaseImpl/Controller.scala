package de.htwg.se.minesweeper.controller.controllerBaseImpl

import com.google.inject.assistedinject.{Assisted, AssistedInject}
import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.Guice
import de.htwg.se.minesweeper.controller.{CellChanged, ControllerInterface, GridSizeChanged, Winner}
import de.htwg.se.minesweeper.model.gridComponent.{GridFactory, GridInterface}
import de.htwg.se.minesweeper.util.UndoManager
import java.awt._

import de.htwg.se.minesweeper.MineSweeperModule

import scala.swing.Publisher

class Controller @AssistedInject() (@Assisted var grid: GridInterface) extends ControllerInterface with Publisher {

  publish(new GridSizeChanged(grid.getHeight, grid.getWidth, grid.getNumMines))
  val injector = Guice.createInjector(new MineSweeperModule)
  var noMineCount: Int = (grid.getHeight * grid.getWidth) - grid.getNumMines
  var mineFound: Int = 0
  var flag: Boolean = true
  private val undoManager = new UndoManager

  def createGrid(height: Int, width: Int, numMines: Int): Unit = {
    grid = injector.instance[GridFactory].create(grid.getHeight(), grid.getWidth(), grid.getNumMines())
    noMineCount = (height * width) - numMines
    mineFound = numMines
    flag = true
    publish(new GridSizeChanged(height, width, numMines))
  }

  def setChecked(row: Int, col: Int, undo: Boolean, command: Boolean): Unit = {
    if (command) {
      undoManager.doStep(new SetCommand(row, col, undo, this))
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
      if(grid.cell(row, col).getValue() == 0) {
        depthFirstSearch(row, col)
      }
      winner(row, col, undo)
    } else {
      if (!grid.cell(row, col).getChecked()) {
        return
      }
      grid.cell(row, col).setChecked(false)
    }
    publish(new CellChanged())
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

  def setFlag(row: Int, col: Int, undo: Boolean): Unit = {
    grid.cell(row, col).setFlag(!undo)
    mineFound -= 1
    publish(new CellChanged())
  }

  def getFlag(row: Int, col: Int): Boolean = {
    grid.cell(row, col).getFlag()
  }

  def depthFirstSearch(rowD: Int, colD: Int): Unit = {
    var R: Int = 0
    var C: Int = 0
    setColor(rowD, colD, 'b')
    setColorBack(rowD, colD, Color.LIGHT_GRAY)
    setChecked(rowD, colD, false, true)
    for (i <- 0.until(8)) {
      R = rowD + grid.getRow(i)
      C = colD + grid.getCol(i)
      if (R >= 0 && R < height && C >= 0 && C < width &&
        getColor(R, C) == 'w') {
        if (getValue(R, C) == 0) {
          depthFirstSearch(R, C)
        } else {
          setChecked(R, C, false, true)
          setColor(R, C, 'b')
        }
      }
    }
  }

  def winner(row: Int, col: Int, undo: Boolean): Unit = {
    if (!undo) {
      if (grid.cell(row, col).getValue() != -1) {
        noMineCount -= 1
      } else {
        publish(new Winner(false))
      }
      if (noMineCount == 0) {
        publish(new Winner(true))
      }
    } else {
      if (grid.cell(row, col).getValue() == -1) {
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
    grid.solve
    publish(new CellChanged())
  }

}
