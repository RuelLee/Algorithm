/*
 Author : Ruel
 Problem : Baekjoon 20943번 카카오톡
 Problem address : https://www.acmicpc.net/problem/20943
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20943_카카오톡;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 유저는 각각 aix + biy + c = 0이라는 직선을 할당 받는다.
        // 모든 유저는 다른 직선을 할당 받는다.
        // 서로 직선이 교차하는 유저끼리는 통신이 가능하다.
        // 통신이 가능한 유저의 쌍을 구하라
        //
        // 조합, 유클리드 호제법
        // 기본적인 수학으로 평행한 직선은 서로 만나지 않는다.
        // 따라서 각 직선 별로 기울기를 구해, 기울기 별로 직선들을 분류한다.
        // 그리고 교차하는 직선의 쌍을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 직선
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 직선 ax + by + c = 0
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 만약 a가 0이라면 b는 어떠한 값이 오더라도
            // x축에 평행한 직선이다.
            // 따라서 b는 1로 고정하여 같은 기울기값으로 받는다.
            if (a == 0)
                b = 1;
            // 마찬가지로 b가 0이라면, y축에 평행 직선이므로
            // 하나의 값으로 통일하여 분류한다.
            if (b == 0)
                a = 1;
            
            // 최소 공배수
            int gcd = getGCD(a, b);
            // a가 음수이라면
            // a가 양수인 경우로 고정하여 분류한다.
            // 가령 x + y + 1 = 0 이나
            // -x -y + 1 = 0은 기울기 같은 직선이기 때문
            if (a < 0)
                gcd = -gcd;
            // a와 b
            a /= gcd;
            b /= gcd;
            
            // 맵에 기울기 별로 분류
            if (!map.containsKey(a))
                map.put(a, new HashMap<>());
            if (!map.get(a).containsKey(b))
                map.get(a).put(b, 1);
            else
                map.get(a).put(b, map.get(a).get(b) + 1);
        }

        long sum = 0;
        // 가능한 조합을 모두 구해
        for (int x : map.keySet()) {
            for (int y : map.get(x).keySet())
                sum += ((long) n - map.get(x).get(y)) * map.get(x).get(y);
        }
        // l이라는 직선과 m이라는 직선이 만나는 경우가
        // l이 우선일 때와 m이 우선일 때, 두 경우로 계산했으므로
        // 2를 나눠준다.
        // 답 출력
        System.out.println(sum / 2);
    }

    // 유클리드 호제법
    // a와 b의 최소 공배수를 구한다.
    static int getGCD(int a, int b) {
        // a와 b가 음수일 경우 고려
        int max = Math.max(Math.abs(a), Math.abs(b));
        int min = Math.min(Math.abs(a), Math.abs(b));
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}