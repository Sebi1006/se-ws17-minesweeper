package de.htwg.se.minesweeper.model

case class Cell(var checked: Boolean, var value: Int, var color: Int) {
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
}
