package de.htwg.se.minesweeper.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.minesweeper.controller._
import scala.io.Source._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "HTWG Minesweeper"
  var cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)

  def highlightpanel = new FlowPanel {
    contents += new Label("Highlight:")
    for { index <- 0 to controller.gridSize } {
      val button = Button(if (index == 0) "" else index.toString) {
      }
      button.preferredSize_=(new Dimension(30, 30))
      contents += button
      listenTo(button)
    }
  }

  def gridPanel = new GridPanel(controller.blockSize, controller.blockSize) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until controller.blockSize
      outerColumn <- 0 until controller.blockSize
    } {
      contents += new GridPanel(controller.blockSize, controller.blockSize) {
        border = LineBorder(java.awt.Color.BLACK, 2)
        for {
          innerRow <- 0 until controller.blockSize
          innerColumn <- 0 until controller.blockSize
        } {
          val x = outerRow * controller.blockSize + innerRow
          val y = outerColumn * controller.blockSize + innerColumn
          val cellPanel = new CellPanel(x, y, controller)
          cells(x)(y) = cellPanel
          contents += cellPanel
          listenTo(cellPanel)
        }
      }
    }
  }
  val statusline = new TextField(controller.statusText, 20)

  contents = new BorderPanel {
    add(highlightpanel, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
        contents += new Menu("File") {
          mnemonic = Key.F
          contents += new MenuItem(Action("Random") { controller.createRandomGrid(controller.gridSize, controller.gridSize, 5) })
          contents += new MenuItem(Action("Quit") { System.exit(0) })
        }
        contents += new Menu("Edit") {
          mnemonic = Key.E
        }
        contents += new Menu("Solve") {
          mnemonic = Key.S
        }
        contents += new Menu("Options") {
          mnemonic = Key.O

    }
  }

  visible = true
  redraw

  def resize(gridSize: Int) = {
    cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
    contents = new BorderPanel {
      add(highlightpanel, BorderPanel.Position.North)
      add(gridPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
  }
  def redraw = {
    for {
      row <- 0 until controller.gridSize
      column <- 0 until controller.gridSize
    } cells(row)(column).redraw
    statusline.text = controller.statusText
    repaint
  }
}