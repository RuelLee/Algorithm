/*
 Author : Ruel
 Problem : Baekjoon 22942번 데이터 체커
 Problem address : https://www.acmicpc.net/problem/22942
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22942_데이터체커;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // x축 위에 존재하는 원들의 x좌표와 r반지름이 주어진다.
        // 서로 만나는 원이 하나도 없다면 YES,
        // 만나는 원이 하나라도 있다면 NO를 출력한다
        //
        // 정렬, 스택 문제
        // 원과 x축이 만나는 두 점을 통해 범위를 찾고,
        // 이를 통해 원들이 서로 접하거나 두 점에서 만나는지 판별하려했으나 시간 초과.
        // 사실 괄호와 같은 문제였다.
        // 원과 x축이 접하는 두 점에 대해 원의 번호와 위치를 기록한 후
        // 위치에 따라 정렬한다
        // 그 후 차례대로 점을 살펴보며, 스택 최상단과 같은 원의 점이 나타났다면
        // 스택의 최상단을 제거하고
        // 다른 원의 점이 나타났다면 스택에 추가해나간다.
        // 최종적으로 모든 점을 살펴봤을 때,
        // 스택이 비어있다면 모든 원들이 자신의 짝을 만나 사라진 것이고
        // 차있다면 원들끼리 서로 겹치는 경우가 발생하여 안 사라진 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 원
        int n = Integer.parseInt(br.readLine());

        // x와 접하는 점의 개수는 원 x 2개
        int[][] points = new int[n * 2][2];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // i, n+i번째 점에는 i번째 원이 x축과 만나는 두 점이 들어간다.
            points[i][0] = points[n + i][0] = i;
            // 왼쪽에서 만나는 점
            points[i][1] = x - r;
            // 오른쪽에서 만나는 점
            points[n + i][1] = x + r;
        }
        // 위치순 정렬
        Arrays.sort(points, Comparator.comparingInt(o -> o[1]));

        // 순차적으로 살펴보며 짝을 만나지 못한 경우 스택에 집어넣는다.
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < points.length; i++) {
            if (!stack.isEmpty()) {     // 스택이 비어있지 않고
                // 스택 최상단에 i번째 점의 짝이 있는 경우
                // 스택 최상단 제거
                if (points[i][0] == points[stack.peek()][0])
                    stack.pop();
                // 만약 스택의 최상단과 i번째 점이 같은 위치라면
                // 접하는 경우. 바로 반복문 종료
                else if (points[i][1] == points[stack.peek()][1])
                    break;
                else        // 그 외의 경우는 스택에 추가
                    stack.push(i);
            }
            else        // 스택이 비어있는 경우도 스택에 추가
                stack.push(i);
        }

        // 스택이 비어있다면 모두 짝을 만난 경우 YES 출력
        // 그렇지 않다면 NO 출력
        System.out.println(stack.isEmpty() ? "YES" : "NO");
    }
}