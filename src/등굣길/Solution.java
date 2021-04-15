package 등굣길;

public class Solution {
    public static void main(String[] args) {
        int m = 4;
        int n = 3;
        int[][] puddles = {{2, 2}};

        int[][] routes = new int[m][n];
        routes[0][0] = 1;
        for (int[] puddle : puddles) routes[puddle[0] - 1][puddle[1] - 1] = -1;

        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                if (routes[i][j] != -1) {
                    if (checkArea(i - 1, j, routes) && routes[i - 1][j] != -1)
                        routes[i][j] += routes[i - 1][j];
                    if (checkArea(i, j - 1, routes) && routes[i][j - 1] != -1)
                        routes[i][j] += routes[i][j - 1];
                    routes[i][j] %= 1000000007;
                }
            }
        }
        System.out.println(routes[m - 1][n - 1]);
    }

    static boolean checkArea(int i, int j, int[][] map) {
        if (i < 0 || j < 0 || i >= map.length || j >= map[i].length)
            return false;
        return true;
    }
}