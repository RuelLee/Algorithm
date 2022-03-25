/*
 Author : Ruel
 Problem : Baekjoon 10090번 역전 계산
 Problem address : https://www.acmicpc.net/problem/10090
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10090_CountingInversions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // 수열이 주어진다
        // 먼저 등장한 수보다 작은 수가 늦게 등장한다면 이러한 쌍의 개수를 세어라.
        //
        // 세그먼트 트리 문제
        // 이제 세그먼트 트리 문제는 보면 어느 정도 느낌이 오는 것 같다
        // 수가 등장할 때마다 이전에 등장한 자신보다 큰 수의 개수를 더해나가면 된다.
        // 펜윅 트리를 사용하도록 하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        fenwickTree = new int[n + 1];

        long sum = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());     // 이번 수
            // 이전에 등장한 num보다 큰 수의 개수는
            // 이전에 등장 수의 개수 i개(0부터 시작했으므로)에서 fenwick 트리로 구한 num 이하의 등장 수의 개수를 빼주면 된다.
            sum += (i - countSmallNumsAppear(num));
            // num이 등장했다고 펜윅트리에 표시해두자.
            checkAppear(num);
        }
        System.out.println(sum);
    }

    static int countSmallNumsAppear(int n) {        // n이하(같은 수는 한번 등장하므로 미만과도 같다) 수가 몇 번 나왔는지 세는 메소드
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }

    static void checkAppear(int loc) {      // loc에 해당하는 수가 등장했다고 체크해둔다.
        while (loc < fenwickTree.length) {
            fenwickTree[loc] += 1;
            loc += (loc & -loc);
        }
    }
}