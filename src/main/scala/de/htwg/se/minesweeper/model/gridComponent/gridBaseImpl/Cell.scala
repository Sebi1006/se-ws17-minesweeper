package de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl

import com.google.inject.assistedinject.{Assisted, AssistedInject}
import de.htwg.se.minesweeper.model.gridComponent.CellInterface
import java.awt.Color

class Cell @AssistedInject() (@Assisted var checked: Boolean, @Assisted var value: Int,
                                   @Assisted var color: Int, @Assisted var colorBack: Color,
                                   @Assisted var flag: Boolean) extends CellInterface {

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

  def setFlag(flag: Boolean): Unit = {
    this.flag = flag
  }

  def getFlag(): Boolean = flag

}
