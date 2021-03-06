package shellPoker.gameEngine.table

import org.scalatest.FunSuite
import shellPoker.gameEngine.player.{ChipStack, NegativeChipCountException, Player}

/** Tests for ChipStack class. */
class PotTest extends FunSuite {

  val player1 = new Player(null, null, new ChipStack(500))
  val player2 = new Player(null, null, new ChipStack(500))
  val player3 = new Player(null, null, new ChipStack(500))

  val players = List(player1, player2, player3)

  val pot: Pot = new Pot(players)

  test("Pot should throw NegativeChipCountException when trying to add negative chips amount.") {

    assertThrows[NegativeChipCountException](pot.addChips(-1))
  }

  test("Pot size should increase wen chips are added.") {

    pot.addChips(38)
    assert(pot.size === 38)
    pot.addChips(100)
    assert(pot.size === 138)
  }

  test("entitledPlayers should contain only players, that are still in the game.") {

    assert(pot.entitledPlayers === players)
    player3.setFolded()

    assert(pot.entitledPlayers === List(player1, player2))
  }
}

