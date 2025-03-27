/*
 Author : Ruel
 Problem : Baekjoon 1069번 집으로
 Problem address : https://www.acmicpc.net/problem/1069
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1069_집으로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 은진이는 현재 (x, y)에 있고, (0, 0)에 있는 집으로 돌아가고자 한다.
        // 이동 방법은 두가지가 있는데
        // 1초에 1만큼 걸어서 이동하는 방법과, t에 d만큼 점프를 하는 방법이 있다.
        // 점프는 일직선으로 정확하게 d만큼만 이동해야한다.
        // 두 방법을 적절히 섞어 최소 시간으로 집으로 도달하고자할 때
        // 그 시간은?
        //
        // 조건이 많은 분기, 기하 문제
        // 먼저 점프에 대해 생각해봐야할 것이
        // 점프할 때의 단위 시간이 단위 거리보다 같거나 커선 안된다. 그냥 걸어가는게 유리해지기 때문
        // 점프를 두 번 이상할 경우, 그 각도를 조절하여 도달하는 일직선 거리를 조절할 수 있다.
        // 지문에서 정확히 d라고 했지만, 점프를 n회 할 경우
        // (n - 1) * d ~ n * d까지의 거리를 각도 조절을 통해 거리를 조절할 수 있다.
        // 점프를 한 번만 할 경우, 집을 넘어서 갔다가 다시 되돌아오는 경우도 있을 수 있다.
        // 위 경우들을 고려하여 문제를 해결한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 현재 좌표 x, y
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        // 점프 조건 t시간 동안 d거리를 뛴다.
        int d = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 집까지의 대각선 거리
        double distance = Math.sqrt(x * x + y * y);
        double answer = distance;

        // 점프를 두번하기엔 적은 거리라면
        // 그래도 t가 적다면 점프를 두번하는 것이 유리할 수도 있다.
        // 점프를 두번하는 경우와 점프를 한 번 하고 남은 거리를 걸어가거나, 지나친 거리를 되돌아가는 방법을 계산한다.
        if (distance < 2 * d)
            answer = Math.min(answer, Math.min(2 * t, t + Math.abs(distance - d)));
        // 점프를 여러번 하는 경우
        // 점프를 최소한으로 하고, 남은 거리를 걸어가는 방법과
        // 각도 조절을 통해 점프를 최소한 + 1번 하여 집으로 가는 경우를 계산.
        else
            answer = Math.min(answer, t * (int) (distance / d) + Math.min(distance % d, t));

        // 답 출력
        System.out.println(answer);
    }
}