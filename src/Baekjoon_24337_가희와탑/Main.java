/*
 Author : Ruel
 Problem : Baekjoon 24337번 가희와 탑
 Problem address : https://www.acmicpc.net/problem/24337
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24337_가희와탑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일직선으로 n개의 건물이 존재하고
        // 한 방향에서 볼 때 앞선 건물보다 더 높은 건물만 분간할 수 있다고 한다.
        // 왼쪽에서 본 건물의 수 a와 오른쪽에서 본 건물의 수 b가 주어질 때
        // 가능한 건물들의 높이 중 사전순으로 가장 앞서는 것은?
        //
        // 그리디 문제
        // 사전순으로 가장 앞서는 것이기 때문에
        // 가능한 맨 앞 건물 높이 1에 많은 건물들을 배치하는 것이 유리하며
        // 불가능할 경우, 왼쪽에서 봤을 때의 마지막 건물인 가장 높은 높이의 건물 바로 뒤에
        // 높이 1의 건물을 최대한 배치하는 것이 유리하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 건물의 개수 n과 왼쪽에서 센 개수 a, 오른쪽에서 센 개수 b
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        // 만약 a와 b의 합이 n + 1을 넘는다면 불가능한 경우.
        if (a + b > n + 1)
            sb.append(-1);
        else {
            // a와 b 두 값중 큰 값만큼은 최소한 건물들이 구분되는 높이여야한다.
            // 따라서 가장 높은 건물의 높이는 두 값중 큰 값으로 결정.
            int maxHeight = Math.max(a, b);
            // maxHeight 왼쪽에 배치할 수 있는 건물의 개수
            // a가 1일 경우에는 하나도 배치할 수 없고,
            // 그렇지 않을 경우에는 n - b만큼의 건물을 배치할 수 있다.
            int remain = (a == 1 ? 0 : n - b);
            // 높이는 1에서 시작
            int current = 1;
            // 아직 배치할 수 있는 건물이 남은 경우
            while (remain > 0) {
                // 남은 구분할 수 있는 건물의 개수가
                // 배치할 수 있는 개수와 일치한다면 높이를 하나씩 늘려가며 건물을 배치해야한다.
                if (a - current == remain)
                    sb.append(current++).append(" ");
                // 그렇지 않고 배치할 수 있는 여유 개수가 더 많은 경우에는
                // current에 해당하는(사실상 1이다) 건물을 구분할 수 있는 건물의 개수와 같아질 때까지 배치한다.
                else
                    sb.append(current).append(" ");
                remain--;
            }
            // 다시 말해 가능한 높이 1의 건물을 최대한 앞쪽에 배치한다.

            // 가운데 구분되는 가장 높은 건물을 세운다.
            sb.append(maxHeight).append(" ");

            // maxHeight의 오른쪽에 건물을 배치하기 시작한다.
            // 만약 a가 1이었다면 maxHeight 왼쪽에 하나도 건물을 배치하지 못했으므로
            // n - 1개의 건물을 배치해야하고
            // 아니라면 최대한 많은 높이 1의 건물을 배치했을 것이므로
            // 높이가 구분되는 b - 1개의 건물만 배치한다.
            remain = (a == 1 ? n - 1 : b - 1);
            // 높이 b - 1부터 낮아지며 건물들을 배치한다.
            current = b - 1;
            // 배치 개수가 남은 동안
            while (remain > 0) {
                // remain이 더 많이 남았다면 높이 1의 건물을 배치한다.
                // 이는 maxHeight 바로 오른편에서만 일어날 것이다.
                if (remain > current)
                    sb.append(1).append(" ");
                else        // 그 후엔 높이를 하나씩 낮춰가며 건물들을 배치한다.
                    sb.append(current--).append(" ");
                remain--;
            }
        }
        // 다시말해 오른편에 배치할 건물의 개수가 구분되는 건물의 개수보다 많은 경우
        // 1을 최대한 앞쪽에 우선적으로 배치하고, 나머지 구분되는 건물을 차근차근 배치해나간다.

        // 전체 답안 출력.
        System.out.println(sb);
    }
}