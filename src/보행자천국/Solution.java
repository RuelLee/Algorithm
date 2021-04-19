package 보행자천국;

public class Solution {
    public static void main(String[] args) {
        // DP문제이나, 상태(위에서 왔는지, 왼쪽에서 왔는지)에 따라 값이 구별이 되어야한다.
        // cityMap과 같은 크기의 가로 세로를 갖지만, 층(상태별로 저장할)을 2개로 한 3차원 배열을 만들자.
        // cases[i][j][0] 공간에는 i,j에 위에서 내려온, case[i][j][1]에는 왼쪽에서 온 가짓수를 계산하자.
        int m = 3;
        int n = 6;
        int[][] cityMap = {{0, 2, 0, 0, 0, 2}, {0, 0, 2, 0, 1, 0}, {1, 0, 0, 2, 2, 0}};

        int[][][] cases = new int[m][n][2];

        cases[0][0][0] = 1;     // 원점에선 어떠한 상태도 아니다. m=1, n=1 등의 상황에서 1이라는 값만 돌려줄 수 있도록 아무 칸에나 1값을 넣어두자.

        for (int i = 0; i < cityMap.length; i++) {
            for (int j = 0; j < cityMap[i].length; j++) {   // 각 칸을 돌며, 가짓수를 계산해주자.
                if (checkArea(i - 1, j, cityMap))
                    cases[i][j][0] += calculateCases(i - 1, j, 0, cityMap, cases);
                if (checkArea(i, j - 1, cityMap))
                    cases[i][j][1] += calculateCases(i, j - 1, 1, cityMap, cases);
                cases[i][j][0] %= 20170805;
                cases[i][j][1] %= 20170805;
            }
        }
        System.out.println(((cases[m - 1][n - 1][0] + cases[m - 1][n - 1][1])) % 20170805);
    }

    static boolean checkArea(int r, int c, int[][] map) {   // cityMap의 범위를 넘지 않는지 체크한다.
        if (r < 0 || c < 0 || r >= map.length || c >= map[r].length)
            return false;
        return true;
    }

    static int calculateCases(int r, int c, int direction, int[][] cityMap, int[][][] cases) {      // r,c가 통행금지나 좌우회전 금지인지와 방향에 따른 가짓수를 리턴한다.
        switch (cityMap[r][c]) {
            case 0:
                return cases[r][c][0] + cases[r][c][1];
            case 2:     // 회전 금지일 때는 해당 방향으로 오는 값만 넘겨준다. (0-> 위에서 아래로, 1-> 왼쪽에서 오른쪽으로)
                return cases[r][c][direction];
            default:
                return 0;
        }
    }
}