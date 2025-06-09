/*
 Author : Ruel
 Problem : Baekjoon 1222번 홍준 프로그래밍 대회
 Problem address : https://www.acmicpc.net/problem/1222
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1222_홍준프로그래밍대회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_SIZE = 2_000_000;

    public static void main(String[] args) throws IOException {
        // n개의 학교의 학생 수가 주어진다.
        // 모든 팀은 같은 수의 학생으로 이루어져있고, 학교는 전체 학생을 팀으로 구성해서 내보내야한다.
        // 본선에는 각 학교마다 최대 1팀이 진출하고, 최소 2학교 이상이 본선에 진출할 때
        // 팀원의 수를 조절하여, 본선에 참가하는 학생의 수를 최대화하고자 할 때
        // 본선에 진출하는 학생의 수는?
        //
        // 수학... 문제?
        // 각 학교는 모든 학생을 팀원으로 내보내야하기 때문에
        // 학생 수의 약수들이 한 학교에서 본선에 보낼 수 있는 한 팀의 수이다.
        // 전체 학교의 약수들을 세어
        // 그 개수 * 팀 원의 수가 가장 큰 값이 답이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 학교
        int n = Integer.parseInt(br.readLine());

        // 학교를 학생 수에 따라 분류
        int[] counts = new int[MAX_SIZE + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            counts[Integer.parseInt(st.nextToken())]++;

        // 약수
        int[] factors = new int[MAX_SIZE + 1];
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] == 0)
                continue;

            // 약수는 학생 수의 제곱근 만큼까지만 살펴보면 된다.
            // i가 학생 수에 나누어떨어진다면 i와 i / j 는 약수
            // i와 i / j가 같을 때를 조심
            for (int j = 1; j * j <= i; j++) {
                if (i % j == 0) {
                    factors[j] += counts[i];
                    if (i != j * j)
                        factors[i / j] += counts[i];
                }
            }
        }

        // 학생 수 * 진출한 학교의 수의 최대값을 찾는다
        long answer = 0;
        for (int i = 1; i < factors.length; i++) {
            if (factors[i] < 2)
                continue;
            answer = Math.max(answer, (long) i * factors[i]);
        }
        // 답 출력
        System.out.println(answer);
    }
}