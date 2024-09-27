/*
 Author : Ruel
 Problem : Baekjoon 15317번 동방 보수
 Problem address : https://www.acmicpc.net/problem/15317
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15317_동방보수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 동아리방과 m개의 동아리가 주어진다.
        // 각 동아리방의 수선 비용이 주어지고, 각 동아리마다 예산이 주어진다.
        // 그리고 전체 동아리에 나눠서 지원해줄 수 있는 금액 x가 주어진다.
        // 가장 많은 동아리방을 배정하고자 할 때, 그 개수는?
        //
        // 그리디, 이분탐색 문제
        // a개의 동아리에 동아리방을 배정한다고 할 때, 가장 가능한 경우의 수는
        // 수선 비용이 가장 싼 a개의 동아리방에, 예산이 가장 많은 a개의 동아리를 순서대로 매칭하는 방법이다.
        // 위의 경우로 배정이 가능한지를 이분탐색을 통해 찾아본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 동아리방, m개의 동아리, 지원 금액 x
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        // 동아리방의 수선 비용
        int[] c = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(c);
        // 동아리들의 예산
        int[] s = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(s);
        
        // 이분 탐색
        int start = 0;
        int end = Math.min(n, m);
        while (start <= end) {
            int mid = (start + end) / 2;
            if (canAllocate(mid, x, c, s))
                start = mid + 1;
            else
                end = mid - 1;
        }
        // 배정 가능한 최대 동아리방의 수 출력
        System.out.println(end);
    }

    // n개의 동아리 방을 x에 지원금액을 통해 배정하는 것이 가능한지 살펴본다.
    static boolean canAllocate(int n, int x, int[] c, int[] s) {
        // 수리 비용이 가장 싼 n개의 동아리방을 차례대로 살펴본다.
        for (int i = 0; i < n; i++) {
            // 예산이 가장 많은 n개의 동아리들을 순서대로 매칭해나간다.
            int idx = s.length - n + i;
            // 예산이 더 많다면 그냥 통과.

            // 예산이 부족한 경우
            // x에서 부족한 만큼을 끌어다 쓴다.
            if (c[i] > s[idx])
                x -= (c[i] - s[idx]);
            
            // 만약 예산이 0 미만으로 떨어졌다면 불가능한 경우이므로
            // false 반환
            if (x < 0)
                return false;
        }
        // 반복문을 모두 통과했다면 가능한 경우
        // true 반환
        return true;
    }
}