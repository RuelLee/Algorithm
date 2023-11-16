/*
 Author : Ruel
 Problem : Baekjoon 27979번 볼링장 아르바이트
 Problem address : https://www.acmicpc.net/problem/27979
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27979_볼링장아르바이트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 볼링공과 무게가 주어진다.
        // 이 볼링공들을 정렬하여 i <= j 일 경우 wi <= wj를 만들고 싶다.
        // 정렬을 할 때는
        // 한 공을 빼내어, 가장 앞으로 이동하는 방법만 가능하다고 한다.
        // 최소 이동 횟수는?
        //
        // 스택 문제
        // 어느 위치에서나 공을 빼낼 수 있기 때문에
        // 위치 정렬을 하지 않아도 되는 공의 개수를 구하면 된다.
        // 이는 뒤에서부터 살펴보며, 가장 뒤에오는 무거운 공들이 순서대로 위치하고 있는가를 살펴보면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 공과 무게
        int n = Integer.parseInt(br.readLine());
        int[] balls = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 스택
        Stack<Integer> stack = new Stack<>();
        // 스택에서 버려진 공들중 가장 무거운 무게
        int discardedMax = 0;
        for (int i = balls.length - 1; i >= 0; i--) {
            // i번째 공이 스택 최상단에 있는 공보다 무겁다면
            // 스택에서 해당 공을 제거하고, discardedMax에 최대 무게를 기록
            while (!stack.isEmpty() && balls[i] > stack.peek())
                discardedMax = Math.max(discardedMax, stack.pop());
            // i번째 공이 discardedMax 보다 같거나 무거운 경우에만 담는다.
            // 가벼운 경우라면 자신보다 뒤에 있는 공이 정렬을 위해 앞으로 갈 것이므로
            // 자신 또한 정렬 대상에 포함되기 때문
            if (balls[i] >= discardedMax)
                stack.push(balls[i]);
        }

        // 전체 n개의 공 중 정렬하지 않아도 되는 공의 수(= 스택의 크기)
        // 만큼을 빼준 값을 출력한다.
        System.out.println(n - stack.size());
    }
}