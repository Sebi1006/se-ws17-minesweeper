package de.htwg.se.minesweeper.aview

import de.htwg.se.minesweeper.controller.Controller

class Tui(controller: Controller) {
  println("Type h for help")
  var lastgame: Int = 1
  var status: Int = 0
  var noMineNumber: Int = 0
  def processInputLine(input: String): Unit = {

    input match {
      case "h" => {
        println("Type 1 for Beginner")
        println("Type 2 for Intermediate")
        println("Type 3 for Expert")
        println("Type 4 for Custom")
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
        createGrid(2, 2, 2)
        status = 0
        noMineNumber = 2
        lastgame = 4
      }
      case "exit" => {
        System.exit(0)
      }
      case "new" => {
        lastgame match {
          case 1 => {
            createGrid(10,10,10)
            status = 0
            noMineNumber = 90
          }
          case 2 => {
            createGrid(16,16,70)
            status = 0
            noMineNumber = 186
          }
          case 3 => {
            createGrid(20,20,150)
            status = 0
            noMineNumber = 250
          }
          case 4 => {
            createGrid(15,15,60)
            status = 0
            noMineNumber = 165
          }
          case _  => {
            createGrid(10,10,10)
            status = 0
            noMineNumber = 90
          }
        }
        controller.createGrid(10, 10, 10)
      }
      case _ => {
        if(status == 0) {
          val vec = input.split(' ')
          if (vec.length != 2) {
            println("Wrong number of arguments")
          } else {
            var row = vec(0).toString.toInt
            var col = vec(1).toString.toInt
            var flag = controller.setChecked(row - 1, col - 1)
            if (controller.getMine(row -1, col - 1)) {
              status = 2
            } else if (flag) {
              noMineNumber -= 1
              if(noMineNumber == 0) {
                status = 1
              }
            }
          }
        }
        if(status == 1) {
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
