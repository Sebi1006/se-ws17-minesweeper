package de.htwg.se.minesweeper.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.minesweeper.MineSweeperModule
import de.htwg.se.minesweeper.model.fileIoComponent.FileIOInterface
import de.htwg.se.minesweeper.model.gridComponent.{GridFactory, GridInterface}
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: GridInterface = {
    var grid: GridInterface = null
    val source: String = Source.fromFile("grid.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val size = (json \ "grid" \ "size").get.toString.toInt
    val injector = Guice.createInjector(new MineSweeperModule)
    grid = injector.instance[GridFactory].create()
    grid.init(height, width, numMines)
    for (index <- 0 until size*size) {
      val row = (json\\"row") (index).as[Int]
      val col = (json \\"col") (index).as[Int]
      val cell = (json \\ "cell")(index)
      val value = (cell \ "value").as[Int]
      grid = grid.set(row, col, value)
      val given = (cell \ "given").as[Boolean]
      val showCandidates = (cell \ "showCandidates").as[Boolean]
      if (given) grid = grid.setGiven(row, col, value)
      if (showCandidates) grid = grid.setShowCandidates(row, col)
    }
    grid
  }