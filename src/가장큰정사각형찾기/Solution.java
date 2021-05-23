package 가장큰정사각형찾기;

public class Solution {
    static int[] dr = {-1, -1, 0};
    static int[] dc = {-1, 0, -1};

    public static void main(String[] args) {
        // board 에서 1으로만 가득찬 정사각형의 최대 넓이를 구해야한다.
        // r,c의 값이 1이라면, 좌측←, 좌상단↖, 상단↑의 값들 중 최소값을 구하자. 하나라도 board 의 범위를 벗어난다면 0값을 주자.
        // 만약 3곳의 값이 모두 1이었다면, 최소값이 1이 나올 것이다. 그럼 이 최소값에 +1을 해 r,c의 값을 2로 갱신해주자.
        // 이는 r,c와, r-1, c-1로 생기는 정사각형의 한 변의 길이가 2임을 나타낸다.
        //  1 1 1 1  만약 넓이가 4인 정사각형이 생긴다면 왼쪽과 같이 갱신될 것이다.
        //  1 2 2 2
        //  1 2 3 3
        //  1 2 3 4
        int[][] board = {{0, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {0, 0, 1, 0}};

        int max = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    board[i][j] = getMinValue(i, j, board) + 1;
                    max = Math.max(board[i][j], max);
                }
            }
        }

        System.out.println(max * max);
    }

    static int getMinValue(int r, int c, int[][] board) {   // 세 곳의 값을 살펴 최소값을 찾아내주자. 한 곳이라도 범위를 벗어난다면 0
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            int nextR = r + dr[i];
            int nextC = c + dc[i];

            if (checkArea(nextR, nextC, board))
                min = Math.min(min, board[nextR][nextC]);
            else {
                min = 0;
                break;
            }
        }
        return min;
    }

    static boolean checkArea(int r, int c, int[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[r].length;
    }
}