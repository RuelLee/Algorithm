/*
 Author : Ruel
 Problem : Baekjoon 12970번 AB
 Problem address : https://www.acmicpc.net/problem/12970
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12970_AB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n, k가 주어질 때, 다음을 만족하는 문자열을 찾아라
        // 문자열의 길이는 n이고, 'A', 'B'로만 이루어져 있다.
        // 문자열에는 0 ≤ i < j < N 이면서 s[i] == 'A' && s[j] == 'B'를 만족하는 (i, j) 쌍이 K개가 있다.
        //
        // 애드 혹
        // 먼저 A와 B의 개수를 찾는다.
        // A와 B로 만들 수 있는 가장 많은 쌍의 개수는
        // n/2에 해당하는 만큼 A를 우선 배치하고,
        // n - (n/2)에 해당하는 만큼 B를 뒤에 배치한 경우이다.
        // 두 수의 곱만큼 쌍이 만들어지며 이 때가 n으로 만들 수 있는 최대 쌍의 개수이다.
        // k보다 크면서 가장 적은 수의 A를 갖는 경우를 찾는다.
        // 그러면 A와 B의 개수가 정해진 상태로 배치만 고려하면 된다.
        // 순서대로 살펴보되, 뒤에 나올 B의 개수를 알고 있으므로
        // 현재 A를 배치할 경우, 몇 개의 쌍이 생기는지 계산이 가능하다.
        // 따라서 위 과정을 반복하며 A와 B를 배치한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n, 쌍의 개수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 가능한 최대 B의 개수
        int b = 0;
        for (int i = 0; i < n; i++) {
            if (i * (n - i) >= k) {
                b = n - i;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        // 만약 해당 길이 n으로
        // k쌍을 만들 수 없는 경우 -1을 기록.
        if (b * (n - b) < k)
            sb.append(-1);
        else {
            // 가능한 경우
            // k가 b보다 같거나 큰 경우
            // 현 위치에 A를 배치하고
            // k에서 b만큼을 뺀다.
            for (int i = 0; i < n; i++) {
                if (k >= b) {
                    sb.append('A');
                    k -= b;
                } else {
                    // k가 b보다 작은 경우
                    // 현 위치에 B를 배치하고
                    // 뒤에 오는 B의 개수를 하나 차감한다.
                    sb.append('B');
                    b--;
                }
            }
        }
        // 결과 출력
        System.out.println(sb);
    }
}