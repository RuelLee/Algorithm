/*
 Author : Ruel
 Problem : Baekjoon 2611번 자동차경주
 Problem address : https://www.acmicpc.net/problem/2611
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2611_자동차경주;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Road {
    int end;
    int score;

    public Road(int end, int score) {
        this.end = end;
        this.score = score;
    }
}

public class Main {
    static List<List<Road>> roads;
    static int[] maxScores;
    static int[] nexts;

    public static void main(String[] args) throws IOException {
        // n개의 지점과 n개의 지점들을 서로 연결하는 m개의 도로들과 해당 도로를 이용하면 얻는 점수가 주어진다
        // 1번 지점에서 출발해서 다시 1번 지점으로 돌아오는데, 가장 큰 점수를 얻고자 한다
        // 이 때의 점수와 경로를 출력하라
        //
        // 메모이제이션 문제.
        // bottom up 방식으로 풀어보자
        // maxScores[] 배열을 선언하고, 그 배열의 값은 해당 지점으로부터 1까지 도달하는 최대 점수를 정한다.
        // 만약 1 -> 3 -> 1이라는 경로를 먼저 계산했고, 1 -> 4 -> 2 -> 3 -> 1이라는 경로를 다시 탐색할 때
        // 3 -> 1이라는 부분은 중복되므로 연산하지 않고, 3의 메모이제이션 값을 바로 참조하는 형태다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        
        // n개의 지점들을 연결하는 도로들
        roads = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            roads.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            roads.get(p).add(new Road(q, r));
        }

        // 메모이제이션
        maxScores = new int[n + 1];
        // 현재 지점에서 이동해야하는 다음 지점.
        nexts = new int[n + 1];
        // 먼저 1 지점에서 모든 도로에 대해 각각 출발시켜준다.
        for (Road next : roads.get(1)) {
            // 만약 현재 1에서 출발할 때의 점수보다 큰 값을 찾은 경우.
            if (maxScores[1] < findMaxScoreToStart(next.end) + next.score) {
                // 값 갱신.
                maxScores[1] = findMaxScoreToStart(next.end) + next.score;
                // 그리고 다음 지점으로 next.end 표시.
                nexts[1] = next.end;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        // 최대 점수는 maxScores[1]에 기록되어있고
        sb.append(maxScores[1]).append("\n");
        // 경로를 1부터 nexts를 따라 가면된다.
        sb.append(1).append(" ");
        int loc = 1;
        while (nexts[loc] != 1) {
            sb.append(nexts[loc]).append(" ");
            loc = nexts[loc];
        }
        sb.append(1);
        System.out.println(sb);
    }

    // loc으로부터 1까지 도달하는 최대 점수를 구한다.
    static int findMaxScoreToStart(int loc) {
        // 만약 1이라면 0 리턴.
        if (loc == 1)
            return 0;

        // 만약 계산된 메모이제이션 결과값이 없다면(= 처음 방문하는 것이라면)
        if (maxScores[loc] == 0) {
            // 모든 도로에 대해 탐색한다.
            for (Road next : roads.get(loc)) {
                // loc -> 1로 가는 경로들 중 기록된 점수보다 더 큰 점수를 찾았을 때.
                if (maxScores[loc] < findMaxScoreToStart(next.end) + next.score) {
                    // 값 갱신.
                    maxScores[loc] = findMaxScoreToStart(next.end) + next.score;
                    // 다음 위치 지점 갱신.
                    nexts[loc] = next.end;
                }
            }
        }
        return maxScores[loc];
    }
}