package com.evolution.bootcamp.assignment.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player
{
    private String combination;
    private String[] convertedCombination;

    private Enum<HandValue> value;

    private String gameTitle;

    private String boardCombination;
    private String[] convertedBoardCombination;

    private Straight straightOptional;

    public FullHouse getFullHouse()
    {
        return fullHouse;
    }

    public Straight getStraightOptional()
    {
        return straightOptional;
    }

    public void setStraightOptional(Straight straightOptional)
    {
        this.straightOptional = straightOptional;
    }

    public void setFullHouse(FullHouse fullHouse)
    {
        this.fullHouse = fullHouse;
    }

    private List<String> boardAndPlayerCards;

    private FullHouse fullHouse;
    public String getGameTitle()
    {
        return gameTitle;
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

    public String[] getConvertedBoardCombination()
    {
        return convertedBoardCombination;
    }

    public void setConvertedBoardCombination(String[] convertedBoardCombination)
    {
        this.convertedBoardCombination = convertedBoardCombination;
    }

    public Player()
    {
    }

    private Player(Builder builder)
    {
        setCombination(builder.combination);
        setConvertedCombination(builder.convertedCombination);
        setValue(builder.value);
        setGameTitle(builder.gameTitle);
        setBoardCombination(builder.boardCombination);
        setConvertedBoardCombination(builder.convertedBoardCombination);
        setBoardAndPlayerCards(builder.boardAndPlayerCards);
    }

    public static Builder builder(Player copy)
    {
        Builder builder = new Builder();
        builder.combination = copy.getCombination();
        builder.convertedCombination = copy.getConvertedCombination();
        builder.value = copy.getValue();
        builder.gameTitle = copy.getGameTitle();
        builder.boardCombination = copy.getBoardCombination();
        builder.convertedBoardCombination = copy.getConvertedBoardCombination();
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

    public String[] getConvertedCombination()
    {
        return convertedCombination;
    }

    public void setConvertedCombination(String[] convertedCombination)
    {
        this.convertedCombination = convertedCombination;
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
        boardAndPlayerCards.addAll(List.of(convertedBoardCombination));
        boardAndPlayerCards.addAll(List.of(convertedCombination));

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
                ", sortedCombination=" + Arrays.toString(convertedCombination) +
                ", value=" + value +
                ", gameTitle='" + gameTitle + '\'' +
                ", boardCombination='" + boardCombination + '\'' +
                ", boardCombinationSorted=" + Arrays.toString(convertedBoardCombination) +
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
        private String[] convertedCombination;
        private Enum<HandValue> value;
        private String gameTitle;
        private String boardCombination;
        private String[] convertedBoardCombination;

        private List<String> boardAndPlayerCards;


        public Builder combination(String val)
        {
            combination = val;
            return this;
        }

        public Builder convertedCombination(String[] val)
        {
            convertedCombination = val;
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

        public Builder convertedBoardCombination(String[] val)
        {
            convertedBoardCombination = val;
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
