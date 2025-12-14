/*
 Author : Ruel
 Problem : Baekjoon 3090번 차이를 최소로
 Problem address : https://www.acmicpc.net/problem/3090
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3090_차이를최소로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] a;
    static int[][] temp;

    public static void main(String[] args) throws IOException {
        // n개의 수가 일렬로 주어진다.
        // 하나의 수를 선택하여 1 감소시키는 연산을 총 t회 할 수 있다.
        // 인접한 수의 차이의 최댓값을 최소화하고자 할 때
        // 해당 배열을 출력하라
        //
        // 그리디, 이분탐색 문제
        // 이분 탐색을 통해, 인근 두 수의 차이의 최댓값의 범위를 좁혀나간다.
        // 해당 차이로 t번 안에 만들 수 있는지 여부는
        // 좌측부터, 원하는 차이 이상의 값일 경우, 값을 변경하는 행위를 하고
        // 그 후, 다시 우측부터 변경하는 행위를 해주며, 총 주어진 횟수 내에 할 수 있는지 체크하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수, 연산 가능 횟수 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 주어지는 n개의 수
        a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            a[i] = Integer.parseInt(st.nextToken());
        // 값을 변경한 내용을 기록할 temp
        temp = new int[2][n];

        // 이분 탐색
        int idx = 0;
        int start = 0;
        int end = 1_000_000_000;
        while (start < end) {
            int mid = (start + end) / 2;
            // t번 내에 차이의 최댓값을 mid로 줄이는 것이 가능한 경우
            if (possible(mid, t, idx % 2)) {
                // end를 mid로 좁힘
                end = mid;
                // idx를 증가시켜, temp[(idx + 1) % 2]의 공간을 사용
                idx++;
            } else  // 불가능한 경우, start를 mid로 좁힘
                start = mid + 1;
        }

        // 답 기록
        StringBuilder sb = new StringBuilder();
        sb.append(temp[(idx + 1) % 2][0]);
        for (int i = 1; i < n; i++)
            sb.append(" ").append(temp[(idx + 1) % 2][i]);
        // 답안 출력
        System.out.println(sb);
    }

    // remain개의 연산 이내에 인근 두 수의 차이를 diff 이하로 만드는 것이 가능한지 판별
    // idx번째 temp 공간을 사용
    static boolean possible(int diff, int remain, int idx) {
        temp[idx][0] = a[0];
        // 우측으로 살펴보며, 오른쪽 수가 왼쪽 수보다 diff보다 더 큰 경우
        // 차이만큼 연산을 시행
        for (int i = 1; i < temp[idx].length; i++) {
            // i번째 수가 i-1번째 수보다 허용되는 차이인 diff보다 얼마나 더 큰 지 계산
            int gap = Math.max(0, a[i] - diff - temp[idx][i - 1]);
            // 차이만큼을 차감하여 기록
            temp[idx][i] = a[i] - gap;
            // 연산 횟수 차감
            remain -= gap;

            // 연산이 부족한 경우, false 반환
            if (remain < 0)
                return false;
        }
        // 마찬가지를 우측에서 좌측으로 살펴보며 진행
        for (int i = temp[idx].length - 2; i >= 0; i--) {
            int gap = Math.max(0, temp[idx][i] - diff - temp[idx][i + 1]);
            temp[idx][i] -= gap;
            remain -= gap;

            if (remain < 0)
                return false;
        }
        // 두 번의 반복문을 모두 통과했다면 가능한 경우
        // true 반환
        return true;
    }
}