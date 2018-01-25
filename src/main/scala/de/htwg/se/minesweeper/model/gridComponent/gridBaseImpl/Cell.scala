package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import de.htwg.se.minesweeper.model.gridComponent.CellInterface
import java.awt.Color

case class Cell(var checked: Boolean, var value: Int, var color: Int, var colorBack: Color, var flag: Boolean) extends CellInterface {

  def setValue(value: Int): Unit = {
    this.value = value
  }

  def getValue(): Int = value

  def setChecked(checked: Boolean): Unit = {
    this.checked = checked
  }

  def getChecked(): Boolean = checked

  def setColor(color: Int): Unit = {
    this.color = color
  }

  def getColor(): Int = color

  def setColorBack(color: Color): Unit = {
    this.colorBack = color
  }

  def getColorBack(): Color = colorBack

  def setFlag(): Unit = {
    this.flag = true
  }

  def getFlag(): Boolean = flag

}
