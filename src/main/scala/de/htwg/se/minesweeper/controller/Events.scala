package de.htwg.se.minesweeper.controller

import scala.swing.event.Event

case class CellChanged() extends Event
case class GridSizeChanged(height: Int, width: Int, mineNumber: Int) extends Event
case class Winner(win: Boolean) extends Event
