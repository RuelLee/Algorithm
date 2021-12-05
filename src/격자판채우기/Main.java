/*
 Author : Ruel
 Problem : Baekjoon 1648번 격자판 채우기
 Problem address : https://www.acmicpc.net/problem/1648
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 격자판채우기;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static final int LIMIT = 9901;
    static int[][] dp;
    static int n, m;

    public static void main(String[] args) {
        // 비트마스크를 활용한 DP문제
        // 개념을 생각하기 정마아아알 어렵다. 나중에 다시 풀어봐야겠다.
        // n = 2, m = 5인 공간을
        // 0 1 2 3 4
        // 5 6 7 8 9
        // 와 같이 연속된 수를 붙여 생각한다
        // 그리고 이번 위치가 i라면 (i ~ i + (m - 1)))까지의 상태를 담은 비트마스크를 갖고 다닌다
        // 자 이제 0번부터 연속적으로 타일을 채우기 시작한다
        // 먼저 비트마스크를 살펴보고 현재 위치에 타일이 채워져있는지 확인한다.(바로 윗 칸에서 세로 타일을 놓아 채워져있을 수 있다.)
        // 채워져있다면 다음 칸의 값을 가져온다.
        // 채워져 있지 않다면 타일을 놓는 방법은 가로로 놓던가, 세로로 놓던가 2가지 방법이 존재한다
        // 가로로 놓는다면, 두 칸 뒤로 넘겨준다
        // 세로로 놓는다면, i + m 칸에 타일이 있다고 표시를 해주고, 다음 칸으로 넘긴다.
        // idx가 n * m 을 넘는다면 범위 밖으로 나간 경우이다. 해당 경우의 가지수는 없다 = 0
        // idx가 n * m에 도착했고, 비트마스크가 0이라면(밖으로 놓인 타일이 없는 경우) 해당 가지수는 1가지
        // 최종적으로 값을 반환 받으면 0번 위치로부터 타일을 채울 수 있는 가지수가 나온다.
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        dp = new int[n * m][1 << m];
        for (int[] d : dp)
            Arrays.fill(d, -1);
        System.out.println(tileFilling(0, 0));
    }

    static int tileFilling(int idx, int rowBitMask) {
        if (idx == n * m && rowBitMask == 0)        // n + m 위치에서 비트마스크가 0으로 끝난다면 n + m -1 까지 모두 타일이 잘 채워진 경우.
            return 1;
        else if (idx >= n * m)      // 범위 밖으로 나갔다. 해당 경우는 세지면 안된다. 0 리턴.
            return 0;

        if (dp[idx][rowBitMask] != -1)      // 이미 계산한 적이 있는 곳이다. 값만 바로 리턴하자.
            return dp[idx][rowBitMask];

        int count = 0;
        if ((rowBitMask & 1) != 0)      // 현재 칸이 차있다면, 바로 옆칸으로 넘겨주자
            count = tileFilling(idx + 1, rowBitMask >> 1);      // 넘겨주면서 idx가 idx+1이 되므로, 비트마스크의 위치가 바뀌어여야한다. 비트마스크의 값을 하나씩 땡겨주자.
        else {
            if (((idx % m) != (m - 1)) && (rowBitMask & 2) == 0)        // 현재 위치가 가장 오른쪽이 아니면서, 옆 칸 또한 비어있다면!
                count += tileFilling(idx + 2, rowBitMask >> 2);     // 가로 방향으로 타일을 놓을 수 있다. 타일을 놓고, 다다음칸으로 넘겨주자.
            count += tileFilling(idx + 1, (rowBitMask | (1 << m)) >> 1);        // 세로 방향의 타일은 항상 놓을 수 있다. 옆 칸으로 넘겨주면서, 비트마스크에는 (idx + m) 칸에 놓여있다고 표시해두자.
        }
        // 최종적으로 계산되는 값을 dp에 넣고 반환해주자.
        return dp[idx][rowBitMask] = count % LIMIT;
    }
}