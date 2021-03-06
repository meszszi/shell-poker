import akka.actor.ActorSystem
import shellPoker.actors.{ActorAddress, ActorsConfig, StartGame}
import shellPoker.actors.client.UserActor
import shellPoker.actors.client.UserActor.Start
import shellPoker.actors.server.TournamentSupervisorActor
import shellPoker.core.cards._
import shellPoker.core.pokerHands.{PokerHand, PokerHandFactory}
import shellPoker.gameEngine._
import shellPoker.gameEngine.gameplay.hand.{Communicator, HandSupervisor, LocalCommunicator, RemoteCommunicator}
import shellPoker.gameEngine.gameplay.room.{RoomSupervisor, TournamentSupervisor}
import shellPoker.gameEngine.gameplay.{GameSettings, GameState}
import shellPoker.gameEngine.player.{ChipStack, LocalTestPlayer, Player, PlayerId}

import scala.io.StdIn
import scala.util.Random

object Testing extends App {

  val gameSettings: GameSettings = GameSettings(
    startingStack = 10000,
    seatsNumber = 3,
    smallBlindValue = 50,
    bigBlindValue = 100,
    decisionTime = 3000)

  print("mode: ")
  val mode: String = {try StdIn.readLine()}

  print("your ip: ")
  val thisIp: String = {try StdIn.readLine()}

  if (mode == "server") {

    val myConfig = ActorsConfig.getSystemConfig(thisIp, 50000)
    val system = ActorSystem("poksik", config = myConfig)
    val room = system.actorOf(TournamentSupervisorActor.props(gameSettings), name = "room")

    println("server is ready")

    while(true) {

      try {
        print("> ")
        val command = StdIn.readLine()
        if(command == "start")
          room ! StartGame
      }
    }
  }

  // Client
  else {

    val config = ActorsConfig.getSystemConfig(thisIp, 0)
    val system = ActorSystem("client", config)

    print("server ip: ")
    val serverIp: String = { try StdIn.readLine() }

    val serverAddress: ActorAddress = ActorAddress("poksik", "/user/room", serverIp, 50000)
    val player = system.actorOf(UserActor.props(serverAddress), "player")

    player ! Start
  }
}
