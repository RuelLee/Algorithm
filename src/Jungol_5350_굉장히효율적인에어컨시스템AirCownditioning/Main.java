/*
 Author : Ruel
 Problem : Jungol 5350번 굉장히 효율적인 에어컨 시스템 (Air Cownditioning)
 Problem address : https://jungol.co.kr/problem/5350
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5350_굉장히효율적인에어컨시스템AirCownditioning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 방의 현재 온도와 만들고자 하는 온도가 주어진다.
        // 연속한 여러 방을 모아 한번에 온도 변화를 줄 수 있다고 한다.
        // 최소 동작으로 모든 방을 원하는 온도로 맞추고자 할 때, 그 횟수는?
        //
        // 그리디 문제
        // 온도 변화를 줄 때, 부호로 크기가 커져간다면, 점점 앞 부분을 제외해가며, 큰 명령 횟수로 조절할 수 있다.
        // 예를 들어 각 방의 온도 변화를 +1, +2, +3으로 주고할 때, 처음엔 1 ~ 3까지의 방에 +1, 다음엔 2 ~ 3에 +1, 3에만 1
        // 총 세번의 명령으로 가능하다
        // 반대로 크기가 작아져간다면 역시 동일 횟수로 가능하나, 단 다음 연속적인 변화를 주는 값의 크기가
        // 현재까지 조절한 마지막 방의 시점을 기준으로 계산된다.
        // +1, +2, +3인 경우, 그 다음 방이 0 ~ 3까지의 변화를 요구하는 경우, 추가적인 명령 없이 포함가능하지만
        // +3, +2, +1인 경우, 다음 방이 0 ~ 1까지만 추가적인 명령이 필요없고, 2이상인 경우는 추가적인 명령이 필요하다.
        // 음수 방향으로도 동일하게 가능

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 방
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 현재 온도
        int[] p = new int[n];
        for (int i = 0; i < n; i++)
            p[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        // 원하는 온도
        int[] t = new int[n];
        for (int i = 0; i < n; i++)
            t[i] = Integer.parseInt(st.nextToken());

        // 전체 명령의 수
        int totalMove = 0;
        // 마지막 변화량
        int preDiff = 0;
        for (int i = 0; i < n; i++) {
            // 현재 원하는 변화량
            int currentDiff = t[i] - p[i];
            // 변화의 방향이 같은 경우
            if (preDiff * currentDiff > 0) {
                // 현재 변화량이 클 경우, 해당 값만큼 추가 명령이 필요
                if (Math.abs(currentDiff) >= Math.abs(preDiff))
                    totalMove += Math.abs(currentDiff - preDiff);
            } else  // 변화량의 방향이 다를 경우, 구간을 분리하여 온전히 0에서 다시 시작
                totalMove += Math.abs(currentDiff);
            // 현재 변화량을 기록
            preDiff = currentDiff;
        }
        // 전체 명령의 수 출력
        System.out.println(totalMove);
    }
}