package com.evolution.bootcamp.assignment.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Player
{
    private String combination;
    private String[] sortedCombination;

    private Enum<HandValue> value;

    private String gameTitle;

    private String boardCombination;
    private String[] boardCombinationSorted;
    private List<String> boardAndPlayerCards;

    private boolean isEquals = false;
    public String getGameTitle()
    {
        return gameTitle;
    }

    public boolean isEquals()
    {
        return isEquals;
    }

    public void setEquals(boolean equals)
    {
        isEquals = equals;
    }

    public List<String> getBoardAndPlayerCards()
    {
        return boardAndPlayerCards;
    }

    public void setBoardAndPlayerCards(List<String> boardAndPlayerCards)
    {
        this.boardAndPlayerCards = boardAndPlayerCards;
    }

    public void setGameTitle(String gameTitle)
    {
        this.gameTitle = gameTitle;
    }

    public String getBoardCombination()
    {
        return boardCombination;
    }

    public void setBoardCombination(String boardCombination)
    {
        this.boardCombination = boardCombination;
    }

    public String[] getBoardCombinationSorted()
    {
        return boardCombinationSorted;
    }

    public void setBoardCombinationSorted(String[] boardCombinationSorted)
    {
        this.boardCombinationSorted = boardCombinationSorted;
    }

    public Player()
    {
    }

    private Player(Builder builder)
    {
        setCombination(builder.combination);
        setSortedCombination(builder.sortedCombination);
        setValue(builder.value);
        setGameTitle(builder.gameTitle);
        setBoardCombination(builder.boardCombination);
        setBoardCombinationSorted(builder.boardCombinationSorted);
        setBoardAndPlayerCards(builder.boardAndPlayerCards);
    }

    public static Builder builder(Player copy)
    {
        Builder builder = new Builder();
        builder.combination = copy.getCombination();
        builder.sortedCombination = copy.getSortedCombination();
        builder.value = copy.getValue();
        builder.gameTitle = copy.getGameTitle();
        builder.boardCombination = copy.getBoardCombination();
        builder.boardCombinationSorted = copy.getBoardCombinationSorted();
        builder.boardAndPlayerCards = copy.getBoardAndPlayerCards();
        return builder;
    }

    public String getCombination()
    {
        return combination;
    }

    public void setCombination(String combination)
    {
        this.combination = combination;
    }

    public String[] getSortedCombination()
    {
        return sortedCombination;
    }

    public void setSortedCombination(String[] sortedCombination)
    {
        this.sortedCombination = sortedCombination;
    }

    public Enum<HandValue> getValue()
    {
        return value;
    }

    public void setValue(Enum<HandValue> value)
    {
        this.value = value;
    }

    protected void combineAndSortCards()
    {
        boardAndPlayerCards = new ArrayList<>();
        boardAndPlayerCards.addAll(List.of(boardCombinationSorted));
        boardAndPlayerCards.addAll(List.of(sortedCombination));

        boardAndPlayerCards.sort((o1, o2) ->
        {
            Integer x = Integer.parseInt(o1.replaceAll("\\D", ""));
            Integer y = Integer.parseInt(o2.replaceAll("\\D", ""));
            return y.compareTo(x);
        });
        this.setBoardAndPlayerCards(boardAndPlayerCards);
    }

    @Override
    public String toString()
    {
        return "Player{" +
                "combination='" + combination + '\'' +
                ", sortedCombination=" + Arrays.toString(sortedCombination) +
                ", value=" + value +
                ", gameTitle='" + gameTitle + '\'' +
                ", boardCombination='" + boardCombination + '\'' +
                ", boardCombinationSorted=" + Arrays.toString(boardCombinationSorted) +
                ", boardAndPlayerCards=" + boardAndPlayerCards +
                '}';
    }

    public Builder builder()
    {
        return new Builder();
    }
    public static final class Builder
    {
        private String combination;
        private String[] sortedCombination;
        private Enum<HandValue> value;
        private String gameTitle;
        private String boardCombination;
        private String[] boardCombinationSorted;

        private List<String> boardAndPlayerCards;


        public Builder combination(String val)
        {
            combination = val;
            return this;
        }

        public Builder sortedCombination(String[] val)
        {
            sortedCombination = val;
            return this;
        }

        public Builder value(Enum<HandValue> val)
        {
            value = val;
            return this;
        }

        public Builder gameTitle(String val)
        {
            gameTitle = val;
            return this;
        }

        public Builder boardCombination(String val)
        {
            boardCombination = val;
            return this;
        }

        public Builder boardCombinationSorted(String[] val)
        {
            boardCombinationSorted = val;
            return this;
        }

        public Builder boardAndPlayerCards(List<String> val)
        {
            boardAndPlayerCards = val;
            return this;
        }

        public Player build()
        {
            return new Player(this);
        }
    }
}
