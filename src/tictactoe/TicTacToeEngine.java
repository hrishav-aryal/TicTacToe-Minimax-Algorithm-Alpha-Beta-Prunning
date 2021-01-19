package tictactoe;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeEngine {
	
	
	
	public void start() {
		
		Integer[][] board = new Integer[3][3];
		
		boolean test = true;
		while(test) {
			
			ArrayList<Pair> validMoves = getAllValidMoveSquares(board);
			
			int maxScore = -1000000;
			Pair maxScorePair = null;
			
			Integer[][] tempBoard = new Integer[3][3];
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					tempBoard[i][j] = board[i][j];
				}
			}
			
			Integer[][] tBoard = new Integer[3][3];
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					tBoard[i][j] = board[i][j];
				}
			}
			
			int alpha = -1000;
			int beta = 1000;
			for(Pair p: validMoves) {
				
				tBoard[p.getX()][p.getY()] = 0; 
				
				int eval = minimax(tBoard, alpha, beta,  false);
				
				if(maxScore < eval) {
					maxScore = eval;
					maxScorePair = p;
				}
				
				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						tBoard[i][j] = board[i][j];
					}
				}
				
			}
			
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					board[i][j] = tempBoard[i][j];
				}
			}
			
			board[maxScorePair.getX()][maxScorePair.getY()] = 0;
			System.out.println("New Board: ");
			printBoard(board);
			
			Scanner sc = new Scanner(System.in);
			System.out.println();
			System.out.println("Enter your move: ");
			String str = sc.next();
			System.out.println();
			
			String[] s = str.split(",");
			board[Integer.parseInt(s[0])][Integer.parseInt(s[1])] = 1;
		}
		
		
		
	}

	private void printBoard(Integer[][] board2) {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(board2[i][j] == null) {
					System.out.print(" - ");
				}else {
					System.out.print(" "+ board2[i][j] +" ");
				}
			}
			System.out.println();
		}
		
	}

	private int minimax(Integer[][] board, int alpha, int beta, boolean kturn) {
		
		if(isGameOver(board)) {
			return -1;
		}else if(isGameDrawn(board)) {
			return 0;
		}else if(isGameWon(board)) {
			return 1;
		}
		
		ArrayList<Pair> validMoves = getAllValidMoveSquares(board);
		if(kturn) {
			int maxEval = -1000000;
			
			Integer[][] xBoard = new Integer[3][3];
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					xBoard[i][j] = board[i][j];
				}
			}
			
			for(Pair pa: validMoves) {
				
				xBoard[pa.getX()][pa.getY()] = 0;
				
				int eval = minimax(xBoard, alpha, beta, false);
				maxEval = Math.max(eval, maxEval);
				
				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						xBoard[i][j] = board[i][j];
					}
				}
				
				alpha = Math.max(eval, alpha);
				if(beta<=alpha) {
					break;
				}
				
			}
			return maxEval;
			
		}else {
			
			Integer[][] xBoard = new Integer[3][3];
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					xBoard[i][j] = board[i][j];
				}
			}
			
			int minEval = 1000000;
			for(Pair pa: validMoves) {
				
				xBoard[pa.getX()][pa.getY()] = 1;
				
				int eval = minimax(xBoard, alpha, beta, true);
				minEval  = Math.min(eval, minEval );
				
				for(int i=0; i<3; i++) {
					for(int j=0; j<3; j++) {
						xBoard[i][j] = board[i][j];
					}
				}
				
				beta = Math.min(eval, beta);
				if(beta<=alpha) {
					break;
				}
				
			}
			return minEval;
			
		}
		
	}

	
	private boolean isGameWon(Integer[][] board2) {
		
		//check all rows
		for(int i=0; i<3; i++) {
			if(board2[i][0]!=null && board2[i][1]!=null && board2[i][2]!=null) {
				if(board2[i][0] == 0 && board2[i][1] == 0 && board2[i][2] == 0) {
					return true;
				}	
			}
		}
		
		//check all columns
		for(int i=0; i<3; i++) {
			if(board2[0][i]!=null && board2[1][i]!=null && board2[2][i]!=null) {
				if(board2[0][i] == 0 && board2[1][i] == 0 && board2[2][i] == 0) {
					return true;
				}
			}
		}
		
		//check all diagonals
		if(board2[0][0]!=null && board2[1][1]!=null && board2[2][2]!=null) {
			if(board2[0][0] == 0 && board2[1][1] == 0 && board2[2][2] == 0) {
				return true;
			}
		}
		if(board2[0][2]!=null && board2[1][1]!=null && board2[2][0]!=null) {
			if(board2[0][2] == 0 && board2[1][1] == 0 && board2[2][0] == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isGameDrawn(Integer[][] board2) {
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(board2[i][j] == null) {
					return false;
				}
			}
		}
		
		if(!isGameWon(board2) && !isGameOver(board2)) {
			return true;
		}
		
		return false;
	}

	
	private boolean isGameOver(Integer[][] board2) {
	
		//check all rows
		for(int i=0; i<3; i++) {
			if(board2[i][0]!=null && board2[i][1]!=null && board2[i][2]!=null) {
				if(board2[i][0] == 1 && board2[i][1] == 1 && board2[i][2] == 1) {
					return true;
				}	
			}
		}
		
		//check all columns
		for(int i=0; i<3; i++) {
			if(board2[0][i]!=null && board2[1][i]!=null && board2[2][i]!=null) {
				if(board2[0][i] == 1 && board2[1][i] == 1 && board2[2][i] == 1) {
					return true;
				}
			}
		}
		
		//check all diagonals
		if(board2[0][0]!=null && board2[1][1]!=null && board2[2][2]!=null) {
			if(board2[0][0] == 1 && board2[1][1] == 1 && board2[2][2] == 1) {
				return true;
			}
		}
		
		if(board2[0][2]!=null && board2[1][1]!=null && board2[2][0]!=null) {
			if(board2[0][2] == 1 && board2[1][1] == 1 && board2[2][0] == 1) {
				return true;
			}
		}
		
		return false;
	}

	
	private ArrayList<Pair> getAllValidMoveSquares(Integer[][] board) {
		ArrayList<Pair> moves = new ArrayList();		
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				
				if(board[i][j] == null) {
					moves.add(new Pair(i,j));
				}
					
			}
		}
		
		return moves;
	}

}
