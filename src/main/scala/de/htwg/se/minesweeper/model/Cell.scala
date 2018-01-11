package de.htwg.se.minesweeper.model


case class Cell(var checked: Boolean, var value: Int) {

    def setValue(value: Int) {
        this.value = value
    }

    def getValue(): Int = value
}