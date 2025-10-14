/*
 Author : Ruel
 Problem : Baekjoon 15606번 High Score
 Problem address : https://www.acmicpc.net/problem/15606
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15606_HighScore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 4개의 토큰 종류별로 톱니, 태블릿, 나침반, 와일드카드가 a, b, c, d개 주어진다.
        // 점수는 a^2 + b^2 + c^2 + 7 * min(a, b, c)점을 얻는다.
        // 와일드카드는 원하는 만큼 a, b, c에 분배할 수 있다.
        // 얻을 수 있는 최대 점수를 구하라
        //
        // 브루트포스, 재귀 문제
        // a, b, c, d가 각각 10억이내로 주어지므로 직접 계산해선 당연히 시간이 초과된다.
        // 조건을 만족하는 덩어리만큼 분리해서 브루트포스 처리해야한다.
        // a b c는 사실 꼭 정해진 종류여야할 필요는 없고 개수만이 중요하다
        // 따라서 a, b, c를 오름차순 정렬하고
        // d를 c(가장 많은 개수)에 몰빵하는 경우
        // a 와 b의 개수가 다르다면 min(b - a, d) 만큼을 a에 추가하는 경우
        // a와 b의 개수가 같고, a와 c의 개수가 다르다면 min(c - a, d / 2)를 a와 b에 더해주는 경우
        // a b c가 모두 같다면 각각 d / 3만큼 더해주는 경우
        // 한 곳에 몰빵하는 경우는 매번 진행하며, 아래 3가지 경우는 해당하는 경우만 진행하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 사람. 사실상 n개의 테스트케이스
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 토큰의 개수
            long[] tokens = new long[4];
            for (int j = 0; j < tokens.length; j++)
                tokens[j] = Integer.parseInt(st.nextToken());
            // 오름차순 정렬
            Arrays.sort(tokens, 0, 3);
            // 브루트포스, 재귀로 답을 찾고, 기록
            sb.append(findAnswer(tokens[0], tokens[1], tokens[2], tokens[3])).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
    
    // 각 토큰의 개수가 a, b, c, d개일 때
    // 얻을 수 있는 최대 점수
    static long findAnswer(long a, long b, long c, long d) {
        // 와일드카드 분배가 끝났다면 점수 계산 후 반환
        if (d == 0)
            return a * a + b * b + c * c + a * 7;

        // 그 외의 경우
        // 와일드카드를 분배하는 경우를 브루트포스로 찾는다.
        long max = 0;
        // d를 c에 몰빵하는 경우
        max = Math.max(max, findAnswer(a, b, c + d, 0));
        // a와 b의 개수가 다른 경우.
        // min(b - a, d)만큼을 a에 추가한다.
        if (a < b) {
            long use = Math.min(b - a, d);
            max = Math.max(max, findAnswer(a + use, b, c, d - use));
        } else if (a == b && a < c && d >= 2) {
            // a와 b의 개수가 같고, c와는 다른 경우
            // a와 b가 c와 최대한 가까워지도록 균등 분배
            long use = Math.min(c - a, d / 2);
            max = Math.max(max, findAnswer(a + use, b + use, c, d - use * 2));
        } else if (a == b && b == c && d >= 3)      // 3개 모두 같은 경우. 3 곳에 균등 분배
            max = Math.max(max, findAnswer(a + d / 3, b + d / 3, c + d / 3, d % 3));
        // 모든 경우를 탐색하고 가장 큰 값을 반환
        return max;
    }
}