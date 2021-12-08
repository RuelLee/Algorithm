/*
 Author : Ruel
 Problem : Baekjoon 1657번 두부장수 장홍준
 Problem address : https://www.acmicpc.net/problem/1657
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 두부장수장홍준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[][] memo;
    static char[][] tofu;
    static int[][] grades = {
            {10, 8, 7, 5, 0, 1},
            {8, 6, 4, 3, 0, 1},
            {7, 4, 3, 2, 0, 1},
            {5, 3, 2, 2, 0, 1},
            {0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 0}};

    public static void main(String[] args) throws IOException {
        // 격자판 채우기(https://www.acmicpc.net/problem/1648), 몬드리안의 꿈(https://www.acmicpc.net/problem/6569)과 유사한 방법으로 풀 수있다
        // 메모이제이션와 비트마스킹을 활용하는 것이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        memo = new int[n * m][1 << m];      // 각 칸마다 idx를 할당하고 해당하는 비트마스킹에 따라 값을 메모한다.
        for (int[] mm : memo)
            Arrays.fill(mm, -1);        // -1로 초기화.

        tofu = new char[n][m];          // 현재 두부판의 상태를 입력 받는다.
        for (int i = 0; i < n; i++)
            tofu[i] = br.readLine().toCharArray();

        System.out.println(findAnswer(0, 0));
    }

    static int findAnswer(int idx, int bitmask) {
        if (idx == n * m && bitmask == 0)       // n * m 위치에 bitmask = 0 값으로 도착했다면 정상적으로 끝난 경우다. 0을 리턴해주자.
            return 0;

        if (memo[idx][bitmask] != -1)       // 이미 연산한 결과가 있다면 바로 리턴해주자.
            return memo[idx][bitmask];

        int count = findAnswer(idx + 1, bitmask >> 1);      // 이번 두부를 선택하지 않고 넘기는 경우.
        if ((bitmask & 1) == 1)     // 이번 두부가 이미 선택이 된 경우라면, 넘기는 경우밖에 없다. 값을 메모해주고 리턴하자.
            return memo[idx][bitmask] = count;
        else {      // 이번 두부가 아직 선택이 안되었다면
            // 이번 칸이 가로줄에서의 마지막 칸이 아니고, 오른쪽 두부가 아직 선택되지 않았다면, 이번 두부와 오른쪽 두부를 선택했을 때의 값을 찾고, 최대값이 갱신되는지 확인하자.
            if ((idx % m != m - 1) && (idx + 1 < n * m) && (bitmask & 2) == 0)
                count = Math.max(count, findAnswer(idx + 2, bitmask >> 2) + getTofuValue(idx, idx + 1));
            // 세로로 두부를 선택했을 때의 최대값을 찾고 갱신되는지 확인하자.
            if (idx + m < n * m)
                count = Math.max(count, findAnswer(idx + 1, (bitmask | 1 << m) >> 1) + getTofuValue(idx, idx + m));
        }
        // 최종적으로 구한 값을 메모이제이션해주고 값을 리턴하자.
        return memo[idx][bitmask] = count;
    }

    static int getTofuValue(int idx1, int idx2) {
        // 어떤 두 두부를 선택하느냐에 따른 값이 정해져있다. 두 두부의 등급에 따른 값을 반환해준다.
        return grades[tofu[idx1 / m][idx1 % m] - 'A'][tofu[idx2 / m][idx2 % m] - 'A'];
    }
}