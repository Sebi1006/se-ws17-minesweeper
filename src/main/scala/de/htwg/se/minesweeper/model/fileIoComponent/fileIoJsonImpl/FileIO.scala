package de.htwg.se.minesweeper.model.fileIoComponent.fileIoJsonImpl

import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.Guice
import de.htwg.se.minesweeper.MineSweeperModule
import de.htwg.se.minesweeper.model.fileIoComponent.FileIOInterface
import de.htwg.se.minesweeper.model.gridComponent.{CellInterface, GridFactory, GridInterface}
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: GridInterface = {
    var grid: GridInterface = null
    val source: String = Source.fromFile("grid.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val height = (json \ "grid" \ "height").get.toString.toInt
    val width = (json \ "grid" \ "width").get.toString.toInt
    val numMines = (json \ "grid" \ "numMines").get.toString.toInt
    val injector = Guice.createInjector(new MineSweeperModule)
    grid = injector.instance[GridFactory].create()
    for (i <- 1 until height + 1; j <- 1 until width + 1) {
      val row = (json \\ "row") (i - 1).as[Int]
      val col = (json \\ "col") (j - 1).as[Int]
      val cell = (json \\ "cell") ((i * j) - 1 + (i - 1) * height)
      val value = (cell \ "value").as[Int]
      grid.cell(row, col).setValue(value)
      val checked = (cell \ "checked").as[Boolean]
      grid.cell(row, col).setChecked(checked)
      val flag = (cell \ "flag").as[Boolean]
      grid.cell(row, col).setFlag(flag)
      val color = (cell \ "color").as[Int]
      grid.cell(row, col).setColor(color)
      //val colorBack = (cell \ "colorBack").as[Color]
      //grid.cell(row, col).setColorBack(colorBack)
    }
    grid
  }

  override def save(grid:GridInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.json"))
    pw.write(Json.prettyPrint(gridToJson(grid)))
    pw.close
  }

  implicit val cellWrites = new Writes[CellInterface] {
    def writes(cell: CellInterface) = Json.obj(
      "value" -> cell.getValue(),
      "checked" -> cell.getChecked(),
      "flag" -> cell.getFlag(),
      "color" -> cell.getColor()
    )
  }

  def gridToJson(grid:GridInterface) = {
    Json.obj(
      "grid" -> Json.obj(
        "height" -> JsNumber(grid.getHeight()),
        "width" -> JsNumber(grid.getWidth()),
        "cells" -> Json.toJson(
          for {row <- 0 until grid.getHeight();
               col <- 0 until grid.getWidth()} yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "cell" -> Json.toJson (grid.cell(row, col)))
          }
        )
      )
    )
  }


}