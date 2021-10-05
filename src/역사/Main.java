/*
 Author : Ruel
 Problem : Baekjoon 1613번 역사
 Problem address : https://www.acmicpc.net/problem/1613
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 역사;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static final int MAX = 500;     // 사건의 최대 개수보다 큰 값.

    public static void main(String[] args) {
        // 위상 정렬로 풀어야하나? 라는 생각을 했지만, 플로이드 와샬로 푸는 문제였다
        // a 이후에 b라는 관계가 성립할 때, 가중치 1을 주기로 했다. 초기화는 모든 사건이 일렬로 정렬된다면 400의 값을 갖기 때문에, 이보다 큰 500을 주었다.
        // 관계들에 따라 이차원 배열의 값을 세팅해간다.
        // 그 후 플로이드 와샬로 각 사건에서 다른 사건까지 이르는 최소한의 비용을 구한다
        // 만약 관계가 성립되지 않는다면 500 이상의 값을 갖게 될 것이고, 성립이 된다면 MAX 미만의 값을 갖게 될 것이다.
        // 그 후 물음에 대해서, a, b라는 두 값이 들어온다면, floydWarshall[a][b]와 floydWarshall[b][a] 값을 참고하여 대답하면 된다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[][] floydWarshall = new int[n + 1][n + 1];
        for (int[] fw : floydWarshall)
            Arrays.fill(fw, MAX);       // MAX 값으로 초기세팅.

        for (int i = 0; i < k; i++)
            floydWarshall[sc.nextInt()][sc.nextInt()] = 1;

        for (int via = 1; via < floydWarshall.length; via++) {      // 플로이드 와샬 알고리즘.
            for (int start = 1; start < floydWarshall.length; start++) {
                if (via == start)
                    continue;
                for (int end = 1; end < floydWarshall.length; end++) {
                    if (end == via || end == start)
                        continue;

                    if (floydWarshall[start][end] > floydWarshall[start][via] + floydWarshall[via][end])
                        floydWarshall[start][end] = floydWarshall[start][via] + floydWarshall[via][end];
                }
            }
        }
        int s = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            if (floydWarshall[a][b] < MAX)      // a -> b의 관계가 성립한다면 -1
                sb.append(-1);
            else if (floydWarshall[b][a] < MAX)     //  b -> a의 관계가 성립한다면 1
                sb.append(1);
            else        // 그렇지 않고 두 사건의 관계를 알 수 없을 때는 0
                sb.append(0);
            sb.append("\n");
        }
        System.out.println(sb);
    }
}