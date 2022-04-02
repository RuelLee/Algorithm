/*
 Author : Ruel
 Problem : Baekjoon 3015번 오아시스 재결합
 Problem address : https://www.acmicpc.net/problem/3015
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3015_오아시스재결합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람의 키가 주어진다
        // 두 사람 사이에 작은 사람의 키보다 큰 사람이 있다면 두 사람은 서로 보지 못한다고 한다
        // 서로 볼 수 있는 사람들의 쌍은 총 몇 쌍인가
        //
        // 스택을 이용하면 빠르게 풀 수 있다는 점은 금방 알아냈지만, 같은 키의 사람이 있을 때의 경우 때문에 생각보다 애를 먹었다
        // 따라서 내림차순 모노톤 스택으로 만들되, 스택 상으로 같은 키의 사람이 연속적으로 위치할 때, 연속된 사람의 수를 저장하여 활용하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] people = new int[n];      // 사람들의 키
        int[] sequentialSameHeight = new int[n];        // 이전 스택의 top과 같은 키일 때 몇 명이나 연속됐는지 표시한다.
        for (int i = 0; i < n; i++)
            people[i] = Integer.parseInt(br.readLine());
        Stack<Integer> stack = new Stack<>();
        long count = 0;
        for (int i = 0; i < people.length; i++) {
            // i번째 사람보다 더 작은 키 사람이 스택의 top에 위치한다면
            // 해당 top은 i 이후의 사람들과는 서로 볼 수 없다
            // 따라서 stack에서 제거하면서, i번사람과는 마주 볼 수 있으므로 이 경우를 센다
            // 만약 stack.pop에 해당하는 사람이 연속하여 같은 키였다면, 해당하는 사람 수 만큼을 모두 반영한다.
            while (!stack.isEmpty() && people[stack.peek()] < people[i])
                count += sequentialSameHeight[stack.pop()] + 1;

            // 스택이 비어있지 않고
            if (!stack.isEmpty()) {
                // 만약 스택 top에 i번째 사람과 같은 키의 사람이 존재한다면, 연속한 사람의 수를 추가해준다.
                // sequentialSameHeight[i]에 해당 sequentialSameHeight[stack.pop()] + 1을 넣어준다
                // 그리고 해당 스택의 top은 제거해준다.
                if (people[stack.peek()] == people[i])
                    count += sequentialSameHeight[i] = sequentialSameHeight[stack.pop()] + 1;

                // 만약 스택이 아직 비어있지 않다면, i번째 사람보다 키 큰 사람이 있을 것이다
                // 따라서 해당 사람과 마주볼 수 있으므로, 한 쌍으로 세어준다.
                if (!stack.isEmpty())
                    count++;
            }
            // 마지막으로 stack에 i번째 사람을 넣는다.
            stack.push(i);
        }
        // 마주볼 수 있는 쌍의 개수를 모두 세었다!. count 값 출력.
        System.out.println(count);
    }
}