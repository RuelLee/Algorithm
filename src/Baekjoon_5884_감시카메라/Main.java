/*
 Author : Ruel
 Problem : Baekjoon 5884번 감시 카메라
 Problem address : https://www.acmicpc.net/problem/5884
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5884_감시카메라;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    static final int LIMIT = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        // (x, y) 위치에 소가 있고, 감시 카메라를 통해
        // 한 수직선 또는 수평선의 모든 소를 감시할 수 있다.
        // 3개의 감시 카메라를 통해 주어지는 n마리의 소를 모두 감시할 수 있는지 판별하라
        // x와 y는 최대 10억까지 주어진다.
        //
        // 브루트포스 알고리즘
        // 이전에 설치한 카메라에 현재 소가 감시되는지 확인하고
        // 그렇지않다면 남은 카메라의 수를 확인하고
        // x좌표와 y좌표 각각에 카메라를 설치했을 때 나머지 소들을 감시할 수 있는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());

        // 각 소들이 서있는 위치
        long[][] points = new long[n][];
        for (int i = 0; i < points.length; i++) {
            points[i] = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
            // x, y 좌표를 서로 겹치지 않는 수로 만들어 판별하기 쉽게 한다.
            points[i][0] *= LIMIT;
        }

        boolean answer = findAnswer(0, 3, new Stack<>(), points);
        // 계산된 결과 출력
        System.out.println(answer ? 1 : 0);
    }

    // idx번째 소가, 설치되어있는 카메라 stack에 감시되는지 확인하고 그렇지 않다면
    // remain 수를 확인하여 새로운 카메라를 설치하며 계산한다.
    static boolean findAnswer(int idx, int remain, Stack<Long> stack, long[][] points) {
        // 마지막 소까지 왔다면 가능한 경우.
        // true 반환
        if (idx >= points.length)
            return true;

        // idx번째 소가 위치한 x or y좌표에 이미 감시카메라가 설치되어있다면
        // 다음 소로 넘어가며, 해당 결과를 반환한다.
        if ((stack.contains(points[idx][0]) || stack.contains(points[idx][1]))
                && findAnswer(idx + 1, remain, stack, points))
            return true;
        // idx번째 소가 이미 설치된 소에 의해 감시되지 않을 때.
        // 남은 감시 카메라 설치 횟수가 남아있다면
        if (remain > 0) {
            // x좌표에 설치하는 경우
            stack.push(points[idx][0]);
            if (findAnswer(idx + 1, remain - 1, stack, points))
                return true;
            stack.pop();

            // y좌표에 설치하는 경우
            stack.push(points[idx][1]);
            if (findAnswer(idx + 1, remain - 1, stack, points))
                return true;
            stack.pop();
        }

        // idx 소가, 이전에 설치되어있던 카메라에 의해 감시되지 않으며
        // idx번째 소의 수직선 혹은 수평선에 카메라를 설치할 경우 모든 소를 감시할 수 없다면
        // false를 반환한다.
        return false;
    }
}