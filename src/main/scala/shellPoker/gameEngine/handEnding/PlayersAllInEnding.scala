package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.{PokerTable, Showdown}

/** Represents the type of ending when all players in game went all-in before the river.
  * Final actions in this case include showing all in-game players' cards and dealing missing streets.
  *
  * @param table Table at which the game is played.
  */
class PlayersAllInEnding(val table: PokerTable) extends HandEndingHelper(table, PlayersAllIn) {

  /* Makes every player at the table show their cards and deals missing streets. */
  override def proceedWithFinalActions(): Unit = {

    table.playersInHand.foreach(_.showCards())
    while(table.dealer.status != Showdown)
      table.dealer.proceedWithAction()
  }

  /* Chooses winner (or winners) for every pot on the table. */
  override def calculateHandResults(): CompleteHandResults = {

    val results = new CompleteHandResults(table.players)

    for (pot <- table.potManager.pots) {

      val potentialWinners
    }

  }
}
