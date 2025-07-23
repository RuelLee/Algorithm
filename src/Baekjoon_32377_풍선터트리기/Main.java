/*
 Author : Ruel
 Problem : Baekjoon 32377번 풍선 터트리기
 Problem address : https://www.acmicpc.net/problem/32377
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32377_풍선터트리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 풍선이 있다.
        // 세 사람 A, B, C가, x, y, z분마다 하나의 풍선을 터트린다.
        // 같은 시간에 풍선을 터뜨린다면 A, B, C 순서대로 터뜨린다.
        // 마지막 풍선을 터뜨리는 사람은?
        //
        // 이분탐색 문제
        // 이분 탐색을 통해 모든 풍선이 터지는 시점을 찾는다.
        // 그 후, 해당 시간 1초전까지 터지는 풍선을 모두 처리한다.
        // 그 후, 해당 시간에 터뜨리는 시간이 된 사람을 A B C 순서대로 살펴보며 터뜨려
        // 마지막 풍선을 터뜨린 사람을 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 풍선
        long n = Integer.parseInt(st.nextToken());
        // A, B, C 사람이 풍선을 터트리는 주기 x, y, z
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int z = Integer.parseInt(st.nextToken());

        // 이분탐색으로 모든 풍선이 터지는 시점을 찾는다.
        long start = 0;
        long end = Long.MAX_VALUE;
        while (start < end) {
            long mid = (start + end) / 2;
            long sum = mid / x + mid / y + mid / z;
            if (sum < n)
                start = mid + 1;
            else
                end = mid;
        }
        
        // 1초 전까지 터지는 풍선들을 처리
        n -= ((start - 1) / x + (start - 1) / y + (start - 1) / z);
        StringBuilder sb = new StringBuilder();
        // A가 start에 주기가 돌아 풍선을 터뜨리고
        // 그게 마지막 풍선인 경우
        if (start % x == 0 && --n == 0)
            sb.append('A');
        // B가 start에 주기가 돌아 풍선을 터뜨리고 그게 마지막인 경우 
        else if (start % y == 0 && --n == 0)
            sb.append('B');
        // 그 외의 경우
        else
            sb.append('C');
        sb.append(" win");
        // 답 출력
        System.out.println(sb);
    }
}