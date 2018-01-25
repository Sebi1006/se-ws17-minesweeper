package de.htwg.se.minesweeper

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.minesweeper.controller.{ControllerFactory, ControllerInterface}
import de.htwg.se.minesweeper.controller.controllerBaseImpl.Controller
import de.htwg.se.minesweeper.model.fileIoComponent.FileIOInterface
import de.htwg.se.minesweeper.model.fileIoComponent.fileIoJsonImpl.FileIO
import de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.minesweeper.model.gridComponent.{CellFactory, CellInterface, GridFactory, GridInterface}
//import de.htwg.se.minesweeper.model.gridComponent.gridAdvancedImpl.Grid
import de.htwg.se.minesweeper.model.gridComponent.gridBaseImpl.Cell
import net.codingwell.scalaguice.ScalaModule

class MineSweeperModule extends AbstractModule with ScalaModule {

  val defaultSize:Int = 10

  override def configure() = {
    install(new FactoryModuleBuilder().implement(classOf[GridInterface], classOf[Grid]).build(classOf[GridFactory]))
    install(new FactoryModuleBuilder().implement(classOf[CellInterface], classOf[Cell]).build(classOf[CellFactory]))
    install(new FactoryModuleBuilder().implement(classOf[ControllerInterface], classOf[Controller]).build(classOf[ControllerFactory]))
    //bind[ControllerInterface].to[Controller]
    bind[CellInterface].to[Cell]
    bind[FileIOInterface].to[FileIO]
  }

}