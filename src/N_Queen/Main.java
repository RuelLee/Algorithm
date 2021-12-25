/*
 Author : Ruel
 Problem : Baekjoon 3344번 N-Queen
 Problem address : https://www.acmicpc.net/problem/3344
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package N_Queen;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 기본적인 N-Queen 문제는 백트래킹을 활용하여 가로 세로 칸이 주어질 때 몇 개의 가지수가 존재하는지를 구하는 문제였다
        // 하지만 이번엔 매우 큰 가로 세로 크기를 주고, N-Queen에 해당하는 케이스 중 하나를 출력하는 문제였다
        // col과 row - col, row + col을 활용하여 중복되지 않는 위치에 퀸을 두는 방법도 생각해보았지만
        // 놓는 순서에 따라 성립하지 않는 경우도 많았다.
        // 구글링을 해보니 위키에 해당 방법에 대한 솔루션이 있었다(https://en.wikipedia.org/wiki/Eight_queens_puzzle#Explicit_solutions)
        // 구현적으로 어렵지는 않지만 해당 조건에 대해 생각하는 것은 매우 어려울 것 같다.
        // 먼저 짝수와 홀수를 분리하는데 이는 ↖으로 서로 겹치는 일이 생기는 것을 방지하기 위함인 것 같다(row - col)
        // 그 후 n이 나머지에 따라 ↗방향으로 서로 겹치는 일이 생길 수 있다
        // 이에 따라 몇 개의 수를 위치를 조정해주고 출력해주면 된다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Deque<Integer> oddNums = new LinkedList<>();
        Deque<Integer> evenNums = new LinkedList<>();
        for (int i = 1; i <= n; i += 2)     // 홀수만
            oddNums.offer(i);
        for (int i = 2; i <= n; i += 2)     // 짝수만
            evenNums.offer(i);

        if (n % 6 == 2) {           // 6으로 나눈 나머지가 2라면
            oddNums.pollFirst();
            oddNums.pollFirst();
            oddNums.offerLast(oddNums.pollFirst());     // 5를 마지막으로 보내고
            oddNums.offerFirst(1);      // 3, 1, ... , 5 형태로 만들어준다.
            oddNums.offerFirst(3);
        } else if (n % 6 == 3) {        // 6으로 나눈 나머지가 3이라면
            evenNums.offerLast(evenNums.pollFirst());       // 2를 마지막으로 보내고
            oddNums.offerLast(oddNums.pollFirst());     // 5, ... , 1, 3형태로 바꿔준다.
            oddNums.offerLast(oddNums.pollFirst());
        }

        StringBuilder sb = new StringBuilder();
        while (!evenNums.isEmpty())         // 정해진 순서에 따라 짝수를 꺼내고
            sb.append(evenNums.pollFirst()).append("\n");
        while (!oddNums.isEmpty())      // 홀수를 꺼내면 완성
            sb.append(oddNums.pollFirst()).append("\n");

        System.out.println(sb);
    }
}