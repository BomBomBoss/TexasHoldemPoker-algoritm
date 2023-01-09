package com.evolution.bootcamp.assignment.poker;

import java.util.*;
import java.util.stream.Collectors;
/** Texas Holdem Poker implementation */
class Solver
{

    static List<Character> characterList;

    static
    {
        characterList = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'));
    }

    static String process(String line)
    {
      if (line == null || line.isEmpty())
      {
        throw new RuntimeException("no input data");
      }

      String[] blocks = line.split(" ");

      if (blocks.length < 3)
      {
        throw new RuntimeException("there is no players");
      }

        List<Player> people = new ArrayList<>();

        Player player = new Player().builder().gameTitle(blocks[0])
                .boardCombination(blocks[1]).convertedBoardCombination(convert(blocks[1]))
                .build();

        for (int i = 2; i < blocks.length; i++)
        {
            people.add(Player.builder(player)
                    .combination(blocks[i])
                    .convertedCombination(convert(blocks[i])).build());
        }
        return winner(people);
    }

    // Converting card's letter values to numbers using array indexes
    private static String[] convert(String combination)
    {
        String[] separateCards = new String[combination.length() / 2];

        for (int i = 0; i < separateCards.length; i++)
        {
            separateCards[i] = String.valueOf(combination.charAt(2 * i + 1))
                    .concat(String.valueOf(characterList.indexOf(combination.charAt(2 * i))));
        }
        return separateCards;
    }

    private static String winner(List<Player> players)
    {
        for (Player player : players)
        {
            player.combineAndSortCards();
            masterCombinationCheck(player);
        }
        findWinner(players);
        return outputResult(players);
    }

    // Sorting list of players by card combination strength
    private static void findWinner(List<Player> players)
    {
        players.sort((player1, player2) ->
        {
            HandValue value1 = (HandValue) player1.getValue();
            HandValue value2 = (HandValue) player2.getValue();
            return value2.getStrength().compareTo(value1.getStrength());
        });
        sortByHighestCard(players);
    }

    // input chain method for card combination check. Card separates to numbers and suits for convenience purpose
    private static void masterCombinationCheck(Player player)
    {
        List<Integer> numbers = player.getBoardAndPlayerCards().stream().map(x -> Integer.parseInt(x.replaceAll("\\D", ""))).collect(Collectors.toList());
        List<String> cardSuits = player.getBoardAndPlayerCards().stream().map(x -> x.replaceAll("\\d", "")).sorted().collect(Collectors.toList());

        straightFlushCheck(player, numbers, cardSuits);
    }

    private static boolean isStraight(List<Integer> numbers)
    {
        for (int y = 0; y < 3; y++)
        {
            int counter = 0;
            for (int i = 0; i < 4; i++)
            {
                if (numbers.get(y + i) - numbers.get(y + i + 1) == 1)
                {
                    counter++;
                    if (i == 3 && counter == 4)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isFlush(List<String> suits)
    {
        Set<String> set;
        Collections.sort(suits);
        for (int i = 0; i < 3; i++)
        {
            set = new HashSet<>(suits.subList(i, 5 + i));
            if (set.size() == 1) return true;
        }
        return false;
    }

    private static void flushCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        if (isFlush(suits)) player.setValue(HandValue.FLUSH);
        else straightCheck(player, numbers);
    }

    private static void straightCheck(Player player, List<Integer> numbers)
    {
        if (isStraight(numbers)) player.setValue(HandValue.STRAIGHT);
        else threeOfAKindCheck(player, numbers);
    }

    private static void straightFlushCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        if (isStraight(numbers) && isFlush(suits))
        {
            player.setValue(HandValue.STRAIGHT_FLUSH);
        } else
        {
            fourOfKindCheck(player, numbers, suits);
        }
    }

    private static void fourOfKindCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        Set<Integer> set;
        for (int i = 0; i < 4; i++)
        {
            set = new HashSet<>(numbers.subList(i, 4 + i));
            if (set.size() == 1)
            {
                player.setValue(HandValue.FOUR_OF_A_KIND);
                break;
            }
        }
        if (player.getValue() == null)
        {
            fullHouseCheck(player, numbers, suits);
        }
    }

    private static void fullHouseCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() == 3) player.setValue(HandValue.FULL_HOUSE);
        else if (set.size() == 4)
        {
            for (int i = 0; i < 5; i++)
            {
                set = new HashSet<>(numbers.subList(i, 3 + i));
                if (set.size() == 1) player.setValue(HandValue.FULL_HOUSE);
            }

        } else flushCheck(player, numbers, suits);
    }

    private static void threeOfAKindCheck(Player player, List<Integer> numbers)
    {
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() == 5)
        {
            for (int i = 0; i < 5; i++)
            {
                set = new HashSet<>(numbers.subList(i, 3 + i));
                if (set.size() == 1) player.setValue(HandValue.THREE_OF_A_KIND);
            }
        }
        if (player.getValue() == null) twoPairsCheck(player, numbers);
    }

    private static void twoPairsCheck(Player player, List<Integer> numbers)
    {
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() == 4 || set.size() == 5) player.setValue(HandValue.TWO_PAIRS);
        else pairCheck(player, numbers);
    }

    private static void pairCheck(Player player, List<Integer> numbers)
    {
        Set<Integer> set = new HashSet<>(numbers);
        if (set.size() != numbers.size()) player.setValue(HandValue.PAIR);
        else player.setValue(HandValue.HIGH_CARD);
    }

    // method for sorting players when combination values are equal
    private static void sortByHighestCard(List<Player> players)
    {
        Set<HandValue> setOfStrength = players.stream().map(Player::getValue).map(x -> (HandValue) x).collect(Collectors.toSet());
        if (setOfStrength.size() != players.size())
        {
            for (int i = 0; i < players.size() - 1; )
            {
                Player player1 = players.get(i);
                Player player2 = players.get(i + 1);

                if (player1.getValue().equals(player2.getValue()))
                {
                    if(player1.getValue().equals(HandValue.FULL_HOUSE))
                    {
                       i = playersFullHouseHighCardFinder(players,i,player1,player2);
                    }
                    i = playersHighCardFinder(players, i, player1, player2);

                } else i++;
            }
        }
    }

    private static int playersHighCardFinder(List<Player> players, int index, Player player1, Player player2)
    {
        List<Integer> playerOneCards = Arrays.stream(player1.getConvertedCombination()).map(x -> Integer.parseInt(x.replaceAll("\\D", ""))).collect(Collectors.toList());
        List<Integer> playerTwoCards = Arrays.stream(player2.getConvertedCombination()).map(x -> Integer.parseInt(x.replaceAll("\\D", ""))).collect(Collectors.toList());
        playerOneCards.sort(Collections.reverseOrder());
        playerTwoCards.sort(Collections.reverseOrder());

        int x = playerTwoCards.get(0).compareTo(playerOneCards.get(0));
        if (x < 0)
        {
            Collections.swap(players, index, index + 1);
            index = 0;
        } else if (x == 0)
        {
            x = playerTwoCards.get(1).compareTo(playerOneCards.get(1));
            if (x < 0)
            {
                Collections.swap(players, index, index + 1);
                index = 0;
                // sort alphabetically players combination
            } else if (x == 0 && (player1.getCombination().compareTo(player2.getCombination()) > 0))
            {
                Collections.swap(players, index, index + 1);
                index++;
            } else index++;
        } else index++;
        return index;
    }

    // method for printing result corresponding to the requirements of task
    private static String outputResult(List<Player> players)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < players.size() - 1; i++)
        {
            Player player1 = players.get(i);
            Player player2 = players.get(i + 1);
            result.append(players.get(i).getCombination());

            if (player1.getValue().equals(player2.getValue()))
            {
                 if (sumOfPlayerCards(player1.getConvertedCombination()) == sumOfPlayerCards(player2.getConvertedCombination()))
                    result.append("=");
                else result.append(" ");
            } else result.append(" ");
            if (i == players.size() - 2) result.append(players.get(i + 1).getCombination());
        }
        return result.toString();
    }

    // checking if players both high cards are equal when combination values were already equal
    private static int sumOfPlayerCards(String[] convertedCombination)
    {
        int a = Integer.parseInt(convertedCombination[0].replaceAll("\\D", ""));
        int b = Integer.parseInt(convertedCombination[1].replaceAll("\\D", ""));
        return a + b;
    }

    // method to find which full house combination is stronger
    private static int playersFullHouseHighCardFinder(List<Player> players, int index, Player player1, Player player2)
    {
        retrieveFullHouseCombination(player1);
        retrieveFullHouseCombination(player2);

        if (player1.getFullHouse().getThreeCardNumber() > player2.getFullHouse().getThreeCardNumber())
        {
            Collections.swap(players, index, index + 1);
            index = 0;
        } else if (player1.getFullHouse().getThreeCardNumber() == player2.getFullHouse().getThreeCardNumber())
        {
            if (player1.getFullHouse().getTwoCardNumber() > player2.getFullHouse().getTwoCardNumber())
            {
                Collections.swap(players, index, index + 1);
                index = 0;
            }
        } else index++;
        return index;


    }

    // retrieve 2 numbers from full house combination
    private static void retrieveFullHouseCombination(Player player)
    {
        player.setFullHouse(new FullHouse());
        List<Integer> playerNumbers = player.getBoardAndPlayerCards().stream().map(x->Integer.parseInt(x.replaceAll("\\D", ""))).collect(Collectors.toList());
        Set<Integer> playerUniqueNumbers = new HashSet<>(playerNumbers);


        int threeCardNumber = 0;
        int twoCardNumber = 0;
        for(Integer number : playerUniqueNumbers)
        {
            long match = playerNumbers.stream().filter(number::equals).count();
            if (match == 3)
            {
                if (number > threeCardNumber && twoCardNumber == 0)
                {
                    twoCardNumber = threeCardNumber;
                    threeCardNumber = number;
                }
                else threeCardNumber = threeCardNumber < number ? number : threeCardNumber;
            }
            else if (match == 2)
            {
                twoCardNumber = twoCardNumber < number ? number : twoCardNumber;
            }
            player.getFullHouse().setThreeCardNumber(threeCardNumber);
            player.getFullHouse().setTwoCardNumber(twoCardNumber);
        }
    }

}


