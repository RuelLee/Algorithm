/*
 Author : Ruel
 Problem : Baekjoon 23817번 포항항
 Problem address : https://www.acmicpc.net/problem/23817
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23817_포항항;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static char[][] map;
    static int[][] adjMatrix;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 지도가 주어진다.
        // 현재 S의 위치에 존재하고 있으며, K라고 적힌 가게를 5곳 들리고자 한다.
        // 상하좌우 한 방향으로 한 칸 움직이는데 1분이 걸리고, X가 적힌 곳으로는 이동하지 못한다.
        // 가게를 5곳 방문하지 못한다면 -1을, 가능하다면 그 때의 최소 시간을 출력하라
        //
        // BFS, 브루트 포스, 백트래킹 문제
        // 먼저 BFS를 통해 시작 지점과 각각의 가게들을 이동하는데 걸리는 시간을 인접행렬로 정리한다.
        // 그 후, 브루트 포스를 활용하여 가게들 중 5곳을 방문하며 걸리는 시간을 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 지도
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        map = new char[n][];
        // 각각의 지점들
        // 시작 지점을 0번 지점으로 주기 위해, 리스트에 시작 지점만 먼저 담고
        // 나머지는 temp 큐에 담아, 추후에 큐에서 리스트로 옮겨준다.
        List<Integer> points = new ArrayList<>();
        Queue<Integer> temp = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S')
                    points.add(i * m + j);
                else if (map[i][j] == 'K')
                    temp.add(i * m + j);
            }
        }
        while (!temp.isEmpty())
            points.add(temp.poll());

        // 인접 행렬
        // 시작 지점과 가게들을 이동하는데 걸리는 시간을 정리한다.
        adjMatrix = new int[points.size()][points.size()];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        int[][] minDistances = new int[n][m];
        // i번째 지점에서 출발
        for (int i = 0; i < points.size(); i++) {
            temp.offer(points.get(i));
            for (int[] md : minDistances)
                Arrays.fill(md, Integer.MAX_VALUE);
            minDistances[points.get(i) / m][points.get(i) % m] = 0;
            // BFS
            while (!temp.isEmpty()) {
                int current = temp.poll();
                int row = current / m;
                int col = current % m;

                for (int d = 0; d < 4; d++) {
                    int nextR = row + dr[d];
                    int nextC = col + dc[d];

                    if (checkArea(nextR, nextC) && map[nextR][nextC] != 'X' && minDistances[nextR][nextC] > minDistances[row][col] + 1) {
                        minDistances[nextR][nextC] = minDistances[row][col] + 1;
                        temp.offer(nextR * m + nextC);
                    }
                }
            }
            // i번째 지점에서 j번째 지점으로 이동하는데 걸리는 시간을 기록한다.
            for (int j = i + 1; j < points.size(); j++)
                adjMatrix[i][j] = adjMatrix[j][i] = minDistances[points.get(j) / m][points.get(j) % m];
        }

        // 만약 가게가 5개 미만이라면 불가능한 경우이므로 -1을 출력
        if (points.size() < 6)
            System.out.println(-1);
        else {
            // 그 외의 경우 브루트 포스로 5곳을 방문하는데 걸리는 최소 시간을 계산.
            int answer = bruteForce(1, 0, new boolean[points.size()], 0);
            // 5곳을 방문하는 것이 불가능하다면(예를 들어 X로 막힌 경우) -1 출력
            // 그 외에는 최소 시간 출력
            System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
        }
    }
    
    // idx 순서, current 현재 위치한 지점, seleced 방문한 지점, sum 현재 소요 시간
    static int bruteForce(int idx, int current, boolean[] selected, int sum) {
        // 모두 방문했다면 총 소요 시간 반환
        if (idx > 5)
            return sum;

        int min = Integer.MAX_VALUE;
        // 다음으로 i번째 지점을 방문하는 것이 가능한지 체크
        for (int i = 1; i < adjMatrix.length; i++) {
            // i번째 지점을 아직 방문하지 않았고, 갈 수 있다면
            if (!selected[i] && adjMatrix[current][i] != Integer.MAX_VALUE) {
                // 방문 체크 후
                selected[i] = true;
                // current -> i로 파생되는 경우, 소요된 최소 시간을 반환 받아, min의 값과 비교
                min = Math.min(min, bruteForce(idx + 1, i, selected, sum + adjMatrix[current][i]));
                // 방문 체크 해제
                selected[i] = false;
            }
        }
        // 얻은 최소 시간 반환
        return min;
    }
    
    // 맵의 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}