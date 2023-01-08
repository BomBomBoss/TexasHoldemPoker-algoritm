package com.evolution.bootcamp.assignment.poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/*
* Trump (Козырь)
Diamonds (Бубы / Алмазы)
Hearts (Черви / Сердца)
Clubs (Трефы / Клубы) крести
Spades (Пики / Лопаты)
* *
j = 11
q =12
k = 13
a = 14
 */
public class Main
{
   static List<Character> characterList;
   static List<Player> people = new ArrayList<>();

   static
   {
       characterList = new ArrayList<>(Arrays.asList('0','1','2','3','4','5','6','7','8','9','T','J','Q','K','A'));
   }

    public static void main(String[] args) throws IOException
    {
        String [] example;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        while (!line.isEmpty())
        {
            example = line.split(" ");
            Player player = new Player().builder().gameTitle(example[0])
                    .boardCombination(example[1]).boardCombinationSorted(convert(example[1]))
                    .build();

            for (int i = 2; i< example.length; i ++)
            {
               people.add(Player.builder(player)
                       .combination(example[i])
                       .sortedCombination(convert(example[i])).build());
            }


            winner(people);
            line = reader.readLine();


        }
    }
    private static String[] convert(String combination)
    {
        String[] separateCards = new String[combination.length()/2];

        for (int i = 0; i < separateCards.length; i ++)
        {
            separateCards[i] = String.valueOf(combination.charAt(2*i+1))
                    .concat(String.valueOf(characterList.indexOf(combination.charAt(2*i))));
        }
        return separateCards;
    }

    private static void winner(List<Player> players)
    {
        for (Player player : players)
        {
            player.combineAndSortCards();
            masterCombinationCheck(player);
        }
        System.out.println(players);
        System.out.println(findWinner(players).stream().map(Player::getCombination).collect(Collectors.toList()));

    }

    private static List<Player> findWinner(List<Player> players)
    {
        players.sort((player1, player2) ->
        {
            HandValue value1 = (HandValue) player1.getValue();
            HandValue value2 = (HandValue) player2.getValue();
            return value2.getStrength().compareTo(value1.getStrength());
        });
        return players;
    }

    private static void masterCombinationCheck(Player player)
    {
        List<Integer> numbers = player.getBoardAndPlayerCards().stream().map(x -> Integer.parseInt(x.replaceAll("\\D", ""))).collect(Collectors.toList());
        List<String> cardSuits = player.getBoardAndPlayerCards().stream().map(x->x.replaceAll("\\d","")).collect(Collectors.toList());

        straightFlushCheck(player, numbers, cardSuits);



    }

    private static boolean isStraight(List<Integer> numbers)
    {

        for (int y = 0; y < 3; y++)
        {
            for (int i = 0; i < 5; i++)
            {
                if (numbers.get(y + i) - numbers.get(i + 1) == 1)
                {
                    if (i == 4)
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
        for(int i = 0; i<3; i++)
        {
            set = new HashSet<>(suits.subList(i, 5+i));
            if (set.size() == 1) return true;
        }
        return false;
    }

    private static void flushCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        if (isFlush(suits)) player.setValue(HandValue.FLUSH);
        else straightCheck(player,numbers);
    }

    private static void straightCheck(Player player, List<Integer> numbers)
    {
        if(isStraight(numbers)) player.setValue(HandValue.STRAIGHT);
        else threeOfAKindCheck(player, numbers);


    }

    private static void straightFlushCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        if(isStraight(numbers) && isFlush(suits))
        {
            player.setValue(HandValue.STRAIGHT_FLUSH);
        }
        else
        {
            fourOfKind(player, numbers, suits);
        }
    }

    private static void fourOfKind(Player player, List<Integer> numbers, List<String> suits)
    {
            Set<Integer> set;
            for(int i = 0; i<4; i++)
            {
                set = new HashSet<>(numbers.subList(i, 4+i));
                if (set.size() == 1)
                {
                    player.setValue(HandValue.FOUR_OF_A_KIND);
                    break;
                }
            }
            if (player.getValue()==null)
            {
                fullHouseCheck(player,numbers, suits);
            }

    }

    private static void fullHouseCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        Set<Integer> set = new HashSet<>(numbers);
        if(set.size()==3) player.setValue(HandValue.FULL_HOUSE);
        else if (set.size()==4)
        {
            for(int i = 0; i<5; i++)
            {
                set = new HashSet<>(numbers.subList(i, 3+i));
                if (set.size() == 1) player.setValue(HandValue.FULL_HOUSE);
            }

        } else flushCheck(player,numbers,suits);

    }

    private static void threeOfAKindCheck(Player player, List<Integer> numbers)
    {
        Set<Integer> set = new HashSet<>(numbers);
            if(set.size()==5)
                for(int i = 0; i<5; i++)
                {
                    set = new HashSet<>(numbers.subList(i, 3+i));
                    if (set.size() == 1) player.setValue(HandValue.THREE_OF_A_KIND);
                }
            else twoPairsCheck(player,numbers);

    }

    private static void twoPairsCheck(Player player, List<Integer> numbers)
    {
        Set<Integer> set = new HashSet<>(numbers);
        if(set.size()==4 || set.size()==5) player.setValue(HandValue.TWO_PAIRS);
        else pairCheck(player, numbers);

    }

    // 11 22 33 4   11 22 345
    private static void pairCheck(Player player, List<Integer> numbers)
    {
        Set<Integer> set = new HashSet<>(numbers);
        if(set.size()!= numbers.size()) player.setValue(HandValue.PAIR);
        else player.setValue(HandValue.HIGH_CARD);

    }




}
