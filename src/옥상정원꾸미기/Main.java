/*
 Author : Ruel
 Problem : Baekjoon 6198번 옥상 정원 꾸미기
 Problem address : https://www.acmicpc.net/problem/6198
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 옥상정원꾸미기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 빌딩이 주어지며, 자신보다 우측에 있으며 높이가 낮은 빌딩만 볼 수 있다고 한다
        // 모든 빌딩이 볼 수 있는 다른 빌딩의 개수의 합을 구하라
        // 유사한 문제를 풀어서인지 바로 스택이 떠올랐다
        // 새로운 빌딩을 스택에 넣기 전에, 자신보다 작거나 같은 빌딩들을 모조리 꺼낸다.
        // 해당 빌딩들은 새로운 빌딩 이후의 빌딩들을 볼 수 없기 때문이다.
        // 꺼내는 빌딩과 새로운 빌딩 사이의 빌딩들은 스택 안에 있던 빌딩이 볼 수 있는 빌딩들이므로, 그 개수를 세어 더해준다.
        // 마지막 빌딩까지 작업 후, 스택에 빌딩이 남아있을 수 있는데, 마지막에 가장 큰 빌딩이 있다 생각하고 나머지 빌딩들을 꺼내며 사이의 빌딩 개수를 더해주면 된다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] buildings = new int[n];
        Stack<Integer> stack = new Stack<>();
        long count = 0;     // 최대 8만개의 빌딩이 주어지므로, 최악의 경우, 79999 + ... + 1 = 80000 * 80001 / 2 = 약 32억 정도의 값이 나올 수 있다
        // long 타입으로 세주자.
        for (int i = 0; i < n; i++) {
            buildings[i] = Integer.parseInt(br.readLine());     // 새로운 빌딩을 입력 받고,
            while (!stack.isEmpty() && buildings[stack.peek()] <= buildings[i])     // 스택 안에 새로운 빌딩의 높이보다 같거나 작은 빌딩들을 꺼내면서, 사이에 있는 빌딩들의 개수만큼 더해준다.
                count += (i - 1) - stack.pop();
            stack.push(i);
        }
        // 마지막 빌딩이후, 스택에 남아있는 빌딩들에 대한 계산도 해준다.
        while (!stack.isEmpty())
            count += n - 1 - stack.pop();

        // 최종 count 값이 정답.
        System.out.println(count);
    }
}