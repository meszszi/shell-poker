package shellPoker.core.pokerHands

import shellPoker.core.cards._

case object StraightFlush extends PokerHandFactory(StraightFlushRank) {

  /** Creates a new StraightFlush instance. */
  override protected def makeHand(cards: List[Card]) = new StraightFlush(cards)


  /** Tests if given cards list make a StraightFlush hand.
    *
    * Firstly checks if cards make an ace-to-five StraightFlush.
    * If not, checks if cards contain five consecutive ranks and are the same suit
    */
  override def isMadeUpOf(cards: List[Card]): Boolean = {
    HandEvaluationHelper.areSingleSuited(cards) &&
      HandEvaluationHelper.areConsecutive(cards)
  }
}


/** Represents a StraightFlush poker hand. */
case class StraightFlush private(override val cards: List[Card]) extends PokerHand(StraightFlushRank, cards) {

  /** Returns true if this.cards make a stronger StraightFlush than other.cards.
    *
    * Evaluation as in Straight poker hand.
    */
  override protected def isStrongerWithinRank(other: PokerHand): Boolean = {

    val thisSortedRanks: List[CardRank] = this.cards.map(_.rank).sortWith(_ < _)
    val otherSortedRanks: List[CardRank] = other.cards.map(_.rank).sortWith(_ < _)

    if (thisSortedRanks == List(Two, Three, Four, Five, Ace))
      return false

    if (otherSortedRanks == List(Two, Three, Four, Five, Ace))
      return true

    thisSortedRanks.last > otherSortedRanks.last
  }
}
