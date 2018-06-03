package shellPoker.core.pokerHands

import org.scalatest.FunSuite
import shellPoker.core.cards._

/** Tests for Pair class. */
class PairTest extends FunSuite {

  test("Certain cards should make at least pair hand"){

    val l1 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Ace, Clubs),
      Card(Two, Diamonds),
      Card(Three, Spades)
    )

    assert(Pair.isMadeUpOf(l1))

    val l2 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(King, Clubs),
      Card(Ten, Diamonds),
      Card(Jack, Spades)
    )

    assert(Pair.isMadeUpOf(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(King, Hearts),
      Card(Three, Spades)
    )

    assert(Pair.isMadeUpOf(l3))

    val l4 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(Queen, Hearts),
      Card(Three, Spades)
    )

    assert(Pair.isMadeUpOf(l4))

    val l5 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Clubs),
      Card(King, Hearts),
      Card(King, Spades)
    )

    assert(Pair.isMadeUpOf(l5))
  }

  test("Certain cards should NOT make a pair hand"){
     val l1 = List(
      Card(Queen, Diamonds),
      Card(Jack, Spades),
      Card(Ace, Clubs),
      Card(King, Hearts),
      Card(Three, Spades)
    )

    assert(!Pair.isMadeUpOf(l1))
  }

  test("Every attempt to make Pair from cards that make better hand should throw InvalidPokerHandException.") {

    val l1 = List(
      Card(Queen, Diamonds),
      Card(King, Spades),
      Card(King, Clubs),
      Card(Two, Diamonds),
      Card(Two, Spades)
    )

    assertThrows[InvalidPokerHandException](Pair(l1))


    val l2 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Ace, Clubs),
      Card(Queen, Diamonds),
      Card(Jack, Spades)
    )

    assertThrows[InvalidPokerHandException](Pair(l2))

    val l3 = List(
      Card(Queen, Diamonds),
      Card(King, Diamonds),
      Card(Ace, Diamonds),
      Card(Two, Diamonds),
      Card(Three, Diamonds)
    )

    assertThrows[InvalidPokerHandException](Pair(l3))

    val l4 = List(
      Card(Queen, Diamonds),
      Card(Queen, Spades),
      Card(Queen, Hearts),
      Card(Queen, Clubs),
      Card(Three, Diamonds)
    )

    assertThrows[InvalidPokerHandException](Pair(l4))

    val l5 = List(
      Card(Seven, Diamonds),
      Card(Six, Spades),
      Card(Five, Diamonds),
      Card(Four, Diamonds),
      Card(Three, Diamonds)
    )

    assertThrows[InvalidPokerHandException](Pair(l5))
  }

}