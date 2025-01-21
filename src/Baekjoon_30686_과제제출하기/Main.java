/*
 Author : Ruel
 Problem : Baekjoon 30686번 과제 제출하기
 Problem address : https://www.acmicpc.net/problem/30686
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30686_과제제출하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] knowledge;
    static int[][] problems;

    public static void main(String[] args) throws IOException {
        // m개의 문제와 n개의 지식이 존재한다.
        // 지식은 각각 유효 기간이 존재하며, 해당 기간이 지나면 잊어버린다.
        // 하루에 여러 지식을 배울 수 있다.
        // m개의 문제는 각각 ki개의 지식이 필요하다
        // 지식을 배우는 걸 최소화하면서 모든 문제를 하루에 하나씩 풀고자한다.
        // 배워야하는 지식의 최소 횟수는?
        //
        // 브루트 포스 문제
        // 브루트 포스로 모든 문제를 배우는 경우의 수를 따져 계산하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지식, m개의 문제
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각각 지식의 유효 기간
        knowledge = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < knowledge.length; i++)
            knowledge[i] = Integer.parseInt(st.nextToken());

        // m개의 문제와 각각 필요한 지식들
        problems = new int[m][];
        for (int i = 0; i < problems.length; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            problems[i] = new int[k];
            for (int j = 0; j < problems[i].length; j++)
                problems[i][j] = Integer.parseInt(st.nextToken()) - 1;
        }

        // 답 출력
        System.out.println(bruteForce(0, new int[m], new boolean[m]));
    }

    // 브루트 포스
    static int bruteForce(int idx, int[] order, boolean[] selected) {
        // 모든 날에 대해 문제 배정이 끝났다면
        if (idx == order.length) {
            int count = 0;
            // 각각의 지식의 유효 기간을 따져가며
            // 모든 문제를 풀기 위한 최소 지식의 배움 횟수를 계산한다.
            int[] lastTimes = new int[knowledge.length];
            for (int i = 0; i < order.length; i++) {
                // i번째 날에 푸는 문제가 요구하는 지식들을 모두 살펴보며
                // 유효 기간이 지났다면, 오늘 새로 배운다.
                for (int j = 0; j < problems[order[i]].length; j++) {
                    if (lastTimes[problems[order[i]][j]] < i + 1) {
                        count++;
                        lastTimes[problems[order[i]][j]] = i + knowledge[problems[order[i]][j]];
                    }
                }
            }
            // 배움의 횟수 반환.
            return count;
        }
        
        // idx번째에서 선택할 수 있는 여러 갈래들 중 배움의 최소 횟수를 계산한다.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < selected.length; i++) {
            // i번 문제를 아직 안 골랐다면
            if (!selected[i]) {
                // idx날에 i번 문제를 할당.
                selected[i] = true;
                order[idx] = i;
                // 그 후로 idx + 1번째 날로 진행하였을 때
                // 얻을 수 있는 최소 배움의 횟수가 함수의 반환값으로 반환되어 온다.
                // 해당 값을 min과 비교
                min = Math.min(min, bruteForce(idx + 1, order, selected));
                // i 문제 선택 해제
                selected[i] = false;
            }
        }
        // 얻은 최소 배움의 횟수 반환.
        return min;
    }
}