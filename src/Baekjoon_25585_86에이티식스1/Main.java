/*
 Author : Ruel
 Problem : Baekjoon 25585번 86 ─에이티식스─ 1
 Problem address : https://www.acmicpc.net/problem/25585
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25585_86에이티식스1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, -1, 1, 1};
    static int[] dc = {-1, 1, 1, -1};

    public static void main(String[] args) throws IOException {
        // 산마그놀리아 공화국은 레기온이라는 무인병기들과 전쟁중이다.
        // 이에 대항하여 저거노트라는 보행 병기를 개발했다.
        // 저거노트는 1초에 대각선으로 한 칸 이동할 수 있다.
        // n * n 크기의 맵이 주어지며, 0은 빈칸, 1은 레기온, 2는 저거노트의 위치를 나타낸다.
        // 1 <= n <= 100
        // 저거노트는 한 대
        // 레기온은 0 <= 레기온의 수 <= min(n^2 -1, 10)
        // 레기온이 없거나, 모두 해칠 수 있으면 Undertaker를 출력하고 레기온을 해치우는데 걸리는 시간을 출력한다.
        // 모든 레기온을 해치울 수 없다면 Shorei 를 출력한다.
        //
        // 브루트포스, 백트래킹, BFS 문제
        // 가장 먼저, 저거노트로 이동할 수 없는 위치에 레기온이 존재할 수 있다.
        // 이는 (r, c)에서 r + c로 홀수번째 저거노트와 모든 레기온이 같은 홀짝번째 대각선에 위치하는지 확인해주면 된다.
        // 저거노트와 레기온들의 위치에서 다른 레기온까지의 거리를 BFS를 통해 계산한다.
        // 그 후 브루트포스를 통해, 현재 저거노트의 위치로부터 다른 레기온들의 위치로 이동하는 모든 순서를 따져
        // 가장 적게 걸리는 시간을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 맵
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        StringTokenizer st;
        int shinei = -1;
        List<Integer> legions = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2)
                    shinei = i * n + j;
                else if (map[i][j] == 1)
                    legions.add(i * n + j);
            }
        }

        int evenOdd = (shinei / n + shinei % n) % 2;
        boolean reachable = true;
        // 저거노트와 모든 레기온이 같은 홀짝번째 대각선에 위치하는지 확인.
        for (int legion : legions) {
            if (evenOdd != (legion / n + legion % n) % 2) {
                reachable = false;
                break;
            }
        }
        // 그렇지 않다면 불가능한 경우.
        if (!reachable) {
            System.out.println("Shorei");
            return;
        }

        // 저거노트에서 레기온까지의 거리, 레기온에서 레기온까지의 거리를 구해
        // 인접 행렬에 저장.
        int[][] adjMatrix = new int[legions.size() + 1][legions.size() + 1];
        int[] distances = calcDistances(shinei, n, legions);
        for (int i = 0; i < distances.length; i++)
            adjMatrix[0][i + 1] = distances[i];
        for (int i = 0; i < legions.size(); i++) {
            distances = calcDistances(legions.get(i), n, legions);
            for (int j = 0; j < distances.length; j++)
                adjMatrix[i + 1][j + 1] = distances[j];
        }

        // 브루트 포스를 통해
        // 레기온들에 방문하는 순서를 모두 따져보고, 가장 적은 시간을 구한다.
        boolean[] visited = new boolean[legions.size() + 1];
        visited[0] = true;
        // 만약 레기온이 존재하지 않는다면 답은 0
        // 그 외의 경우 큰 값으로 설정하여 최소값을 찾아나간다.
        int answer = legions.isEmpty() ? 0 : Integer.MAX_VALUE;
        for (int i = 1; i < legions.size() + 1; i++) {
            visited[i] = true;
            answer = Math.min(answer, bruteForce(i, 1, adjMatrix, visited) + adjMatrix[0][i]);
            visited[i] = false;
        }
        // 답 출력
        System.out.println("Undertaker");
        System.out.println(answer);
    }
    
    // 브루트 포스
    static int bruteForce(int current, int destroyed, int[][] adjMatrix, boolean[] visited) {
        // 모든 레기온들을 파괴한 경우
        if (destroyed == visited.length - 1)
            return 0;
        
        // 아직 레기온들이 남은 경우
        int sum = Integer.MAX_VALUE;
        for (int i = 1; i < visited.length; i++) {
            // 방문하지 않은 레기온이라면
            if (!visited[i]) {
                // 방문 체크 후,
                visited[i] = true;
                // 다음으로 i번째 레기온에 방문한다.
                // 이 때의 거리는 adjMatrix[currnet][i]
                sum = Math.min(sum, bruteForce(i, destroyed + 1, adjMatrix, visited) + adjMatrix[current][i]);
                visited[i] = false;
            }
        }
        // current에서부터 남은 레기온들을 방문하는데 걸리는 최소 시간을 반환한다.
        return sum;
    }

    // start로부터 레기온들의 위치까지 방문하는 최소 거리를 BFS로 찾는다.
    static int[] calcDistances(int start, int n, List<Integer> legions) {
        int[][] dp = new int[n][n];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[start / n][start % n] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int r = current / n;
            int c = current % n;

            for (int d = 0; d < 4; d++) {
                int nextR = r + dr[d];
                int nextC = c + dc[d];

                if (checkArea(nextR, nextC, n) && dp[nextR][nextC] > dp[r][c] + 1) {
                    dp[nextR][nextC] = dp[r][c] + 1;
                    queue.offer(nextR * n + nextC);
                }
            }
        }
        int[] array = new int[legions.size()];
        for (int i = 0; i < legions.size(); i++)
            array[i] = dp[legions.get(i) / n][legions.get(i) % n];
        return array;
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}