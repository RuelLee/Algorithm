package 합승택시요금;

public class Solution {
    public static void main(String[] args) {
        // 경로가 간접적으로라도 연결이 되어있다면 그 최소 비용을 각각 구하고,
        // S ~ (각각의)동승 지점 + 동승 지점 ~ A + 동승 지점 ~ B의 값을 구해야한다.
        // 각각의 최소비용을 구하므로 Dijkstra로 각각 구해도 되지만
        // 어차피 모든 경로를 다 구해야하므로 Floyd Warshall 알고리즘으로 구하자.

        int n = 7;
        int s = 3;
        int a = 4;
        int b = 1;
        int[][] fares = {{5, 7, 9}, {4, 6, 4}, {3, 6, 1}, {3, 2, 3}, {2, 1, 6}};

        int[][] faresMatrix = new int[n][n];

        for (int i = 0; i < faresMatrix.length; i++) {
            for (int j = 0; j < faresMatrix[i].length; j++) {
                if (i == j)
                    faresMatrix[i][j] = 0;
                else
                    faresMatrix[i][j] = 10000000;   // 직접적인 경로가 없는 경우, 충분히 큰 값으로 설정하자.
            }
        }

        for (int[] fare : fares)    // 요금 매트릭스 구성.
            faresMatrix[fare[0] - 1][fare[1] - 1] = faresMatrix[fare[1] - 1][fare[0] - 1] = fare[2];

        for (int via = 0; via < n; via++) {     // 플로이드 와샬
            for (int from = 0; from < n; from++) {
                if (via == from)
                    continue;
                for (int to = 0; to < n; to++) {
                    if (to == via || to == from)
                        continue;
                    if (faresMatrix[from][to] > faresMatrix[from][via] + faresMatrix[via][to])
                        faresMatrix[from][to] = faresMatrix[from][via] + faresMatrix[via][to];
                }
            }
        }

        int[] totalMinFares = new int[n];
        int minFare = Integer.MAX_VALUE;

        for (int i = 0; i < totalMinFares.length; i++) {    // i는 A와 B가 S부터 같이 동승하는 지점
            if (faresMatrix[s - 1][i] != 1000000 && faresMatrix[i][a - 1] != 1000000 && faresMatrix[i][b - 1] != 1000000) {
                totalMinFares[i] = faresMatrix[s - 1][i] + faresMatrix[i][a - 1] + faresMatrix[i][b - 1];
                minFare = Math.min(minFare, totalMinFares[i]);
            } else
                totalMinFares[i] = Integer.MAX_VALUE;
        }
        System.out.println(minFare);
    }
}