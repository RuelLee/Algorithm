/*
 Author : Ruel
 Problem : Baekjoon 13711번 LCS 4
 Problem address : https://www.acmicpc.net/problem/13711
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13711_LCS4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 n이고 1 ~ n까지의 수가 한번씩 등장하는 수열
        // A, B가 주어진다.
        // 두 수열 모두의 부분 수열이 되며, 길이가 가장 큰 부분 수열의 길이를 구하라
        //
        // 최장 증가 수열 문제
        // 먼저, A에 대해서는 해당 수가 몇번째 순서에 등장했는지를 기록한다.
        // 그 후, B에 대해서는 해당 수가 A에서 등장한 순서로 기록한다.
        // 이렇게 기록해두면, B에 기록된 수를 최장 증가 수열로 만들면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 N의 두 수열 A, B
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // A에 대해서는 해당 수가 몇번째에 등장했는지 순서를 기록한다.
        int[] a = new int[n + 1];
        for (int i = 0; i < n; i++)
            a[Integer.parseInt(st.nextToken())] = i + 1;
        // b에 대해서는 해당 수가 A에서 몇번째에 등장했는지를 기록한다.
        int[] b = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            b[i] = a[Integer.parseInt(st.nextToken())];

        // 이제 B에 대해서 최장 증가 수열의 길이를 구한다.
        // 이분 탐색을 활용.
        int[] order = new int[n];
        int length = 0;
        for (int i = 0; i < n; i++) {
            // b[i]가 들어갈 순서를 찾는다.
            int idx = Arrays.binarySearch(order, 0, length, b[i]);
            // 일치하는 수가 없다면 음수값을 반환하는데 이에 값 보정
            if (idx < 0)
                idx = (idx + 1) * -1;
            // 해당 위치에 b[i]값을 대입
            order[idx] = b[i];
            // 길이의 변화 확인
            length = Math.max(length, idx + 1);
        }
        // 답 출력
        System.out.println(length);
    }
}