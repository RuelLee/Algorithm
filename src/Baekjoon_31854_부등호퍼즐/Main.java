/*
 Author : Ruel
 Problem : Baekjoon 31854번 부등호 퍼즐
 Problem address : https://www.acmicpc.net/problem/31854
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31854_부등호퍼즐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자판이 주어지고, 각 칸마다 인근 격자판과의 대소 관계가 주어진다.
        // 해당 대소 관계를 만족하는 격자판을 1 ~ n^2의 수를 사용하여 채워라.
        //
        // 위상 정렬 문제
        // 항상 만족하는 조건만 제시되므로, 위상 정렬에 따라 계산해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자판
        int n = Integer.parseInt(br.readLine());
        
        // 위상 정렬을 위한 연결.
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n * n; i++)
            connections.add(new ArrayList<>());
        // 진입 차수
        int[] indegree = new int[n * n];
        StringTokenizer st;
        // 대소 관계 입력
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n - 1; j++) {
                if (st.nextToken().equals(">")) {
                    indegree[i * n + j]++;
                    connections.get(i * n + j + 1).add(i * n + j);
                } else {
                    indegree[i * n + j + 1]++;
                    connections.get(i * n + j).add(i * n + j + 1);
                }
            }
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                if (st.nextToken().equals(">")) {
                    indegree[i * n + j]++;
                    connections.get((i + 1) * n + j).add(i * n + j);
                } else {
                    indegree[(i + 1) * n + j]++;
                    connections.get(i * n + j).add((i + 1) * n + j);
                }
            }
        }
        
        // 위상 정렬
        Queue<Integer> queue = new LinkedList<>();
        // 진입 차수가 0인 칸부터 입력
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0)
                queue.offer(i);
        }

        // 위상 정렬 순서에 따라 칸을 순차적으로 탐색하며
        // 수를 채워넣는다.
        int[][] answer = new int[n][n];
        int count = 1;
        while (!queue.isEmpty()) {
            // 현재 위치에 수를 입력
            int current = queue.poll();
            answer[current / n][current % n] = count++;

            // current를 방문하면서 낮출 수 있는 인근 격자판의 진입 차수를 낮춘다.
            for (int next : connections.get(current)) {
                if (--indegree[next] == 0)
                    queue.offer(next);
            }
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length - 1; j++)
                sb.append(answer[i][j]).append(" ");
            sb.append(answer[i][n - 1]).append("\n");
        }
        // 출력
        System.out.print(sb);
    }
}