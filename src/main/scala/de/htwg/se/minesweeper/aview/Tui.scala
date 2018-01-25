package de.htwg.se.minesweeper.aview

import de.htwg.se.minesweeper.controller.controllerBaseImpl.Controller
import de.htwg.se.minesweeper.controller.{CellChanged, GridSizeChanged, Winner}
import scala.swing.Reactor
import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Reactor {

  listenTo(controller)
  println("Type h for Help")
  var lastgame: Int = 1
  var status: Int = 0
  var noMineNumber: Int = 0
  var savedHeight: Int = 10
  var savedWidth: Int = 10
  var savedMines: Int = 10

  def processInputLine(input: String): Unit = {
    input match {
      case "h" => {
        println("Type 1 for Beginner")
        println("Type 2 for Intermediate")
        println("Type 3 for Expert")
        println("Type 4 for Custom")
        println("Type f to set a flag")
        println("Type exit for Exit")
        println("Type new for New Game")
      }
      case "1" => {
        createGrid(10, 10, 10)
        status = 0
        noMineNumber = 90
        lastgame = 1
      }
      case "2" => {
        createGrid(16, 16, 70)
        status = 0
        noMineNumber = 186
        lastgame = 2
      }
      case "3" => {
        createGrid(20, 20, 150)
        status = 0
        noMineNumber = 250
        lastgame = 3
      }
      case "4" => {
        var input: String = readLine()
        var inputCustom = input.split(' ').map(c => c.toInt)
        if (inputCustom.length != 3) {
          println("Help: Custom parameters are (height) (width) (mines)")
          return
        } else {
          if (inputCustom(2) >= inputCustom(0) * inputCustom(1)) {
            println("Number of Mines must be smaller than grid size")
            return
          } else if (inputCustom(0) < 10 || inputCustom(1) < 10 || inputCustom(2) < 10) {
            println("Height, Width and Number of Mines must be minimum 10")
            return
          } else if (inputCustom(0) > 35 || inputCustom(1) > 35) {
            println("Height and Width may not exceed 35")
            return
          }
          createGrid(inputCustom(0), inputCustom(1), inputCustom(2))
          noMineNumber = (inputCustom(0) * inputCustom(1)) - inputCustom(2)
          savedHeight = inputCustom(0)
          savedWidth = inputCustom(1)
          savedMines = inputCustom(2)
        }
        status = 0
        lastgame = 4
      }
      case "exit" => {
        System.exit(0)
      }
      case "new" => {
        lastgame match {
          case 1 => {
            createGrid(10, 10, 10)
            status = 0
            noMineNumber = 90
          }
          case 2 => {
            createGrid(16, 16, 70)
            status = 0
            noMineNumber = 186
          }
          case 3 => {
            createGrid(20, 20, 150)
            status = 0
            noMineNumber = 250
          }
          case 4 => {
            createGrid(savedHeight, savedWidth, savedMines)
            status = 0
            noMineNumber = (savedHeight * savedWidth) - savedMines
          }
          case _  => {
            createGrid(10, 10, 10)
            status = 0
            noMineNumber = 90
          }
        }
        controller.createGrid(10, 10, 10)
      }
      case _ => {
        if (status == 0) {
          val vec = input.split(' ')
          if (vec.length != 2 && vec.length != 3) {
            println("Wrong Number of Arguments")
            return
          } else if (vec.length == 2) {
            var row = vec(0).toString.toInt
            var col = vec(1).toString.toInt
            controller.setChecked(row - 1, col - 1, false, true)
          } else {
            if (vec(0).toString.equals("f")) {
              var row = vec(1).toString.toInt
              var col = vec(2).toString.toInt
              controller.setFlag(row - 1, col - 1)
            } else {
              println("Please use f to set a flag")
            }
          }
        }
      }
    }
  }

  def createGrid(height: Int, width: Int, numMines: Int): Unit = {
    controller.createGrid(height, width, numMines)
  }

  reactions += {
    case event: GridSizeChanged => {
      println(controller.grid.toString)
      status = 0
    }
    case event: CellChanged => println(controller.grid.toString)
    case event: Winner => {
      if (event.win) {
        println("Hurray! You win!")
        status = 1
      } else {
        println("Game Over!")
        status = 2
      }
    }
  }

}
