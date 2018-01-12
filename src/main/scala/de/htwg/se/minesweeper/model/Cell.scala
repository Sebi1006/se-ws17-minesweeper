package de.htwg.se.minesweeper.model

case class Cell(var checked: Boolean, var value: Int) {

  def setValue(value: Int): Unit = {
    this.value = value
  }

  def setChecked(checked: Boolean): Unit = {
    this.checked = checked
  }

  def getValue(): Int = value
}