package shellPoker.gameEngine

/** Supervisor of a single hand.
  *
  * @param initState Initial state of the hand.
  */
class HandSupervisor(val initState: GameState, val supervisor: RoomSupervisorActor) {

  private val actionManager: ActionManager = new ActionManager(initState)
  private val showdownManager: ShowdownManager = new ShowdownManager

  /** Plays a single hand, ending when some people win chips */
  def playSingleHand(): Unit = {

    val hasEnded: Boolean = false

    val table = initState.table
    // table.dealAllHoleCards()

    var showDownStatuses: List[ShowdownStatus] = _

    while (!hasEnded) {
      table.dealer.dealNextStreet() //now it should include dealing hole cards, maybe table.nextDealerAction ?

      actionManager.startNextBettingRound()

      runBettingRound(supervisor)

      supervisor ! showStatus // not sure if nessecary

      if (table.activePlayersNumber <= 1 || table.dealer.status == River){
        showDownStatuses = showdownManager.getShowdownStatuses(table, actionManager.roundEndingSeat)
        hasEnded = true
      }
    }

    supervisor ! showDownStatuses  //??? could be like that 
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
