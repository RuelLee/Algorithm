/*
 Author : Ruel
 Problem : Baekjoon 30689번 미로 보수
 Problem address : https://www.acmicpc.net/problem/30689
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30689_미로보수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static char[][] map;
    static int[][] costs, minCostIdxs;

    public static void main(String[] args) throws IOException {
        // 각 칸에 상 하 좌 우 하나가 표시되어있는 n * m 크기의 미로가 주어진다.
        // 각 칸에 적힌 방향으로 한 칸 움직일 수 있으며, 범위 밖으로 나가는 순간 미로 탈출이다.
        // 운이 나쁜 경우, 미로에서 탈출하지 못하는 경우도 있다.
        // 몇몇 위치에 밖으로 탈출 할 수 있는 점프대를 설치하여 모든 경우에 미로에서 탈출할 수 있도록 하고자한다.
        // 각 위치에서의 점프대 설치 비용이 주어질 때, 설치해야하는 점프대들의 최소 비용은?
        //
        // BFS 문제
        // BFS 탐색을 통해, 진행하며, 미로 밖으로 나갈 수 있는지, 혹은 순환하는 구간이 생겨 탙출하지 못하는지 확인한다.
        // 탈출할 수 없다면, 순환하는 구간 내에서 가장 점프대 비용이 적은 곳에 점프대를 설치한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 미로
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 미로의 각 칸에 적힌 방향
        map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 점프대 설치 비용
        costs = new int[n][m];
        for (int i = 0; i < costs.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < costs[i].length; j++)
                costs[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 현 위치에서 시작할 때
        // 미로를 나갈 수 있는지 혹은 점프대를 통해 나간다면 점프대의 위치 
        minCostIdxs = new int[n][m];
        for (int[] mci : minCostIdxs)
            Arrays.fill(mci, Integer.MAX_VALUE);
        
        // 방문 체크
        boolean[][] visited = new boolean[n][m];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 이미 i, j 위치의 결과가 계산되어있다면 건너 뛴다.
                if (minCostIdxs[i][j] != Integer.MAX_VALUE)
                    continue;

                stack.clear();
                // 그렇지 않다면 i, j에서 정상적으로 탈출할 수 있는지
                // 혹은 점프대를 사용한다면 어느 위치에서 사용하는지 계산한다.
                findMinCostIdx(i, j, visited, stack);
            }
        }

        int sum = 0;
        // 모든 칸을 돌아보며
        // 
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 정상적으로 나갈 수 있는 경우 건너뛰고
                if (minCostIdxs[i][j] == -1)
                    continue;
                
                // 점프대를 사용할 경우
                int r = minCostIdxs[i][j] / m;
                int c = minCostIdxs[i][j] % m;
                // 해당 점프대가 계산이 되었는지 확인.
                // 이미 해당 점프대를 사용한 적이 없다면(=설치 되어있지 않다면)
                // 해당 점프대의 설치 비용 합산
                if (visited[r][c]) {
                    sum += costs[r][c];
                    visited[r][c] = false;
                }
            }
        }
        // 전체 답안 출력
        System.out.println(sum);
    }
    
    // BFS로 탐색
    static int findMinCostIdx(int r, int c, boolean[][] visited, Stack<Integer> stack) {
        // 범위 밖으로 나갈 경우
        // 정상적으로 탈출이 가능
        // -1 반환
        if (r < 0 || r >= map.length || c < 0 || c >= map[r].length)
            return -1;
        else if (minCostIdxs[r][c] != Integer.MAX_VALUE)        // 이미 계산된 결과가 존재한다면, 해당 결과값 반환.
            return minCostIdxs[r][c];
        else if (visited[r][c]) {       // 순환하는 경우
            // 순환하는 구간을 현재까지 기록한 stack을 따라가며
            // (r, c) <-> (r, c)를 찾고
            // 해당 구간에서 가장 싼 점프대의 위치를 찾는다.
            int minCostIdx = r * m + c;
            while (!stack.isEmpty() && !(r == stack.peek() / m && c == stack.peek() % m)) {
                if (costs[minCostIdx / m][minCostIdx % m] > costs[stack.peek() / m][stack.peek() % m])
                    minCostIdx = stack.peek();
                stack.pop();
            }
            // 그 값을 기록하고 반환
            return minCostIdxs[r][c] = minCostIdx;
        }
        
        // 아직 밖을 탈출하지 못했고, 순환하는 구간을 찾지 못했다면
        // 발판에 적힌 위치로 이동
        // 방문 체크
        visited[r][c] = true;
        // 스택 추가
        stack.push(r * m + c);
        switch (map[r][c]) {
            case 'U' -> minCostIdxs[r][c] = findMinCostIdx(r - 1, c, visited, stack);
            case 'D' -> minCostIdxs[r][c] = findMinCostIdx(r + 1, c, visited, stack);
            case 'L' -> minCostIdxs[r][c] = findMinCostIdx(r, c - 1, visited, stack);
            case 'R' -> minCostIdxs[r][c] = findMinCostIdx(r, c + 1, visited, stack);
        }
        // 계산된 결과값 반환.
        return minCostIdxs[r][c];
    }
}