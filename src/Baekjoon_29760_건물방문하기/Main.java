/*
 Author : Ruel
 Problem : Baekjoon 29760번 건물 방문하기
 Problem address : https://www.acmicpc.net/problem/29760
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29760_건물방문하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // h층, 각층에 w호를 갖는 건물이 주어진다.
        // 같은 층에 인접한 방으로 이동하는데는 1초,
        // 다른 인접한 층, 같은 호로 이동하는데는 100초가 걸린다.
        // 방문하고자하는 방이 n개 주어질 때
        // 모든 방문을 마치는 최소 시간은?
        //
        // DP 문제
        // 층을 옮겨다니는데 100초가 걸리므로, 층을 이동하는 횟수는 최소로 해야한다.
        // 따라서 한 층에서 방문할 수 있는 방을 모두 방문하고 다음 층으로 넘어가야한다.
        // 각 층마다 방문해야하는 방의 최소 호와 최대 호를 계산해두고
        // 이를 바탕으로 한 층에 모든 방문을 마치고, 최소 호에 남아있는 경우와 최대 호에 남아있는 경우
        // 두 경우를 기준으로 DP를 채워 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 방문해야하는 방
        int n = Integer.parseInt(st.nextToken());
        // h * w 크기의 건물
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        // 방문해야하는 방들을 층마다 나눠 최소 호와 최대 호를 계산한다.
        int[][] targetMinMax = new int[h + 1][2];
        for (int i = 0; i < targetMinMax.length; i++) {
            targetMinMax[i][0] = Integer.MAX_VALUE;
            targetMinMax[i][1] = 0;
        }

        // 편의상 0층 1호에서 시작한다고 가정
        targetMinMax[0][0] = targetMinMax[0][1] = 1;
        // 방문해야하는 최대 층수
        int maxFloor = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int floor = Integer.parseInt(st.nextToken());
            maxFloor = Math.max(maxFloor, floor);
            int room = Integer.parseInt(st.nextToken());
            targetMinMax[floor][0] = Math.min(targetMinMax[floor][0], room);
            targetMinMax[floor][1] = Math.max(targetMinMax[floor][1], room);
        }
        
        // 현재 최소, 최대 호의 값
        int[] currentRoom = new int[2];
        Arrays.fill(currentRoom, 1);
        // 최소 호에 이르는 최소 시간과 최대 호에 이르는 최소 시간
        int[] dp = new int[2];
        int[] temp = new int[2];
        for (int i = 1; i <= maxFloor; i++) {
            // 만약 방문해야하는 방이 없는 층이라면 건너뛴다.
            if (targetMinMax[i][0] == Integer.MAX_VALUE)
                continue;

            Arrays.fill(temp, 0);
            // 최소 호와 최대 호 간의 거리
            int distance = targetMinMax[i][1] - targetMinMax[i][0];
            // 최소 호에 마지막으로 도달하는 경우는
            // 이전 최소 호 -> 이번 최대 호 -> 이번 최소 호로 이동하는 경우와
            // 이전 최대 호 -> 이번 최대 호 -> 이번 최소 호로 이동하는 두 가지 경우 중 더 짧은 시간이 걸리는 경우를 택한다.
            temp[0] = Math.min(Math.abs(targetMinMax[i][1] - currentRoom[0]) + distance + dp[0],
                    Math.abs(targetMinMax[i][1] - currentRoom[1]) + distance + dp[1]);
            // 최대 호에 마지막으로 도달하는 경우는
            // 이전 최소 호 -> 이번 최소 호 -> 이번 최대 호
            // 이전 최대 호 -> 이번 최소 호 -> 이번 최대 호
            // 마찬가지로 계산
            temp[1] = Math.min(Math.abs(targetMinMax[i][0] - currentRoom[0]) + distance + dp[0],
                    Math.abs(targetMinMax[i][0] - currentRoom[1]) + distance + dp[1]);
            // 현재 방의 위치 변경
            currentRoom = targetMinMax[i];
            // temp에 기록된 값 dp로 이동
            dp[0] = temp[0];
            dp[1] = temp[1];
        }
        // dp에 기록된 값 중 더 작은 값에
        // 이동한 층수 * 100한 값을 더해 출력
        System.out.println(Math.min(dp[0], dp[1]) + (maxFloor - 1) * 100);
    }
}