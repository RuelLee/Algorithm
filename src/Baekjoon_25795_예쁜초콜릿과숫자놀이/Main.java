/*
 Author : Ruel
 Problem : Baekjoon 25795번 예쁜 초콜릿과 숫자놀이
 Problem address : https://www.acmicpc.net/problem/25795
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25795_예쁜초콜릿과숫자놀이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int LIMIT = 100_000;
    static int n, a, b, c;

    public static void main(String[] args) throws IOException {
        // 화이트 초콜릿과 다크 초콜릿 n개가 각각 있다.
        // (화이트, 다크)는 예쁜 초콜릿이다.
        // (화이트, 예쁜 초콜릿, 다크)는 예쁜 초콜릿이다.
        // (예쁜 초콜릿, 예쁜 초콜릿)은 예쁜 초콜릿이다.
        // 위의 3가지 규칙으로 만들 수 없는 초콜릿 배열은 예쁜 초콜릿이 아니다.
        // 초콜릿의 점수는 특정 정수 a부터 시작하여, 화이트 초콜릿의 경우 +b, 다크 초콜릿의 경우 *c를 하여
        // 최종적으로 얻은 값에 mod 10^5을 한 값이 초콜릿 점수이다.
        // 얻을 수 있는 가장 높은 점수는?
        //
        // 백트래킹 문제
        // 먼저 초콜릿의 형태를 보면 올바른 괄호를 배치하는 것과 같다.
        // 즉 화이트 초콜릿은 n개 이하로 언제든 배치할 수 있고
        // 다크 초콜릿은 화이트 초콜릿이 등장한 개수 미만인 경우에만 추가로 배치할 수 있다.
        // dp[w][d] = 점수로도 풀려고 했으나, 중간중간 mod를 해야하는 상황이 발생하고, 그에 따라
        // 원래 값은 더 크지만 mod값은 낮아, 값이 대소가 바뀌는 경우가 발생할 수 있다.
        // 따라서 백트래킹을 통해 모든 경우의 수를 따진다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n쌍의 초콜릿
        // 점수 계산을 위한 상수 a, b, c
        n = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        System.out.println(findAnswer(0, 0, a));
    }
    
    // white 사용된 화이트 초콜릿의 개수
    // dark 사용된 다크 초콜릿의 개수
    // sum 현재까지의 점수
    static long findAnswer(int white, int dark, long sum) {
        // 모든 초콜릿의 배치가 끝났다면 점수 반환
        if (white == n && dark == n)
            return sum;

        long max = 0;
        // white가 아직 n개 다 안 쓴 경우
        // white를 배치 가능
        if (white < n)
            max = Math.max(max, findAnswer(white + 1, dark, (sum + b) % LIMIT));
        // dark가 white 개수보다 적은 경우
        // dark 배치 가능
        if (dark < white)
            max = Math.max(max, findAnswer(white, dark + 1, (sum * c) % LIMIT));
        // 두 경우의 값 중 더 큰 값을 반환
        return max;
    }
}