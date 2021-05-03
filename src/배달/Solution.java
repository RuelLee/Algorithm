package 배달;

public class Solution {
    public static void main(String[] args) {
        // 1에서 부터 각 구간까지 갈 수 있는 최소 비용이 K이하인지 확인하는 문제.
        // 플로이드 와샬로 계산.
        // 다익스트라로도 가능할 것 같음. 추후 확인 후 시간 비교해보기.
        int N = 5;
        int[][] road = {{1, 2, 1}, {2, 3, 3}, {5, 2, 2}, {1, 4, 2}, {5, 3, 1}, {5, 4, 2}};
        int K = 3;

        int[][] roadMatrix = new int[N][N];

        for (int i = 0; i < roadMatrix.length; i++) {
            for (int j = 0; j < roadMatrix[i].length; j++) {
                if (i == j)
                    continue;
                roadMatrix[i][j] = 20000000;
            }
        }

        for (int[] path : road) {
            if (roadMatrix[path[0] - 1][path[1] - 1] == 0 || roadMatrix[path[0] - 1][path[1] - 1] > path[2])
                roadMatrix[path[0] - 1][path[1] - 1] = roadMatrix[path[1] - 1][path[0] - 1] = path[2];
        }

        for (int via = 0; via < roadMatrix.length; via++) {
            for (int start = 0; start < roadMatrix.length; start++) {
                if (via == start)
                    continue;
                for (int end = 0; end < roadMatrix.length; end++) {
                    if (end == via || end == start)
                        continue;

                    if (roadMatrix[start][end] > roadMatrix[start][via] + roadMatrix[via][end])
                        roadMatrix[start][end] = roadMatrix[start][via] + roadMatrix[via][end];
                }
            }
        }

        int count = 0;
        for (int i = 0; i < N; i++) {
            if (roadMatrix[0][i] <= K)
                count++;
        }

        System.out.println(count);
    }
}