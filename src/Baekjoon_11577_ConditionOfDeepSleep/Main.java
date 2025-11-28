/*
 Author : Ruel
 Problem : Baekjoon 11577번 Condition of deep sleep
 Problem address : https://www.acmicpc.net/problem/11577
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11577_ConditionOfDeepSleep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 전구가 일렬로 늘어서있다.
        // 이 중 연속한 k개의 전구의 상태를 반전시킬 수 있다.
        // 최소한의 행동을 통해, 모든 전구를 끄고자할 때, 행동의 횟수는?
        // 불가능하다면 Insomnia를 출력한다.
        //
        // 그리디, 차분 배열 트릭 문제
        // 버튼을 한 번 누름으로써 i번부터, i + k - 1번까지의 전구의 상태가 반전된다.
        // 범위에 대해 값을 변경하는 것이므로, 차분 배열 트릭을 통해, 전구의 상태가 바뀐 횟수를 계산해주자.
        // 그리고 전구의 원래 상태 + 상태가 바뀐 횟수를 통해, 현재 전구의 상태를 찾고, 켜져있다면 끄는 행동을 해주자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 전구, 연속한 k개의 전구의 상태를 반전시킬 수 있음.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 차분 배열 트릭
        // 초기값 입력
        // 처음엔 하나의 전구의 상태만 들어오므로
        // i번째 전구가 켜져있다면 psusm[i]에는 +1, psums[i+1]에는 -1
        int[] psums = new int[n + 1 + k];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            if (Integer.parseInt(st.nextToken()) == 1) {
                psums[i]++;
                psums[i + 1]--;
            }
        }

        int cnt = 0;
        // n - k + 1번 전구까지 상태를 살펴보고, 켜져있다면 자신을 포함한 k개의 전구의 상태를 반전
        for (int i = 1; i <= n - k + 1; i++) {
            psums[i] += psums[i - 1];
            if (psums[i] % 2 == 1) {
                psums[i]++;
                psums[i + k]--;
                cnt++;
            }
        }

        boolean possible = true;
        // 이후 n - k + 2번째 전구부터, n번 전구까지 모두 꺼져있는지를 체크
        // 켜져있다면 불가능한 경우.
        for (int i = n - k + 2; i <= n; i++) {
            psums[i] += psums[i - 1];
            if (psums[i] % 2 == 1) {
                possible = false;
                break;
            }
        }
        // 모든 전구가 꺼져있다면 해당 행동의 횟수를 출력
        // 그 외의 경우 Insomnia를 출력
        System.out.println(possible ? cnt : "Insomnia");
    }
}