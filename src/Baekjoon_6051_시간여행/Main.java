/*
 Author : Ruel
 Problem : Baekjoon 6051번 시간 여행
 Problem address : https://www.acmicpc.net/problem/6051
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6051_시간여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 쿼리가 주어진다.
        // a k -> k번 문제를 푼다.
        // s -> 가장 최근에 푼 문제를 목록에서 삭제한다.
        // t k -> k번 쿼리의 직전으로 돌아간다.
        // 각각 쿼리마다 가장 최근에 푼 문제를 출력하라
        //
        // 자료 구조, 연결 리스트
        // 가장 최근에 푼 문제만 출력하고, 삭제하는 걸로 보아 스택과 관련이 있을 것 같지만,
        // 사실 스택을 사용하지 않아도 풀수 있는 문제
        // 배열을 통해 최상단 문제와, 이전 문제의 idx를 관리해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 쿼리
        int n = Integer.parseInt(br.readLine());

        // 최상단의 문제
        int[] topProblem = new int[n + 1];
        topProblem[0] = -1;
        // 최상단 아래 문제의 idx
        int[] preProblemIdxes = new int[n + 1];

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        // 쿼리 처리
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            switch (st.nextToken().charAt(0)) {
                // k번 문제를 푼다.
                case 'a' -> {
                    int k = Integer.parseInt(st.nextToken());
                    // 최상단 문제를 풀고
                    topProblem[i] = k;
                    // 최상단 아래 문제의 idx는 i - 1로 변경
                    preProblemIdxes[i] = i - 1;
                }
                // 최상단 문제 제거
                case 's' -> {
                    // 최상단 문제를 최상단 아래 문제로 교체하고
                    topProblem[i] = topProblem[preProblemIdxes[i - 1]];
                    // 최상단 아래 문제를 최상단 아래 문제의 이전 아래 문제로 교체
                    preProblemIdxes[i] = preProblemIdxes[preProblemIdxes[i - 1]];
                }
                // k번 쿼리 이전으로 되돌린다.
                case 't' -> {
                    int k = Integer.parseInt(st.nextToken());
                    // 최상단 및 최상단 아래 문제 모두 k-1번에 기록된 값으로 교체
                    topProblem[i] = topProblem[k - 1];
                    preProblemIdxes[i] = preProblemIdxes[k - 1];
                }
            }
            // 현재 최상단 문제 기록
            sb.append(topProblem[i]).append("\n");
        }
        // 답 출력
        System.out.println(sb);
    }
}