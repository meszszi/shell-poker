package shellPoker.gameEngine.handEnding

import shellPoker.gameEngine.Player

/** Represents the results of all players at the table after completed hand.
  *
  */
class CompleteHandResults(val players: List[Player]) {

  val results: List[SinglePlayerHandResults] =
    for (player <- players) yield new SinglePlayerHandResults(player)

  def addChipsToPlayer(player: Player, amount: Int): Unit = {

    val targetSlot: SinglePlayerHandResults = results.find(_.player ==  player).orNull
    targetSlot.addChips(amount)
  }
}