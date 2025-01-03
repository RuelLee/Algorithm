/*
 Author : Ruel
 Problem : Baekjoon 32984번 겨울이 좋아
 Problem address : https://www.acmicpc.net/problem/32984
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32984_겨울이좋아;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] a, b;

    public static void main(String[] args) throws IOException {
        // n그루의 나무가 있고, i번째 나무는 Ai개 만큼 나뭇잎이 달려있고, 
        // 매일 Bi개 만큼씩 나뭇잎이 떨어진다고 한다.
        // 모든 나무의 나뭇잎이 떨어진 날부터를 겨울이라고 부른다.
        // 정우는 능력을 통해, 하루에 한 번, 한 나무에서 떨어지는 나뭇잎의 개수를 2배로 만들 수 있다.
        // 첫 나뭇잎이 떨어지는 날을 1일째라고 할 때, 가장 빠르게 겨울이 오는 날은 언제인가
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해, x일에 x번 이하의 능력을 사용하여, 모든 나무의 나뭇잎이 떨어지게 할 수 있는가를 따져보면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n그루의 나무
        int n = Integer.parseInt(br.readLine());

        // 각각 나무에 달린 나뭇잎의 수
        StringTokenizer st = new StringTokenizer(br.readLine());
        a = new int[n];
        for (int i = 0; i < a.length; i++)
            a[i] = Integer.parseInt(st.nextToken());

        // 날마다 떨어지는 나뭇잎의 수
        st = new StringTokenizer(br.readLine());
        b = new int[n];
        for (int i = 0; i < b.length; i++)
            b[i] = Integer.parseInt(st.nextToken());

        // 이분 탐색
        int start = 1;
        int end = 1_000_000_000;
        while (start < end) {
            int mid = (start + end) / 2;
            // mid일에 겨울이 오게 하는 것이 가능하다면
            // start ~ mid까지의 범위로 줄여 탐색
            if (possible(mid))
                end = mid;
            else        // 불가능하다면 mid + 1 ~ end까지의 범위로 줄여 탐색
                start = mid + 1;
        }
        // 이분 탐색을 통해 찾은 최소일 출력
        System.out.println(start);
    }

    // days 날에 겨울이 오게하는 것이 가능한지 판별한다.
    static boolean possible(int days) {
        long actions = 0;
        for (int i = 0; i < a.length; i++) {
            // 자연적으로 떨어진 나뭇잎을 제외하고 남은 나뭇잎의 수.
            long remain = a[i] - (long) b[i] * days;
            // remain이 0보다 크다면, 능력을 사용하여, 잔여 나뭇잎들을 떨어뜨려야한다.
            // 남은 나뭇잎을 b[i]로 나눠 올림한 개수만큼 능력을 사용해야한다.
            if (remain > 0)
                actions += (remain + b[i] - 1) / b[i];

            // 만약 능력을 사용한 횟수가 days보다 커졌다면, 불가능한 경우. false 반환
            if (actions > days)
                return false;
        }
        // 위 반복문을 통과했다면 가능한 경우. true 반환
        return true;
    }
}