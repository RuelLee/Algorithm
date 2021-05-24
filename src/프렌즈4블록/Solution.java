package 프렌즈4블록;

public class Solution {
    static int[] dr = {0, 1, 1};
    static int[] dc = {1, 0, 1};

    public static void main(String[] args) {
        // 어려운 문제는 아니나, 시뮬레이션 문제는 헷갈리지 않고, 체계적으로 구현해내는가가 중요한 것 같다.
        // 2 * 2 모양으로 같은 글자들이 모여있는 위치를 찾아내고, - 으로 값을 바꾸는 findSquare 메소드
        // - 값일 경우, 위쪽 블록에 -가 아닌 값이 있을 경우 값을 찾아 내리는 doFall 메소드.
        // 위 두 메소드를 board 에 변화가 생기지 않을 때까지 반복.
        // 마지막에 board 에서 사라진 블록의 수를 세도 가능하나, 사라진 블록의 갯수를 지속적으로 더해 계산한다.
        int m = 4;
        int n = 5;
        String[] board = {"CCBDE", "AAADE", "AAABF", "CCBBF"};

        char[][] gameBoard = new char[m][];
        for (int i = 0; i < gameBoard.length; i++)
            gameBoard[i] = board[i].toCharArray();

        int count = 0;
        boolean diff = false;
        do {
            count += findSquare(gameBoard);
            diff = doFall(gameBoard);
        } while (diff);
        System.out.println(count);
    }

    static int findSquare(char[][] gameBoard) {
        boolean[][] check = new boolean[gameBoard.length][gameBoard[0].length];
        int count = 0;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                char cur = gameBoard[i][j];
                if (cur == '-')
                    continue;

                boolean same = true;
                for (int d = 0; d < 3; d++) {
                    int nextR = i + dr[d];
                    int nextC = j + dc[d];

                    if (checkArea(nextR, nextC, gameBoard)) {
                        if (gameBoard[nextR][nextC] != cur) {
                            same = false;
                            break;
                        }
                    } else {
                        same = false;
                        break;
                    }
                }
                if (same) {
                    check[i][j] = true;
                    for (int d = 0; d < 3; d++) {
                        int nextR = i + dr[d];
                        int nextC = j + dc[d];
                        if (!check[nextR][nextC]) {
                            check[nextR][nextC] = true;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (check[i][j]) {
                    gameBoard[i][j] = '-';
                    count++;
                }
            }
        }
        return count;
    }

    static boolean doFall(char[][] gameBoard) {
        boolean check = false;
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j] == '-') {
                    for (int k = i - 1; k >= 0; k--) {
                        if (gameBoard[k][j] != '-') {
                            gameBoard[i][j] = gameBoard[k][j];
                            gameBoard[k][j] = '-';
                            check = true;
                            break;
                        }
                    }
                }
            }
        }
        return check;
    }

    static boolean checkArea(int r, int c, char[][] gameBoard) {
        return r >= 0 && r < gameBoard.length && c >= 0 && c < gameBoard[r].length;
    }
}