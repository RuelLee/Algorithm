/*
 Author : Ruel
 Problem : Baekjoon 12934번 턴 게임
 Problem address : https://www.acmicpc.net/problem/12934
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12934_턴게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 사람이 게임을 진행한다.
        // i번째 게임을 이길 경우, i점을 획득한다.
        // 두 사람이 얻은 점수가 x, y일 때, 해당 점수를 얻는 것이 가능하다면
        // x점을 얻기 위해 이겨야하는 게임의 최소 횟수
        // 불가능하다면 -1을 출력한다.
        //
        // 그리디 문제
        // i번째 게임을 이길 때마다 i점을 획득하므로
        // 두 사람의 점수 합은 n * (n + 1) / 2를 만족해야한다.
        // 따라서 이분탐색을 통해 해당하는 n을 찾고, 정확히 값이 일치하는지 확인한다.
        // 그 후, 찾은 n값이 한번에 최대 얻을 수 있는 점수이므로
        // x점에서 n값을 하나씩 줄여나가며 값을 뺀다.
        // 그러다 x가 n보다 작아진다면, 해당 점수는 한번에 얻을 수 있으므로
        // 그 횟수를 계산하여 출력하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // x점과 y점
        long x = Long.parseLong(st.nextToken());
        long y = Long.parseLong(st.nextToken());

        // x와 y가 최대 1조로 주어지므로
        // n * (n + 1) / 2가 최대 2조이다.
        // 따라서 가능한 n은 최대 200만.
        // 이분 탐색을 통해 빠르게 값의 범위를 줄여준다.
        long start = 0;
        long end = 2_000_000;
        while (start < end) {
            long mid = (start + end) / 2;
            if (mid * (mid + 1) / 2 < x + y)
                start = mid + 1;
            else
                end = mid;
        }
        // 찾은 n 값은 end

        StringBuilder sb = new StringBuilder();
        // 두 수의 합이 연속한 자연수의 합으로 표현될 수 없다면
        // -1을 기록
        if (end * (end + 1) / 2 != x + y)
            sb.append(-1);
        else {
            // 가능하다면
            int count = 0;
            // x가 end보다 큰 경우에는 end를 하나씩 빼가며 횟수를 센다.
            while (x > end) {
                x -= end--;
                count++;
            }
            // 만약 x가 0보다 큰 값이 남았다면
            // 이는 해당하는 번째의 게임을 한번만 승리하면 얻을 수 있는 값이다.
            // 따라서 0보다 크다면 count + 1회,
            // 0이라면 이미 점수를 모두 얻었으므로 count회를 기록한다.
            sb.append(x > 0 ? count + 1 : count);
        }
        // 답안 출력
        System.out.println(sb);
    }
}