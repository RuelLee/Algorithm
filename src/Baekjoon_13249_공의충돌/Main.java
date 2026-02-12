/*
 Author : Ruel
 Problem : Baekjoon 13249번 공의 충돌
 Problem address : https://www.acmicpc.net/problem/13249
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13249_공의충돌;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // x축 위에 n개의 공이 놓여있고, 각각의 위치가 주어진다.
        // 공은 모두 1의 속력으로 왼쪽 혹은 오른쪽을 같은 확률로 굴러간다.
        // 반대 방향으로 진행하는 두 공이 부딪친 경우, 서로의 속력은 바뀌지 않으며 진행 방향만 바뀐다.
        // t초 후에 공이 충돌한 횟수의 기댓값을 구하라
        //
        // 확률, 조합 문제
        // 공들이 서로 만났을 때 완성 탄성 충돌을 하므로
        // 거리가 2 * t 이내의 두 공이 서로 마주보는 방향으로 진행할 때만 두 공이 충돌한다.
        // 사이에 어떠한 공이 어떠한 방향으로 진행하더라도 두 공 중 하나와 탄성 충돌하여,
        // 같은 시각에 같은 위치, 같은 속도로 진행할 것이므로 따로 복잡하게 계산하지 않아도 된다.
        // 2 * t 내의 거리를 갖는 두 공을 구하고, 각각이 마주보는 방향으로 진행할 확률 1/4을 곱해주면 기댓값이 나온다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 공
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] balls = new int[n];
        for (int i = 0; i < n; i++)
            balls[i] = Integer.parseInt(st.nextToken());
        // 정렬되서 주어질 것이라고 기대되나, 정렬
        Arrays.sort(balls);

        // 주어진 시간 t
        int t = Integer.parseInt(br.readLine());

        // 2 * t보다 같거나 작은 만큼의 거리를 갖는 두 공의 쌍 개수
        int cnt = 0;
        for (int i = 0; i < n - 1; i++) {
            int j = i;
            while (j + 1 < n && (balls[j + 1] - balls[i] <= 2 * t))
                j++;
            cnt += (j - i);
        }
        // 각각의 쌍이 서로 마주보는 방향으로 진행할 때, 충돌할 수 있다.
        // 마주보는 방향으로 진행할 확률 1/4을 곱해준다.
        System.out.println(cnt / 4.0);
    }
}