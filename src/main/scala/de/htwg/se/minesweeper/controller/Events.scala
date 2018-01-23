package de.htwg.se.minesweeper.controller

import scala.swing.event.Event

class CellChanged extends Event
case class GridSizeChanged(height: Int, width: Int, mineNumber: Int) extends Event