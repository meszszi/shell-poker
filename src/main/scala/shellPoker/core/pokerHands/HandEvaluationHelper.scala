package shellPoker.core.pokerHands

import shellPoker.core.cards._


object HandEvaluationHelper {

  /** Returns all ranks that satisfy given count condition.
    *
    * @param cards list of Card class instances
    * @param predicate function that is evaluated over each Rank count
    * @return list of all Ranks that satisfy given condition
    *
    * Method iterates through all of the possible rank values.
    * For each rank a count is made of how often the rank appears in the cards list.
    * If the count number satisfies the predicate, the rank is added to the result list.
    */
  def getRanksByCount(cards: List[Card], predicate: Int => Boolean): List[CardRank] = {

    // Counts all rank appearances in this.cards.
    def countRank(r: CardRank): Int = cards.count(_.rank == r)

    // Gets all ranks of which the number of appearances satisfies the predicate.
    CardRank.ranks.filter(rank => predicate(countRank(rank)))
  }


  /** Returns true if the first hand makes a stronger high card than the second one.
    *
    * @param cards1 the first list of cards
    * @param cards2 the second list of cards
    * @return positive Int if cards1 are better than cards2, 0 if both lists are equally strong, negative Int if cards2 make better kickers than cards1
    *
    * Sorts both cards lists in descending order and compares their elements one by one until a pair of differently ranked cards is found.
    */
  def compareKickers(cards1: List[Card], cards2: List[Card]): Int = {

    val sortedCards1 = cards1.sortWith(_.rank > _.rank)
    val sortedCards2 = cards2.sortWith(_.rank > _.rank)

    for ((a, b) <- sortedCards1 zip sortedCards2)
      if (a.rank != b.rank) return { if (a.rank > b.rank) 1 else -1 }

    0
  }


  def areSingleSuited(cards: List[Card]): Boolean = {
    val suits: List[Suit] = cards.map(_.suit)
    suits.forall(_ == suits.head)
  }

  def areConsecutive(cards: List[Card]): Boolean = {
    val sortedRanks: List[CardRank] = cards.map(_.rank).sortWith(_ < _)

    if (sortedRanks == List(Two, Three, Four, Five, Ace))
      return true

    (sortedRanks zip sortedRanks.drop(1)).forall { case (a, b) => a.strength + 1 == b.strength }
  }
}
