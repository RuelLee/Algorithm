/*
 Author : Ruel
 Problem : Baekjoon 18324번 Race
 Problem address : https://www.acmicpc.net/problem/18324
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18324_Race;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 총 k미터를 달려가며, 처음 속도는 0이다.
        // 매 초마다 속도를 +1, 0, -1 만큼 바꿀 수 있고, 바뀐 속도로 1초 동안 달려간다.
        // 마지막 목적지에 도달할 때, 속도는 x 이하가 되어야한다.
        // 이 때의 최소 시간을 구하라
        // n개의 테스트케이스가 주어진다.
        //
        // 그리디 문제
        // 속도는 높일 수 있을만큼 높이되, 목적지의 속도를 고려해, 낮추는 시간도 생각해야한다.
        // 현재 속도가 x 미만일 때는 무조건 속도를 올려나가며,
        // 속도가 x 이상이 된 경우, 목적지에 도달하기 전에 감속할 때의 x도 같이 고려해주기 시작한다.
        // 속도가 x 이상이더라도 1초간만 속도를 내었다가, 다시 감속할 수도 있으므로
        // 한 번 증가시킨 속도로 누적 거리가 k이상이 된다면 이 땐 한번만 고려해도 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // k미터, n개의 테스트케이스
        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < n; t++) {
            // 도착지의 최대 속도 x
            int x = Integer.parseInt(br.readLine());
            
            // 행동의 수 = 시간
            int count = 0;
            // 누적 거리
            int sum = 0;
            // 현재 속도
            int speed = 0;
            // 누적 거리가 k가 아직 되지 않은 경우에 반복
            while (sum < k) {
                // 속도를 1증가 시킨 것이 x 미만이거나
                // 이번에 속도를 1 증가시켜 sum에 누적시킬 경우, k이상이 되는 경우
                if (++speed < x || sum + speed >= k) {
                    // 행동 한 번으로 속도를 1만 증가
                    count++;
                    sum += speed;
                } else {
                    // 그 외의 경우는
                    // 출발지부터 시작하여 가속하는 경우와
                    // 목적지에 도달하기 전에 감속하는 경우 두 경우를 같이 세어준다.
                    count += 2;
                    sum += 2 * speed;
                }
            }
            // 걸린 시간 기록
            sb.append(count).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}