/*
 Author : Ruel
 Problem : Jungol 5333번 사진촬영2 (Photoshoot 2)
 Problem address : https://jungol.co.kr/problem/5333
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5333_사진촬영2_Photoshoot2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 사진 촬영
        // n마리의 각 소들의 번호가 메겨져있고, 현재 소들이 나열된 순서가 길이 n의 수열 a로 주어진다.
        // 해당 순서를 길이 n의 수열 b로 바꾸고자 할 때
        // 움직여야하는 소의 최소 수는?
        //
        // 최장 증가 부분 수열 문제
        // a와 b를 비교해, 같은 순서인 소들의 최대 길이를 구한다.
        // 나머지가 이동이 필요한 소들의 수

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 현재 나열된 순서
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] b = new int[n + 1];
        // 바꾸려는 배치에
        // idx번 소가 몇 번째에 놓여있는지를 기록한다.
        for (int i = 0; i < n; i++)
            b[Integer.parseInt(st.nextToken())] = i + 1;

        // 이분 탐색
        int[] order = new int[n + 1];
        Arrays.fill(order, Integer.MAX_VALUE);
        int length = 0;
        for (int i = 0; i < n; i++) {
            // a[i] 소가
            // 원하는 배치에서는 몇 번째에 있는지를 확인하며
            // 최장 증가 부분 수열에서 최대 몇 번째에 속할 수 있는지를 찾는다.
            int start = 1;
            int end = length + 1;
            while (start < end) {
                int mid = (start + end) / 2;
                if (order[mid] > b[a[i]])
                    end = mid - 1;
                else
                    start = mid + 1;
            }
            // a[i]가 놓이는 순서에 b에서의 순서를 기록해둔다.
            order[end] = Math.min(order[end], b[a[i]]);
            // 길이 갱신
            length = Math.max(length, end);
        }
        // 최대 길이만큼의 소는 움직일 필요가 없다.
        // 나머지 수의 소들을 옮긴다.
        // 답 출력
        System.out.println(n - length);
    }
}