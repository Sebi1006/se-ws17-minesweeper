package de.htwg.se.minesweeper.model

case class Player(name: String) {
  override def toString: String = name
}
