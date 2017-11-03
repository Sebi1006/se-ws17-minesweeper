package de.htwg.se.minesweeper

import de.htwg.se.minesweeper.model.Player

object MineSweeper {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
