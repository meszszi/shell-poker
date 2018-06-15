package shellPoker.gameEngine

import shellPoker.gameEngine.handEnding.{ShowdownManager, ShowdownStatus}

/** Supervisor of a single hand.
  *
  * @param initState Initial state of the hand.
  */
class HandSupervisor(val initState: GameState, val supervisor: RoomSupervisorActor) {

  private val actionManager: ActionManager = new ActionManager(initState)
  private val showdownManager: ShowdownManager = new ShowdownManager

  /** Plays a single hand, ending when some people win chips */
  def playSingleHand(): Unit = {

    val table = initState.table

    // Posting blinds
    if(!table.smallBlind.isEmpty)
      table.smallBlind.player.postBlind(initState.smallBlindValue)

    if(!table.bigBlind.isEmpty)
      table.bigBlind.player.postBlind(initState.bigBlindValue)


    // Shuffling the deck and dealing hole cards
    table.dealer.clearAllCards()
    table.dealer.proceedWithAction()


    var possibleAction: Boolean = true
    var showdownStatuses: List[ShowdownStatus] = null

    while (possibleAction && table.dealer.status != Showdown) {

      // Standard betting round elements
      actionManager.startNextBettingRound()
      runBettingRound(supervisor)
      table.potManager.collectBets()

      // Sending update info about current game state
      supervisor ! update

      if (table.activePlayersNumber <= 1)
        possibleAction = false

      table.dealer.proceedWithAction()
    }

    if (table.dealer.status == Showdown)
      ??? // klasyczny showdown

    else {
      ??? // alternatywne zakonczenie
    }

    ??? // distribute won chips

    supervisor ! finalStatus  //??? could be like that
  }

  private def runBettingRound(supervisor: RoomSupervisorActor) = {

    //if current action taker == null that means that the round has ended
    while(actionManager.actionTaker != null){
      //getting action from current action taker
      val currentAction: Action = getPlayerAction(actionManager.actionTaker)

      //apply that action to the state fo the action manager
      actionManager.applyAction(currentAction)

      //send the supervisor info about current action
      supervisor ! currentAction
    }
  }

  private def getPlayerAction(actionTaker: TableSeat): Action = {

    //get player actor object corresponding to the current seat
    val playerActor: PlayerActor = tableSeatToPlayerActor(actionTaker)

    //get initial player action
    var playerAction: Action = requestAction(playerActor)

    //get initial action validation for this action
    var actionLegalness: ActionValidation = actionManager.validateAction(playerAction)

    //keep requesting for legal action
    while(actionLegalness != Legal){

      //sending illegal message info to the player actor
      playerActor ! actionLegalness

      //getting the action again
      playerAction = requestAction(playerActor)

      //get the action validation
      actionLegalness = actionManager.validateAction(playerAction)
    }

    playerAction
  }

  /** Prompts the player actor to return Action object */
  private def requestAction(playerActor: PlayerActor): Action = ???

  /** Maps TableSeat objects to corresponding PlayerActor objects. */
  private def tableSeatToPlayerActor(tableSeat: TableSeat): PlayerActor = ???



}
