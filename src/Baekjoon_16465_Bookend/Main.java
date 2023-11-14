/*
 Author : Ruel
 Problem : Baekjoon 16465번 Bookend
 Problem address : https://www.acmicpc.net/problem/16465
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16465_Bookend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 책을 너비 m인 책장에 꽂으려 한다.
        // 이 때 밑바닥의 길이가 l인 북엔드를 사용하여
        // 모든 책들이 비스듬히 세워지지 않게 하려한다.
        // 책들의 순서를 유지하며 책을 꽂을 때, 필요한 북엔드의 수는?
        //
        // 애드 혹 문제
        // 책이 책장에 올바르게 꽂힐 수 있는 여러가지 경우를 생각해야한다.
        // 하나 추가적으로 생각하기 힘든 함정 같은 부분이 있다면
        // 북엔드를 책이 꽂힌 방향이 아니라 역방향으로도 세울 수 있다는 점이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // n권의 책, 너비 m의 책장, 북엔드의 밑바닥 길이 l
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 각 책의 너비
        int[] widths = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 책들의 너비 총합
        int sum = Arrays.stream(widths).sum();

        // 책장의 너비와 책들의 너비가 일치한다면
        // 북엔드를 사용할 필요가 없다.
        if (sum == m)
            System.out.println(0);
        // 책들의 너비가 책장의 너비보단 작고
        // 책들의 너비가 북엔드의 밑바닥 길이보다 크거나 같을 경우에는
        // 북엔드를 책방향으로 세울 수 있으므로 1개만 필요.
        // 그렇지 않다하더라도 남은 책장의 길이가 북엔드의 길이보다 길다면
        // 북엔드를 반대로 세워 책들을 세울 수 있으므로 1개만 필요한다.
        else if (sum < m && (sum >= l || sum + l <= m))
            System.out.println(1);
        // 그 외의 경우는 불가능한 경우.
        else
            System.out.println(-1);
    }
}