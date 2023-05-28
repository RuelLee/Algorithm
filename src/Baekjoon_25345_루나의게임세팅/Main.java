/*
 Author : Ruel
 Problem : Baekjoon 25345번 루나의 게임 세팅
 Problem address : https://www.acmicpc.net/problem/25345
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25345_루나의게임세팅;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1000000007;

    public static void main(String[] args) throws IOException {
        // n개의 서로 다른 높이의 타워 중 k개를 선택해 일렬로 배치하고자 한다.
        // 각각의 빌딩은 왼쪽 혹은 오른쪽 둘 중 한 방향에서는 반드시 빌딩의 모습이 보여야한다.
        // 다시말해 각각의 빌딩은 양 옆에 자신보다 높은 빌딩이 놓여서는 안된다.
        // 그러한 방법이 총 몇 가지가 있는지 출력하라
        // 수가 클 수 있으므로, 10^9 + 7으로 나눈 나머지를 출력한다.
        //
        // 조합 문제
        // 먼저 파스칼의 삼각형을 통해 n개의 타워들 중 k개를 고르는 경우의 수를 구한다.
        // 그 후 빌딩을 두 방향 중 하나 곳에서는 보이도록 배치해야한다.
        // 하지만 이 경우 간단한 것이, 가장 높은 빌딩을 세우고
        // 그 후에 차근차근 높은 순서대로 왼쪽 혹은 오른쪽에 배치해 나가면 된다.
        // 따라서 k 개의 빌딩을 배치하는 경우는, 가장 큰 빌딩을 빼고, 나머지 (k - 1)개의 빌딩을
        // 오른쪽 혹은 왼쪽에 배치하는 경우이므로 2^(k-1) 가지가 된다.
        // 파스칼의 삼각형과, 위 제곱 모두 범위를 벗어날 가능성이 있으므로 모듈러 연산을 적용해야함에 유의하자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 배치 가능한 빌딩
        int n = Integer.parseInt(st.nextToken());
        // 그 중 선택하는 k개의 빌딩
        int k = Integer.parseInt(st.nextToken());
        // 빌딩의 높이들
        // 사실 서로 다른 높이라고 주어지기 때문에 구체적인 수치까지는 필요없다.
        int[] towers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 파스칼의 삼각형
        long[][] pascalsTriangles = new long[n + 1][n + 1];
        pascalsTriangles[0][0] = 1;
        for (int i = 1; i < pascalsTriangles.length; i++) {
            pascalsTriangles[i][0] = 1;
            for (int j = 1; j <= i; j++)
                pascalsTriangles[i][j] = (pascalsTriangles[i - 1][j - 1] + pascalsTriangles[i - 1][j]) % LIMIT;
        }
        
        // 2의 제곱 계산
        long[] pow = new long[k];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = pow[i - 1] * 2 % LIMIT;

        // 답안 출력.
        System.out.println(pascalsTriangles[n][k] * pow[k - 1] % LIMIT);
    }
}