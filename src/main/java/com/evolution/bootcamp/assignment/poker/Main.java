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
            System.out.println(players);
        }

    }

    private void masterCombinationCheck(Player player)
    {
        List<Integer> numbers = player.getBoardAndPlayerCards().stream().map(x -> Integer.parseInt(x.replaceAll("\\D", ""))).collect(Collectors.toList());
        List<String> cardSuits = player.getBoardAndPlayerCards().stream().map(x->x.replaceAll("\\d","")).collect(Collectors.toList());

        straightFlushCheck(player, numbers, cardSuits);

    }

    private boolean isStraight(List<Integer> numbers)
    {
        boolean straight = false;

        for (int i = 0; i < numbers.size() - 1; i++)
        {
            if (numbers.get(i) - numbers.get(i + 1) == 1)
            {
                if (i == numbers.size() - 1)
                {
                    straight = true;
                }
            } else break;
        }
        return straight;
    }

    private boolean isFlush(List<String> suits)
    {
        Set<String> cardSuit = new HashSet<>(suits);
        return cardSuit.size()<3;
    }

    private void straightFlushCheck(Player player, List<Integer> numbers, List<String> suits)
    {
        boolean straight = isStraight(numbers);
        boolean flush = isFlush(suits);

        if(straight || flush)
        {
            if(straight && !flush)
            {
                player.setValue(HandValue.STRAIGHT);
            }
            else if (!straight)
            {
                player.setValue(HandValue.FLUSH);
            }
            player.setValue(HandValue.STRAIGHT_FLUSH);
        }
    }

    private void fourOfKind(Player player)
    {

    }




}
