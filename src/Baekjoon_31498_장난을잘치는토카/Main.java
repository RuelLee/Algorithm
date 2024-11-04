/*
 Author : Ruel
 Problem : Baekjoon 31498번 장난을 잘 치는 토카 양
 Problem address : https://www.acmicpc.net/problem/31498
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31498_장난을잘치는토카;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 토카는 친구인 돌돌이에게 장난을 치고 도망친다.
        // 돌돌이 <-> 토카 <-> 집 위치는 일직선이며
        // 토카는 집으로부터 a만큼 떨어진 위치에서, 한번에 b만큼 이동한다.
        // 대신 한번 이동할 때마다 지쳐, 이동거리가 k만큼씩 줄어든다.
        // 돌돌이는 토카로부터 c만큼 떨어져있으며, 한번에 d만큼 이동하며, 지치지 않는다.
        // 토카가 돌돌이로부터 도망쳐 집에 갈 수 최소 시간을 계산하라
        // 집에 도달하기 전, 토카와 돌돌이가 같은 위치에 있거나
        // 돌돌이가 토카를 제치거나, 집에 동시에 도착하면, 잡힌 것이 된다.
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해, 토카가 집에 도달하는 최소 시간을 구한다.
        // 그 때, 돌돌이가 토카의 위치보다 같거나 커진다면 잡힌 판정이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 토카와 집 사이의 거리 a, 토카의 이동 거리 b
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        // 돌돌이와 토카 사이의 거리 c, 돌돌이의 이동 거리 d
        long c = Long.parseLong(st.nextToken());
        long d = Long.parseLong(st.nextToken());
        // 토카가 한 번 이동할 때, 줄어드는 이동 거리 k
        long k = Long.parseLong(br.readLine());

        // 이분 탐색
        long start = 1;
        // k가 만약 0이라면, 최대 a / b + 1회 안에 집에 도착 가능
        // 그 외의 경우엔 1회 이동 거리가 0보다 큰 시점까지 계산
        long end = (k == 0 ? a / b + 1 : b / k + 1);
        while (start < end) {
            long mid = (start + end) / 2;

            if (mid * (b + b - (mid - 1) * k) / 2 < a)
                start = mid + 1;
            else
                end = mid;
        }

        // 찾은 횟수에 대해서의 토카의 위치
        long tokaLoc = c + start * (b + b - (start - 1) * k) / 2;
        // 돌돌이의 위치
        long doldolLoc = d * start;

        // 만약 토카가 집에 도달하지 못했거나
        // 토카가 집에 도달하지 못했거나
        // 집에 도달했으나, 돌돌이 역시 집의 위치 이상에 도달했다면
        // 잡힌 경우
        if (tokaLoc < a + c || doldolLoc >= a + c)
            System.out.println(-1);
        else        // 그 외의 경우는 토카가 도망치는데 성공한 경우
            System.out.println(start);
    }
}