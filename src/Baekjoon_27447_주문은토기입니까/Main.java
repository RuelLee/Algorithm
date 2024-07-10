/*
 Author : Ruel
 Problem : Baekjoon 27447번 주문은 토기입니까?
 Problem address : https://www.acmicpc.net/problem/27447
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27447_주문은토기입니까;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] times;

    public static void main(String[] args) throws IOException {
        // n개의 손님이 주문하는 시간이 주어진다.
        // 각 커피는 토기에 담아 서빙하는데
        // 각 토기를 굽는데 1의 시간
        // 토기에 커피를 담는데 1의 시간
        // 커피를 서빙하는데 1의 시간이 소모된다고 한다.
        // 또한 커피를 토기에 완전히 담은 후부터 m시간이 지나면 커피는 흙탕물로 변하며
        // 서빙은 커피가 흙탕물이 되기 전에 되어야한다. 서빙과 동시에 흙탕물이 되는 건 허용한다.
        // 모든 손님을 처리할 수 있다면 success를, 그렇지 않다면 fail을 출력한다.
        //
        // 그리디 문제
        // 손님들을 차례대로 살펴보며
        // 지금 비어있는 시간 중 가장 이른 시간대에 토기를 구워둔다.
        // 그 후, 해당 손님의 주문 시간 - m부터 손님의 주문 시간 -1까지의 비어있는 시간에 커피를 담고
        // 주문 시간에는 서빙을 바로 시작해야한다.
        // 경로 단축을 이용하여, n보다 크지만 비어있는 가장 이른 시간을 찾아내어 문제를 해결했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 손님, 흙탕물로 변하는 시간 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 손님들의 주문 시간
        int[] customers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 비어있는 시간
        times = new int[customers[customers.length - 1] + 1];
        // 처음에는 모든 시간이 비어있다.
        for (int i = 1; i < times.length; i++)
            times[i] = i;
        // 손님들의 주문 시간을 살펴보고, 해당 시간에는 서빙이 시작되어야하므로 비어있는 시간이 아니다.
        // 서빙 시간을 반영
        for (int c : customers)
            times[c] = findEmptyTimeAfterN(c + 1);

        boolean possible = true;
        // 손님들을 순차적으로 살펴보며
        // 토기를 굽고, 커피를 담는 시간 할당이 가능한지 살펴본다.
        for (int c : customers) {
            // 토기를 굽는건 가장 이른 시간에 아무 때나 해도 된다.
            int baking = findEmptyTimeAfterN(0);
            // 그 시간이 c보다 같거나 크다면 더 이상 주문 처리가 불가능한 경우이므로
            // possible을 false 처리하고 반복문을 끝낸다
            if (baking >= c) {
                possible = false;
                break;
            }
            // 아니라면 토기를 구울 시간이 주어진다.
            // 해당 시간에 토기를 굽고, 다음 가장 빠른 비어있는 시간을 담아둔다.
            times[baking] = findEmptyTimeAfterN(baking + 1);

            // 커피를 담는 시간.
            // c - m과 0중 큰 값부터 가장 이른 비어있는 시간을 찾는다.
            int coffee = findEmptyTimeAfterN(Math.max(c - m, 0));
            // 만약 이것 역시 c와 같거나 크다면 커피 담을 시간이 없는 경우.
            // 모든 주문 처리가 불가능한 경우.
            if (coffee >= c) {
                possible = false;
                break;
            }
            // 해당 시간에 커피를 담으므로
            // 해당 시간에는 다음 시간들 중 가장 빠른 비어있는 시간을 담는다.
            times[coffee] = findEmptyTimeAfterN(coffee + 1);
        }
        // 모든 주문 처리가 가능하면 success
        // 불가능하면 fail을 출력한다.
        System.out.println(possible ? "success" : "fail");
    }

    // n보다 같거나 늦은 시간들 중 비어있는 가장 이른 시간을 찾는다.
    static int findEmptyTimeAfterN(int n) {
        // 시간의 범위를 벗어난다면 큰 값을 반환한다.
        if (n >= times.length)
            return Integer.MAX_VALUE;
        // n에 해당하는 시간이 비어있는 경우, n 반환
        else if (times[n] == n)
            return n;
        // 그렇지 않은 경우, 현재 담겨있는 값을 토대로
        // 재귀로 메소드를 호출하여가장 이른 시간을 찾고 기록하고, 반환한다
        return times[n] = findEmptyTimeAfterN(times[n]);
    }
}