/*
 Author : Ruel
 Problem : Baekjoon 30640번 운전 연습
 Problem address : https://www.acmicpc.net/problem/30640
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30640_운전연습;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 차를 운전해 항상 양의 방향으로만 움직인다.
        // 시작점을 포함한 n + 1개의 충전소에 대한 위치와 충전량이 주어진다.
        // 차는 충전량 만큼의 거리를 이동할 수 있다.
        // 시작점을 제외한 충전소에는 마법사가 숨어있어, 이전 충전소 중 하나로 돌려보낸다.
        // 재밌게도 돌아갈 충전소는 운전자가 선택할 수 있다.
        // 시작점에 가장 멀리 가고자할 때,
        // 각 충전소마다 되돌아가야할 이전 충전소의 번호와
        // 처음 해당 충전소를 도착했을 때와, 돌아가고 나서 다시 도착했을 때의 충전 변동량을 출력하라
        //
        // 누적합 문제
        // 먼저 i 충전소에서 i+1 충전소로 갈 수 있는지를 판별해야한다.
        // 이는 현재까지의 충전량 - 현재 충전소의 위치로 쉽게 구할 수 있다. 누적합 처리를 해주면 연산을 줄일 수 있다.
        // 그래서 i+1 충전소에 도달할 수 있다면, 이젠 되돌아가야할 충전소를 선택해야한다.
        // maxIdx는 이전까지 가장 유리했던 돌아갈 충전소를 나타낸다.
        // 따라서 i+1 -> maxIdx로 돌아가는 경우와 i+1 -> i로 돌아가는 경우를 비교하여
        // 두 경우 중 어느 것이 더 유리한지 판별하고, i가 더 유리하다면 maxIdx를 i로 갱신해준다.
        // 위 과정을 반복하며 답을 계산해주자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 시작점을 제외한 n개의 충전소
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        // 충전소 정보
        long[][] stations = new long[n + 1][2];
        for (int i = 0; i < stations.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < stations[i].length; j++)
                stations[i][j] = Long.parseLong(st.nextToken());
        }

        int maxIdx = 0;
        StringBuilder sb = new StringBuilder();
        // 마법사를 만나지 않고서 현재 충전소에 도달하는 것이 불가능해진다면
        // 당연히 이후 모든 충전소에 도달하는 것이 불가능하다.
        boolean impossible = false;

        // 첫번째 충전소에는 되돌아갈 충전소가 시작점 밖에 없다.
        // 해당 충전소에 도달할 수 없는 경우.
        if (stations[0][1] < stations[1][0]) {
            impossible = true;
            sb.append(-1).append(" ").append(-1).append("\n");
        } else {
            // 있는 경우
            sb.append(maxIdx).append(" ").append(stations[0][1] - stations[1][0]).append("\n");
            // 충전량은 누적합 처리를 해준다.
            stations[1][1] += stations[0][1];
        }

        for (int i = 2; i < stations.length; i++) {
            // 이미 충전소에 도달하는 것이 불가능해졌거나, 이번 충전소부터 불가능해지는 경우
            if (impossible ||
                    stations[i - 1][1] < stations[i][0]) {
                impossible = true;
                sb.append(-1).append(" ").append(-1).append("\n");
                continue;
            }
            
            // 충전량 누적합 처리
            stations[i][1] += stations[i - 1][1];
            // i-1 주유소로 되돌아가는 경우
            long preFuel = stations[i - 1][1] - stations[i - 1][0] - (stations[i][0] - stations[i - 1][0]) * 2 + stations[i - 1][1] - stations[i - 2][1];
            // maxIdx 주유소로 돌아가는 경우
            long maxFuel = stations[i - 1][1] - stations[i - 1][0] - (stations[i][0] - stations[i - 1][0]) + (stations[i - 1][1] - (maxIdx - 1 >= 0 ? stations[maxIdx - 1][1] : 0)) - (stations[i][0] - stations[maxIdx][0]);
            // i-1 주유소로 돌아가는 것이 같거나 유리한 경우
            if (preFuel >= maxFuel) {
                sb.append(i - 1).append(" ").append(stations[i - 1][1] - stations[i - 2][1] - (stations[i][0] - stations[i - 1][0])).append("\n");
                maxIdx = i - 1;
            } else      // maxIdx로 돌아가는 것이 유리한 경우
                sb.append(maxIdx).append(" ").append(stations[i - 1][1] - (maxIdx - 1 >= 0 ? stations[maxIdx - 1][1] : 0) - (stations[i][0] - stations[maxIdx][0])).append("\n");
        }
        // 전체 답 출력
        System.out.println(sb);
    }
}