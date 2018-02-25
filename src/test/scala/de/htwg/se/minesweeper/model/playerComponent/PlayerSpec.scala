package de.htwg.se.minesweeper.model

import de.htwg.se.minesweeper.model.playerComponent.Player
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {

  "A Player" when {
    "new" should {
      val player = Player("Master")
      "have a name" in {
        player.name should be("Master")
      }
      "have a nice string representation" in {
        player.toString should be("Master")
      }
    }
  }

}
