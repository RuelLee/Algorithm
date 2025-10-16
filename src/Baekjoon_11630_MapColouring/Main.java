/*
 Author : Ruel
 Problem : Baekjoon 11630번 Map Colouring
 Problem address : https://www.acmicpc.net/problem/11630
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11630_MapColouring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int c;
    static boolean[][] adjMatrix;

    public static void main(String[] args) throws IOException {
        // c개의 국가와 국가 간 인접 정보가 주어진다.
        // 인접한 국가는 서로 다른 색으로 칠하고자할 때
        // 모든 국가를 칠하는 최소 색의 개수는?
        //
        // 브루트 포스, 백트래킹 문제
        // c가 최대 16으로 그리 크지 않으므로
        // 0번 국가부터 c-1번 국가까지 인접한 국가들의 색을 따져가며, 칠할 수 있는 색을 칠해본다.
        // 1 ~ 4개의 색까지 가능한지를 따져보고, 불가능하다면 그냥 many 출력

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        // 국가 간 인접 정보
        adjMatrix = new boolean[16][16];
        // 각 국가가 칠해진 색
        int[] colored = new int[16];
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 국가의 수
            c = Integer.parseInt(st.nextToken());
            // 인접 국가 정보의 개수
            int b = Integer.parseInt(st.nextToken());

            // adjMatrix 초기화
            for (boolean[] am : adjMatrix)
                Arrays.fill(am, false);
            // 인접 국가 정보 입력
            for (int i = 0; i < b; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());

                adjMatrix[u][v] = adjMatrix[v][u] = true;
            }

            // 모든 국가를 칠하는 최소 색의 수
            int answer = Integer.MAX_VALUE;
            for (int j = 1; j <= 4; j++) {
                // colored 배열 초기화
                Arrays.fill(colored, 0);
                // j개의 색으로 칠할 수 있는 체크
                if (canColor(0, j, colored)) {
                    // 가능한 경우
                    // answer 값 변경 후 반복문 종료
                    answer = j;
                    break;
                }
            }
            // answer가 초기값인 상태라면 many
            // 그 외의 경우 해당 값 기록
            sb.append(answer > 4 ? "many" : answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // c개의 국가를 num개의 색으로 칠하는데 
    // 현재 node번째 국가의 차례이며, 각 국가에 칠해진 색의 정보 colored
    static boolean canColor(int node, int num, int[] colored) {
        // c번까지 진행되어왔다면
        // num이하의 색으로 칠하는 것이 가능한 경우이므로 true 반환
        if (node == c)
            return true;

        // node에 color을 칠하는 것이 가능한지
        // 인근 국가들을 살펴본다.
        for (int color = 1; color <= num; color++) {
            boolean possible = true;
            for (int near = 0; near < node; near++) {
                if (adjMatrix[node][near] && color == colored[near]) {
                    possible = false;
                    break;
                }
            }

            // 가능하다면
            if (possible) {
                // node에 color을 칠하고 진행시켰을 때
                // num개 이하의 색으로 모두 칠할 수 있는지 확인.
                colored[node] = color;
                // 가능한 경우 true 반환
                if (canColor(node + 1, num, colored))
                    return true;
            }
        }
        // 여기에 도달했다면 불가능한 경우이므로 false 반환
        return false;
    }
}