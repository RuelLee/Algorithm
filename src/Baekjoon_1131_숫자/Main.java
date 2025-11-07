/*
 Author : Ruel
 Problem : Baekjoon 1131번 숫자
 Problem address : https://www.acmicpc.net/problem/1131
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1131_숫자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] pow, memo;

    public static void main(String[] args) throws IOException {
        // 자연수 n이 주어질 때, 각 자리의 수들을 k제곱한 후, 그 합을 구하는 함수를 Sk(n)이라고 한다.
        // 예를 들어 S2(65) = 6^2 + 5^2 = 61이다.
        // 다음과 같은 수열을 만든다, n, Sk(n), Sk(Sk(n)), ...
        // a보다 같거나 크고, b보다 같거나 작은 모든 n으로 각각 수열을 만들 때
        // 수열에서 가장 작은 수의 합을 구하는 프로그램을 작성하라
        //
        // dfs, 백트래킹, 메모이제이션 문제
        // n을 수열로 만들므로, 하나의 n에 대해 계산할 때, 이후에 나올 n들에 관해서 미리 계산할 수 있다. 
        // 수열의 수를 적다보면 반드시 사이클이 생긴다.
        // 사이클이 생길 때, 사이클 내의 원소들 중 최소값은 사이클 내에 모두 적용된다.
        // 사이클 이전에 등장한 수는 자신과 이후에 등장할 수들 중 더 적은 값에 도달할 수 있다.
        // 따라서 정해진 범위에 대해 모두 계산하더라도, 메모이제이션에 의해 미리 처리되므로, O(n)으로 처리할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // a ~ b 범위, k제곱
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // k제곱을 미리 계산
        pow = new int[10];
        for (int i = 1; i < pow.length; i++) {
            pow[i] = 1;
            for (int j = 0; j < k; j++)
                pow[i] *= i;
        }
        // n이 최대 100만까지, k는 최대 6으로 주어지므로
        // 6 * 9^6까지의 값이 등장할 수 있다.
        memo = new int[3188647];

        long answer = 0;
        Stack<Integer> stack = new Stack<>();
        // a ~ b범위의 수들에 대해 수열을 만들 때, 최솟값을 구해 누적시킨다.
        for (int i = a; i <= b; i++) {
            stack.clear();
            answer += findMin(i, stack);
        }
        System.out.println(answer);

    }

    // num 이후로 만들어지는 수열에 대해 최솟값을 구한다
    static int findMin(int num, Stack<Integer> stack) {
        // memo[num]이 -1인 경우는
        // 이미 방문했지만 아직 값이 안 채워진 경우.
        // 그런데 다시 방문을 했으므로 -> 사이클이 발견된 경우이다.
        if (memo[num] == -1) {
            Queue<Integer> queue = new LinkedList<>();
            memo[num] = num;
            // 스택 내의 num이 발견될 때까지 큐에 값을 추가시키며 최솟값을 찾는다.
            while (stack.peek() != num) {
                memo[num] = Math.min(memo[num], stack.peek());
                queue.offer(stack.pop());
            }
            stack.pop();
            // 큐의 모든 값들에 대해 최솟값을 반영
            while (!queue.isEmpty())
                memo[queue.poll()] = memo[num];
        } else if (memo[num] == 0) {
            // meme[num]이 0인 경우는 최초 방문인 경우
            // 방문했다는 표시로 -1 값을 채우고, 스택에 넣은 다음
            memo[num] = -1;
            stack.push(num);
            // num과 이후로 수열에서 찾을 수 있는 최솟값 중 더 적은 값을 memo[nun]에 기록한다.
            memo[num] = Math.min(num, findMin(nextNum(num), stack));
        }

        // 위의 if와 else if를 마친 후이거나 이미 해당 num 이후의 수열에 대해 최솟값을 찾은 경우
        // memo[num]에 이후 수열에 등장하는 최솟값이 계산되어있다.
        // 해당 값 반환
        return memo[num];
    }

    // n의 각 자리수를 k제곱하여 누적시킨다.
    static int nextNum(int n) {
        int sum = 0;
        while (n > 0) {
            int num = n % 10;
            sum += pow[num];
            n /= 10;
        }
        return sum;
    }
}