package de.htwg.se.minesweeper.aview

import de.htwg.se.minesweeper.controller.Controller

class Tui(controller: Controller) {

  println("Type h for help")
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
        if(inputCustom.length != 3) {
          println("Help: Costum parameters are (height) (width) (mines)")
          return
        } else {
          createGrid(inputCustom(0),inputCustom(1),inputCustom(2))
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
            noMineNumber = 2
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
            println("Wrong number of arguments")
            return
          } else if(vec.length == 2) {
            var row = vec(0).toString.toInt
            var col = vec(1).toString.toInt
            var checked = controller.setChecked(row - 1, col - 1)
            if(controller.getValue(row, col) == 0) {
              controller.depthFirstSearch(row, col)
            }
            if (controller.getMine(row - 1, col - 1)) {
              status = 2
            } else if (checked) {
              noMineNumber -= 1
              if (noMineNumber == 0) {
                status = 1
              }
            }
          } else {
            if(vec(0).toString.equals("f")) {
              var row = vec(1).toString.toInt
              var col = vec(2).toString.toInt
              controller.setFlag(row - 1, col - 1)
            } else {
              println("Please use f")
            }
          }
        }
        if (status == 1) {
          println("Hurray! You win!")
        } else if (status == 2) {
          println("Game Over!")
        }
      }
    }
  }

  def createGrid(height: Int, width:Int, numMines: Int): Unit = {
    controller.createGrid(height, width, numMines)
  }

}
