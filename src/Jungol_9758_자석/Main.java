/*
 Author : Ruel
 Problem : Jungol 9758번 자석
 Problem address : https://jungol.co.kr/problem/9758
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_9758_자석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지 번호가 메겨진 실험대가 일렬로 놓여있다.
        // 크기가 2 ~ n인 일자 모양 자석을 설치하여
        // 1. n이 놓인 칸의 에너지만큼 충전
        // 2. s가 놓인 칸의 에너지 방큼 소모
        // 3. 자석의 길이 * k 만큼의 에너지가 소모
        // 3가지 현상이 동시에 발생한다 했을 때
        // 한번에 충전할 수 있는 최댓값은?
        //
        // 그리디 문제
        // 떨어진 거리를 고려하여 에너지 차이가 큰 두 위치를 골라야한다.
        // 한 방향으로 탐색해나가면서 현재 위치를 N, 이전 위치를 S로 뒀을 때를 계산한다.
        // i번째를 탐색하는 경우, 현재까지 중 가장 에너지가 작았던 위치와, i-1번째를 비교하여 충전량을 계산한다.
        // 그리고 i-1이 더 작다면 i-1로 위치를 갱신해나간다.
        // 이를 왼쪽 -> 오른쪽, 오른쪽 -> 왼쪽 양방향에 대해 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 실험대, 거리에 따른 에너지 보정 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());

        int ans = Integer.MIN_VALUE;
        // 현재까지 가장 에너지가 작았던 위치
        int lastMinIdx = 0;
        // 왼쪽 -> 오른쪽으로 살펴가며
        // 현재 위치를 N, 이전 위치를 S를 뒀을 때, 가장 높은 충전량을 찾는다.
        for (int i = 1; i < n; i++) {
            // i를 기준으로, 현재 작았던 위치에 대해 소모값
            int dpValue = array[lastMinIdx] + (i - lastMinIdx) * k;
            // 직전 위치에 에너지 소모값
            int preValue = array[i - 1] + k;
            // 두 값을 비교
            if (preValue <= dpValue)
                lastMinIdx = i - 1;
            // 현재 위치에서의 최대 충전량
            ans = Math.max(ans, array[i] - Math.min(dpValue, preValue));
        }

        // 오른쪽 -> 왼쪽으로 살펴가며 계산
        lastMinIdx = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            int dpValue = array[lastMinIdx] + (lastMinIdx - i) * k;
            int preValue = array[i + 1] + k;
            if (preValue <= dpValue)
                lastMinIdx = i + 1;
            ans = Math.max(ans, array[i] - Math.min(dpValue, preValue));
        }
        // 답 출력
        System.out.println(ans);
    }
}