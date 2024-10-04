/*
 Author : Ruel
 Problem : Baekjoon 2695번 공
 Problem address : https://www.acmicpc.net/problem/2695
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2695_공;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] memo;

    public static void main(String[] args) throws IOException {
        // 동일한 유리공이 B개 주어지고, M개의 층이 주어질 때, 해당 유리공이 깨지지 않는 최대층을 최소 시도로 찾는 문제
        // 공이 한개일 때는 제일 적은 층부터 하나씩 시도해보는 수밖에 없다. 유리공이 깨지면 기회는 끝이므로.
        // 처음 읽었을 때는 단순히 이분탐색으로 풀 수 있을 거라 생각했지만 아니었다
        // 메모이제이션을 활용해서 풀어야한다
        // M개의 층이 주어진다면, 1 ~ m까지 층 중 하나의 골라 공을 던져볼 수 있다
        // 이 때 깨지는 경우와 깨지지 않는 경우가 있다
        // 만약 n층에서 깨졌다면, 이는 다시 B-1 개의 공으로 1층부터 n-1층까지의 최소 시도 회수 + 1로 생각할 수 있따
        // 만약 n층에서 깨지지 않았다면, 이는 다시 B개의 공으로 n+1층부터 m층까지의 최소 시도 회수 + 1로 생각할 수 있고
        // 이는 다시 n+1 ~ m -> 1 ~ m-n 으로 바꿀 수 있다.
        // 이 때 최소 시도회수를 각각 메모로 저장해두고 참조하는 식으로 만들자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        memo = new int[51][1001];       // 공은 최대 50개, 층은 최대 1000층.

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());
            int ball = Integer.parseInt(st.nextToken());
            int floor = Integer.parseInt(st.nextToken());

            sb.append(findAnswer(ball, floor)).append("\n");
        }
        System.out.println(sb);
    }

    static int findAnswer(int ball, int floor) {
        if (memo[ball][floor] != 0)     // 만약 값이 들어있다면
            return memo[ball][floor];       // 바로 값 참조
        else if (ball == 1)         // 공이 1개만 남았다면 floor층을 1층부터 모두 시도해보는 수밖에 없다.
            return memo[ball][floor] = floor;
        else if (floor == 1 || floor == 0)      // 1층이거나 0층일 때는 직접 한번 시도해보는 수밖에 없다.
            return memo[ball][floor] = 1;

        int min = Integer.MAX_VALUE;        // 최소 시도 회수를 찾을 것이다.
        for (int i = 1; i < floor; i++) {       // 1 ~ floor 층 중 하나의 층을 골라
            // i층에서 깨지는 경우에는 ball-1, 1 ~ i-1층까지의 최소 시도 회수 + 1
            // 1층에서 깨지지 않는 경우에는 ball, 1 ~ floor-1(= i+1 ~ floor)층까지의 최소 시도 회수 + 1로 나타낼 수 있고
            // 둘 중 큰 값이 최악의 경우의 최소 시도 회수다.
            int turn = Math.max(findAnswer(ball - 1, i - 1) + 1, findAnswer(ball, floor - i) + 1);
            min = Math.min(min, turn);
        }
        // 찾은 값을 메모해두고 반환하자.
        return memo[ball][floor] = min;
    }
}