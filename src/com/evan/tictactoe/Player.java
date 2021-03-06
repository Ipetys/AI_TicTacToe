package com.evan.tictactoe;

import java.util.Scanner;

/* Abstract com.evan.TicTacToe.Player class from which human and AI will inherit */
public abstract class Player
{
	public String playerName;
	public char playerCharacter;

	public Player(String name, char character)
	{
		playerName = name;
		playerCharacter = character;
	}

	/* A default(bad) strategy that any com.evan.TicTacToe.Player can play */
	public int makeMove(GameBoard board)
	{
		char[] squares = board.getSquares();

		for (int i = 1; i < squares.length; i++)
		{
			if (squares[i] == '_')
			{
				return i;
			}
		}
		/* Default failure case, shouldn't ever happen */
		return -1;
	}
}

/* Class defining the operation of the human com.evan.TicTacToe.Player */
class HumanPlayer extends Player
{
	private Scanner keyboard = new Scanner(System.in);
	public HumanPlayer(String name, char character)
	{
		super(name, character);
	}

	/* Prompts the user for a move */
	public int makeMove(GameBoard board)
	{
		System.out.print("Please choose a square: ");
		return keyboard.nextInt();
	}
}

enum strategyType
{
	MINMAX, SIMPLE
}

/* Class defining the operation of the computer com.evan.TicTacToe.Player */
class ComputerPlayer extends Player
{
	/* Enum for the strategy type, either simple or minmax */
	private strategyType strategy;

	public ComputerPlayer(String name, char character, String strat)
	{
		super(name, character);
		strategy = strat == "minMax" ? strategyType.MINMAX : strategyType.SIMPLE;
	}

	/* AI makes move according to its strategy mode */
	public int makeMove(GameBoard board)
	{
		//return super.makeMove(board);

		if (strategy == strategyType.SIMPLE)
		{
			return followSimple(board);
		}
		else if (strategy == strategyType.MINMAX)
		{
			return followMinMax(board);
		}
		else
		{
			/* Should never get here, follow default strategy */
			return super.makeMove(board);
		}
	}

	/*
		Here is where we implement the AI of this system following the simple method that was written down during lab:
			1. Win if we can
			2. Block if the human can win
			3. Fork if possible
			4. Take the center if open
			5. If there is an open corner, take it
			7. Take an empty 'side' square if available
			8. Take any open square
	*/
	private int followSimple(GameBoard board)
	{
		int move;
		if ((move = board.findWiningSpace(playerCharacter)) != -1)
		{
			return move;
		}
		else if ((move = board.findBlockingSpace(playerCharacter)) != -1)
		{
			return move;
		}
		else if ((move = board.findForkingSpace(playerCharacter)) != -1)
		{
			return move;
		}
		else if ((move = board.findOpenCenterSpace()) != -1)
		{
			return move;
		}
		else if ((move = board.findOpenCornerSpace()) != -1)
		{
			return move;
		}
		else if ((move = board.findEmptySideSpace()) != -1)
		{
			return move;
		}
		return findEmptySquare(board);
	}

	/* Wrapper for the super's default move behavior */
	private int findEmptySquare(GameBoard board)
	{
		return super.makeMove(board);
	}

	/* Will eventually hold my implementation of the MinMax search algorithm for Tic Tac Toe */
	private int followMinMax(GameBoard board)
	{
		return -1;
	}
}