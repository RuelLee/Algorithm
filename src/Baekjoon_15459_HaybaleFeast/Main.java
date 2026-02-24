/*
 Author : Ruel
 Problem : Baekjoon 15459번 Haybale Feast
 Problem address : https://www.acmicpc.net/problem/15459
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15459_HaybaleFeast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 건초더미가 주어지며 각각 풍미와 매운 정도가 주어진다.
        // 연속한 건초들을 하나의 음식으로 먹는데
        // 풍미는 속한 건초들 풍미의 합, 매운 정도는 건초들 중 최대 매운 정도로 결정된다.
        // 음식이 최소 m의 풍미를 가질 때, 얻을 수 있는 최소 매운 정도는 얼마인가
        //
        // 두 포인터 문제
        // 연속한 건초들을 먹기 때문에 두 포인터로 풀 수 있다.
        // 시작과 끝 포인터를 갖고, 그 안에 건초들의 풍미 합을 계산한다.
        // 우선순위큐를 통해, 최대 매운맛을 갖는 건초를 찾아, 풍미 합이 m이상이며 최소 매운맛을 갖는 음식을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 건초 더미, 최소 요구 풍미 m
        int n = Integer.parseInt(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        // 건초들 입력
        int[][] haybales = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                haybales[i][j] = Integer.parseInt(st.nextToken());
        }

        // 최소 매운 맛
        int minSpiciness = Integer.MAX_VALUE;
        // 구간 내 풍미 합
        long flavorSum = haybales[0][0];
        // 최대 매운 맛을 우선순위큐로 관리
        // 범위를 벗어나는 값은 제거
        PriorityQueue<Integer> spiciness = new PriorityQueue<>((o1, o2) -> Integer.compare(haybales[o2][1], haybales[o1][1]));
        spiciness.offer(0);
        int j = 0;
        // i ~ j까지의 건초 더미 범위
        for (int i = 0; i < haybales.length; i++) {
            // 풍미 합이 m 이하일 경우, j를 증가시켜 나감
            while (j + 1 < n && flavorSum < m) {
                flavorSum += haybales[++j][0];
                spiciness.offer(j);
            }

            // 그랬는데도 풍미 합이 m 미만이라면 불가능한 경우
            // 반복문 종료
            if (flavorSum < m)
                break;

            // 성립이 됐다면, 범위를 벗어나는 값이 우선순위큐에 있는지 확인하고 제거
            while (!spiciness.isEmpty() && spiciness.peek() < i)
                spiciness.poll();
            // 우선순위큐 최상단의 값이 해당 범위의 건초들 중 최대 매운 맛
            // 해당 값을 통해 minSpiciness 갱신
            minSpiciness = Math.min(minSpiciness, haybales[spiciness.peek()][1]);

            // 다음엔 i+1 ~ j범위를 볼 것이므로 i의 풍미 차감
            flavorSum -= haybales[i][0];
        }
        // 답 출력
        System.out.println(minSpiciness);
    }
}