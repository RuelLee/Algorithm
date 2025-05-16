/*
 Author : Ruel
 Problem : Baekjoon 1014번 컨닝
 Problem address : https://www.acmicpc.net/problem/1014
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1014_컨닝;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static char[][] seats;
    static int[][] dp;
    static int[][] checkBitLoc;

    public static void main(String[] args) throws IOException {
        // 시험을 보는데 수험자의 좌우, 앞대각선의 위치에 다른 수험자가 있다면 컨닝을 한다고 한다
        // 수험자를 배치 가능한 위치 '.'와 불가능한 위치 'x'가 주어질 때 가장 많은 학생을 배치할 수 있는 수를 구하라
        // 예전에 풀었던 격자판 채우기(https://www.acmicpc.net/problem/1648), 두부장수 장홍준(https://www.acmicpc.net/problem/1657)과 유사하게 비트마스크와 메모이제이션을 활용한다
        // 만약 수험자가 왼쪽 끝에 앉은 경우
        // . . .
        // o . . 자신의 오른쪽 위의 한칸만 살펴보면 된다
        // 만약 수험자가 끝이 아닌 위치에 앉은 경우
        // . . .
        // . o . 자신의 왼쪽 위, 오른쪽 위, 왼쪽 세 곳을 살펴봐야한다
        // 수험자가 오른쪽 끝에 앉은 경우
        // . . .
        // . . o 자신의 왼쪽 위와 왼쪽을 살펴봐야한다
        // 따라서 비트마스크로 갖고 가야할 값의 최대 크기는 자신의 왼쪽 대각선 위까지 봐야하므로, 1 << (m + 1) 이다.
        // 그리고 자신의 차례가 지나가면 왼쪽 대각선 위치는 자연스럽게 사라지게 하기 위해서
        // 0 1 2
        // 3 x .    처럼 비트필드를 활용한다고 하자. 자신의 왼쪽 위 위치는 첫번째 비트이고, 자신의 바로 앞은 두번째, ... , 자신의 왼쪽은 m + 1번째 비트이다.
        // 따라서 위 비트마스크를 활용하여 현 위치에 수험자를 배치할 수 있는지 살펴보고, 배치하고 넘겨주거나, 배치하지 않고 넘어가면 된다.
        // 중복된 연산이 생길 수 있으므로, 해당 위치에서 비트마스크로 계산한 값은 현위치에서 최대로 더 추가할 수 있는 학생 수이고 이를 저장하여 메모이제이션으로 활용한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            seats = new char[n][];      // 책상 상태. 앉을 수 있는 곳은 '.', 앉을 수 없는 곳은 'x'
            for (int i = 0; i < seats.length; i++)
                seats[i] = br.readLine().toCharArray();
            dp = new int[n * m][1 << (m + 1)];      // n * m 개의 책상을 선형적으로 두고, 비트마스크 각각의 상태를 따로 저장한다.
            // 책상의 위치에 따라 살펴봐야할 이전 책상들의 위치가 다르다
            // checkBitLoc[0]은 왼쪽 끝의 위치인 경우, checkBitLoc[2]는 오른쪽 끝의 위치인 경우, checkBitLoc[1]은 둘 다 아닌 경우
            checkBitLoc = new int[][]{{1 << 2}, {1 << 0, 1 << 2, 1 << m}, {1 << 0, 1 << m}};
            sb.append(findAnswer(0, 0)).append("\n");
        }
        System.out.println(sb);
    }

    static int findAnswer(int num, int bitmask) {
        if (num == dp.length)       // 모든 책상에 대해 계산을 마친 경우. 0을 리턴.
            return 0;

        if (dp[num][bitmask] != 0)      // 이미 저장된 계산값이 있는 경우, 바로 값을 활용한다.
            return dp[num][bitmask];

        // 왼쪽 끝의 위치인 경우 0, 오른쪽 끝인 경우 2, 둘 다 아닌 경우 1
        int loc = num % m == 0 ? 0 : (num % m == m - 1 ? 2 : 1);

        // num의 위치가 앉을 수 있는 책상이고, 비트마스크를 봤을 때, 현 위치에 수험자를 배치할 수 있다면
        // 현재 저장된 값과, 다음 책상에서 최대로 배치할 수 있는 학생 수 + 1(현 위치에 학생 배치) 중 큰 값을 메모이제이션으로 저장해둔다.
        // 다음 책상(num + 1)로 넘기며, 비트마스크는 오른쪽으로 하나 쉬프트하고, m 위치(m + 1번째 비트)에 1을 표시하여 현 위치에 수험자를 배치했음을 표시한다.
        if (seats[num / m][num % m] == '.' && checkBitmask(loc, bitmask))
            dp[num][bitmask] = Math.max(dp[num][bitmask], findAnswer(num + 1, (bitmask >> 1) | (1 << m)) + 1);
        // 모든 경우에 대해서 현 위치에 학생을 배치하지 않은 경우도 계산한다.
        // num에는 값을 하나 증가시킨 값과, bitmask에서는 오른쪽으로 하나 쉬프트 한 값을 넘겨준다.
        dp[num][bitmask] = Math.max(dp[num][bitmask], findAnswer(num + 1, bitmask >> 1));

        return dp[num][bitmask];
    }

    static boolean checkBitmask(int loc, int bitmask) {
        // loc과 bitmask에 따라 현재 위치에 학생을 배치할 수 있는지 살펴본다.
        for (int i = 0; i < checkBitLoc[loc].length; i++) {
            if ((checkBitLoc[loc][i] & bitmask) != 0)
                return false;
        }
        return true;
    }
}