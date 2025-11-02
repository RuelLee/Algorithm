/*
 Author : Ruel
 Problem : Baekjoon 16974번 레벨 햄버거
 Problem address : https://www.acmicpc.net/problem/16974
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16974_레벨햄버거;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 햄버거는 다음과 같이 정의된다.
        // 레벨 0 : 패티
        // 레벨 L : 햄버거번, 레벨 L-1 햄버거, 패티, 레벨 L-1 햄버거, 햄버거번
        // 으로 이루어져있다.
        // n레벨의 햄버거의 아래 x장을 먹었을 때, 먹은 패티의 수는?
        //
        // 분할 정복
        // 햄버거의 형태가 안에 -1레벨의 햄버거를 두개 안에 품고 있는 형태로 주어진다.
        // 따라서, 각 레벨마다, 총 레이어의 수와 패티의 수를 미리 계산해두고
        // 아래의 햄버를 온전히 포함한다면 미리 계산해둔 피티의 수 + 윗부분을 레벨을 낮춰 계산하고
        // 아랫부분을 온전히 포함하지 않는다면, 햄버거 번을 제외하고, 해당 부분만 레벨을 낮춰 계산한다.
        
        // layers[i][0] = i레벨의 햄버거의 총 레이어 수
        // layers[i][1] = i레벨의 햄버거의 패티의 수
        long[][] layers = new long[51][2];
        layers[0][0] = layers[0][1] = 1;
        for (int i = 1; i < layers.length; i++) {
            layers[i][0] = 3 + layers[i - 1][0] * 2;
            layers[i][1] = layers[i - 1][1] * 2 + 1;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n레벨의 햄버거를 아래에서 x장 먹는다.
        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());

        long answer = 0;
        // 레벨도, 먹을 장수도 0보다 큰 경우
        while (x > 0 && n > 0) {
            // 아래의 햄버거를 온전히 포함하는 경우
            if (x >= layers[n - 1][0] + 2) {
                // 미리 계산해둔 패티의 수와 중앙의 패티의 수 누적
                answer += layers[n - 1][1] + 1;
                // 그 후, x 수 차감 혹은 가장 윗부분의 햄버거 번이 포함된다면 번은 제외
                x = Math.min(x - layers[n - 1][0] - 2, layers[n - 1][0]);
            } else      // 가장 아래 햄버거 번만 제외한다.
                x--;
            // 레벨을 낮춰 다시 계산
            n--;
        }
        // 0레벨일 때까지 도달했다면, 나머지는 전부 패티
        // 개수 누적
        answer += x;
        // 답 출력
        System.out.println(answer);
    }
}