import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {

    @Test
    void notFinished() {
        assertEquals(-1, Solution.isSolved(new int[][] {
            {0, 0, 1},
            {0, 1, 2},
            {2, 1, 0}
        }), "[0, 0, 1]\n[0, 1, 2]\n[2, 1, 0]");
    }
    @Test
    void winnerX() {
        assertEquals(1, Solution.isSolved(new int[][] {
            {1, 1, 1},
            {0, 2, 2},
            {0, 0, 0}
        }), "[1, 1, 1]\n[0, 2, 2]\n[0, 0, 0]");
    }
    @Test
    void winnerO() {
        assertEquals(2, Solution.isSolved(new int[][] {
            {1, 1, 2},
            {0, 2, 0},
            {2, 1, 1}
        }), "[1, 1, 2]\n[0, 2, 0]\n[2, 1, 1]");
    }
    @Test
    void draw() {
        assertEquals(0, Solution.isSolved(new int[][] {
            {1, 2, 1},
            {1, 1, 2},
            {2, 1, 2}
        }), "[1, 2, 1]\n[1, 1, 2]\n[2, 1, 2]");
    }
  
    @Test
    void fixedTests() {
        assertEquals(1, Solution.isSolved(new int[][] {
            { 1, 1, 1 },
            { 0, 2, 2 },
            { 0, 0, 0 }
        }), "[1, 1, 1]\n[0, 2, 2]\n[0, 0, 0]");
        assertEquals(1, Solution.isSolved(new int[][] {
            { 1, 2, 0 },
            { 0, 1, 2 },
            { 0, 0, 1 }
        }), "[1, 2, 0]\n[0, 1, 2]\n[0, 0, 1]");
        assertEquals(2, Solution.isSolved(new int[][] {
            { 2, 1, 1 },
            { 0, 1, 1 },
            { 2, 2, 2 }
        }), "[2, 1, 1]\n[0, 1, 1]\n[2, 2, 2]");
        assertEquals(2, Solution.isSolved(new int[][] {
            { 2, 2, 2 },
            { 0, 1, 1 },
            { 1, 0, 0 }
        }), "[2, 2, 2]\n[0, 1, 1]\n[1, 0, 0]");
        assertEquals(0, Solution.isSolved(new int[][] {
            { 2, 1, 2 },
            { 2, 1, 1 },
            { 1, 2, 1 }
        }), "[2, 1, 2]\n[2, 1, 1]\n[1, 2, 1]");
        assertEquals(0, Solution.isSolved(new int[][] {
            { 1, 2, 1 },
            { 1, 1, 2 },
            { 2, 1, 2 }
        }), "[1, 2, 1]\n[1, 1, 2]\n[2, 1, 2]");
        assertEquals(-1, Solution.isSolved(new int[][] {
            { 2, 0, 2 },
            { 2, 1, 1 },
            { 1, 2, 1 }
        }), "[2, 0, 2]\n[2, 1, 1]\n[1, 2, 1]");
        assertEquals(-1, Solution.isSolved(new int[][] {
            { 0, 0, 2 },
            { 0, 0, 0 },
            { 1, 0, 1 }
        }), "[0, 0, 2]\n[0, 0, 0]\n[1, 0, 1]");
        assertEquals(-1, Solution.isSolved(new int[][] {
            { 1, 2, 1 },
            { 1, 1, 2 },
            { 2, 2, 0 }
        }), "[1, 2, 1]\n[1, 1, 2]\n[2, 2, 0]");
        assertEquals(-1, Solution.isSolved(new int[][] {
            { 0, 2, 2 },
            { 2, 1, 1 },
            { 0, 0, 1 }
        }), "[0, 2, 2]\n[2, 1, 1]\n[0, 0, 1]");
        assertEquals(-1, Solution.isSolved(new int[][] {
            { 0, 1, 1 },
            { 2, 0, 2 },
            { 2, 1, 0 }
        }), "[0, 1, 1]\n[2, 0, 2]\n[2, 1, 0]");
    }

    @Test
    void testRandomBoards() {
        var random = ThreadLocalRandom.current();
        for (int i = 0; i < 10_000; i++) {
            int[][] board = new int[3][3];
            int turns = 9 - random.nextInt(4) * random.nextInt(4);
            boolean x = random.nextBoolean();
            int expectedResult = -1;
            int turn = 0;
            while (expectedResult < 0 && turn < turns) {
                int row = random.nextInt(3);
                int col = random.nextInt(3);
                if (board[row][col] > 0)
                    continue;
                int val = x ? 1 : 2;
                board[row][col] = val;
                if (board[row][0] == val && board[row][1] == val && board[row][2] == val)
                    expectedResult = val;
                else if (board[0][col] == val && board[1][col] == val && board[2][col] == val)
                    expectedResult = val;
                else if (board[0][2] == val && board[1][1] == val && board[2][0] == val)
                    expectedResult = val;
                else if (board[0][0] == val && board[1][1] == val && board[2][2] == val)
                    expectedResult = val;
                x = !x;
                turn++;
            }
            if (expectedResult < 0 && turns == 9)
                expectedResult = 0;
            String msg = Arrays.stream(board).map(r -> Arrays.toString(r)).collect(Collectors.joining("\n"));
            assertEquals(expectedResult, Solution.isSolved(board), msg);
        }
    }

}



public class Solution {

    public static int isSolved(int[][] board) {
      
      
      return win(board);
      


      
      }
  static int win(int board[][]){
      for(int i = 0; i<board.length; i++){
        for(int j = 0;j<board[i].length; j++){
          if(i == 0 && board[i][j] != 0){
            if(equal(board[i][j], board[i+1][j], board[i+2][j])){
              return board[i][j];
            }
          }if(j == 0 && board[i][j] != 0){
            if(equal(board[i][j], board[i][j+1], board[i][j+2])){
              return board[i][j];
            }
          }if(i == 0 && board[i][j] != 0 && j == 0){
            if(equal(board[i][j], board[i+1][j+1], board[i+2][j+2])){
              return board[i][j];
            }
          }if(i == 0 && board[i][j] != 0 && j == board.length - 1){
            if(equal(board[i][j], board[i+1][j-1], board[i+2][j-2])){
              return board[i][j];
            }
          }

        } 
      }
      if(draw(board)){
          return 0;
        }
    return -1;
  }

static boolean draw(int[][] board){
  for(int i = 0; i < board.length; i++){
    for(int j = 0; j < board[i].length; j++){
      if(board[i][j] == 0){
        return false;
      }
    }
  }
  return true;
}
  
  static boolean equal(int a, int b, int c){
          
    return a == b && a == c;
  }
      }
