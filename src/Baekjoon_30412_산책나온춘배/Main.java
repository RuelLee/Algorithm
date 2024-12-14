/*
 Author : Ruel
 Problem : Baekjoon 30412번 산책 나온 춘배
 Problem address : https://www.acmicpc.net/problem/30412
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30412_산책나온춘배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 타워가 주어진다.
        // 양 옆에 있는 타워들과 높이 차이를 x이상 갖는 타워를 하나 만들고 싶다.
        // 원하는 타워의 높이를 1 높일 수 있고, 이를 한 번의 시행이라고 한다.
        // 가장자리에 있는 타워는 바로 옆 타워하고만 높이를 비교하면 된다.
        // 양 옆에 있는 타워들과 높이 차이가 x 이상 나는 타워를 만들기 위한 최소 시행은?
        //
        // 브루트 포스 문제
        // 타워의 높이만 높일 수 있으므로
        // 먼저 가장 자리의 타워를 생각해보면
        // 자신의 옆에 있는 타워와의 차이에서 x와의 차만큼을 자신 혹은 옆의 타워의 높이를 높이면 된다.
        // 그 외의 양 옆에 타워가 있는 타워들은 3가지 경우에 대해 생각해보면서 계산한다
        // 1. 해당 타워가 두 타워보다 작은 경우
        // 2. 해당 타워가 한 타워보다는 작고, 한 타워보다는 큰 경우
        // 3. 해당 타워가 두 타워보다 큰 경우
        // 를 타워의 높이를 조정하여 만들되, 만드는 시행의 횟수를 비교한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 타워, 필요한 높이 차이 x
        int n = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        // 타워들
        long[] towers = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < towers.length; i++)
            towers[i] = Integer.parseInt(st.nextToken());
        
        // 필요한 최소 시행
        // 가장 자리 타워에서 필요한 시행 횟수를 초기값으로 한다.
        // 첫번째 타워와 두번째 타워의 높이 차와 x의 차만큼과
        // 마지막 타워와 마지막에서 두번째 타워의 높이 차와 x의 차만큼을 비교하여 더 적은 값을 취하되
        // 음수의 값이 될 수 있으므로, 음수의 경우 0이 되도록 한다.
        
        long answer = Math.max(0, x - Math.max(Math.abs(towers[0] - towers[1]), Math.abs(towers[n - 1] - towers[n - 2])));
        for (int i = 1; i < towers.length - 1; i++) {
            // 두 타워 중 작은 높이와 큰 높이
            long min = Math.min(towers[i - 1], towers[i + 1]);
            long max = Math.max(towers[i - 1], towers[i + 1]);

            // 두 타워보다 해당 타워가 작은 경우.
            // 두 타워 모두 towers[i] + x와 같거나 커야한다.
            answer = Math.min(answer, (min >= towers[i] + x ? 0 : towers[i] + x - min) +
                    (max >= towers[i] + x ? 0 : towers[i] + x - max));

            // towers[i]가 min보다는 크고, max보다는 작은 경우
            // towers[i]를 min + x보다 같거나 크게 만들어야하고
            // max는 towers[i] 혹은 min + x 중 큰 값보다 x만큼이 더 커야한다.
            answer = Math.min(answer, (towers[i] >= min + x ? 0 : min + x - towers[i]) +
                    (max >= Math.max(min + x, towers[i]) + x ? 0 : (Math.max(min + x, towers[i]) + x - max)));

            // 양 옆의 타워보다 해당 타워가 큰 경우
            // towers[i]가 max + x와 같거나 크면 된다.
            answer = Math.min(answer, towers[i] >= max + x ? 0 : (max + x - towers[i]));
        }
        // 답 출력
        System.out.println(answer);
    }
}