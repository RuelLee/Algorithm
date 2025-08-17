/*
 Author : Ruel
 Problem : Baekjoon 10477번 경운기
 Problem address : https://www.acmicpc.net/problem/10477
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10477_경운기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // (0, 0) ~ (a, b) 크기의 공간이 주어지고, 시작 위치는 (0, 0)이다.
    // 각 턴마다 x축 혹은 y축과 평행한 방향으로 이동한다.
    // n번째 움직임은 선택한 방향으로 2^(n-1)칸 움직인다.
    // 방문할 수 있는 좌표의 총 개수는 몇 개인가?
    //
    // 브루트 포스
    // 각 움직임마다 2의 배수만큼 움직일 수 있는 칸의 개수가 커진다.
    // 이를 x축이나 y축의 방향 중 하나만 이동할 수 있으므로
    // n번째 턴에 움직일 수 있는 좌표 (i, j)는 2^(n + 1) - 1 = i + j이다.
    // 따라서 n번째 턴까지 움직이는 칸의 총 합이 a + b보다 같거나 작은 동안
    // 해당 좌표 내에 들어가는 점의 개수를 세어준다.

    static long[] pows;
    public static void main(String[] args) throws IOException {
        // 2의 제곱을 미리 계산해둠
        pows = new long[35];
        pows[0] = 1;
        for (int i = 1; i < pows.length; i++)
            pows[i] = pows[i - 1] * 2;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 좌표의 크기 (0, 0) ~ (a, b)
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            long sum = 0;
            // i번째 움직임마다
            for (int i = 0; pows[i] - 1 <= a + b; i++) {
                // 움직인 칸의 총 합이 a보다 같거나 작고
                if (pows[i] - 1 <= a) {
                    // b보다도 같거나 작다면
                    // 총 0 ~ 2^i - 1까지 총 2^i개의 점을 찍는 것이 가능
                    if (pows[i] - 1 <= b)
                        sum += pows[i];
                    else    // b가 pows[i] - 1 보다 작다면 b + 1개의 점이 가능
                        sum += b + 1;
                } else {
                    // 움직인 칸의 총합이 a보다 크고
                    // b보다는 같거나 작다면
                    if (pows[i] - 1 <= b)       // a+ 1개의 점을 방문 가능
                        sum += a + 1;
                    else    // b보다도 크다면, a + b + 1 - (2^i - 1)개만큼이 가능
                        sum += a + b - pows[i] + 2;
                    // 이 경우가 조금 생각하기 까다롭다.
                    // i번째 움직임일 때, 찍을 수 있는 점이 대각선의 형태로 2^i - 1개 찍히게 된다.
                    // 이 중 (a, b) 범위 내의 점만 체크하고, 범위 밖은 제외해야한다.
                    // 알다시피 각 대각선에서 x와 y의 값은 고유하다.
                    // 따라서 따라서 x + y 에서 2^i - 1값을 빼준 뒤, 남은 값만큼을 x에 추가로 배치하거나 y에 추가로 배치하는 관점으로 본다
                }
            }
            sb.append(sum).append("\n");
        }
        System.out.print(sb);
    }
}