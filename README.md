## Implementation

In this projects developer implemented Texas Holdem Poker game.
Main class with all game logic located in Solver.class. 
Additionaly created classes:
Player.class with player cards, board cards, combined player + board cards fields, card combination and others
HandValue enum class with all cards combination for futher sorting and finding game's winner
FullHouse.class, Straight.Class and Strength enum class (exclusively for Straight.class) for comparing these combinations with their individual rules:

- "When comparing Full House-s, the Three of a kind rank comparison is more important than the Pair rank comparison, for example, QQQ88 > 999KK, KKK77 > QQQJJ and KKK77 > KKK66"
- "When comparing Straight-s, the A2345 Straight is the weakest one and the TJQKA one the strongest one, for example, 23456 > A2345 and TJQKA > 9TJQK"

Algorith for finding winner represents hierarchy chain checking from strongest card combination to weakest one.
When player main combination are equals additional high card checking methods implemented. For FullHouse and Straight additional checking methods are more complex depending on game requirement
For output there is certaing method who prints result and checks if player combinations + player two cards are equals and prints "=" according to task requirement

## RUN

Application can be checked and run in TexasHoldemSolverTest.java.
Additional custom unit tests were added

## Obvious weaknesses

Simple input validation for empty string and min array length implemented however there are plenty ways to break application inputing initial string which not corresponds to game rules
Lot of complex algorithm which aren't user friendly for reading. Comments in code can help little bit to a reviewer
Can be simplified with functional programming paradigms.
