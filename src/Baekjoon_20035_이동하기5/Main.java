/*
 Author : Ruel
 Problem : Baekjoon 20035번 이동하기 5
 Problem address : https://www.acmicpc.net/problem/20035
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20035_이동하기5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final long tenBillion = 1000000000L;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 미로에 갇혀있다.
        // 그리고 크기가 n인 수열 A와 크기가 m인 수열 B가 주어진다.
        // (i, j)에 놓여있는 사탕의 개수는 Ai * 1000000000 + Bj이다.
        // 이동은 (r, c)에서 (r+1, c) or (r, c+1)로 이동 가능하다.
        // (1, 1)에서 (n, m)으로 이동하며 얻을 수 있는 사탕의 최대 개수는?
        //
        // 그리디 문제
        // 아 이런거 풀어봤는데! 하면서 DP로 풀었다간 메모리 초과가 난다.
        // n과 m이 최대 10만으로 주어지기 때문
        // 가만히 생각해보면, 각 행과 각 열을 모두 한번씩은 지나쳐간다.
        // 다만 행 이동을 할 때는 열이, 열 이동을 할 때는 행이 중복해서 누적된다.
        // 따라서 각 행과 열에 대해 후에 나오는 행과 열에 사탕이 더 많은지, 같은지, 적은지를 나타내고
        // 그거이 따라 행 이동을 할지 열 이동을 할지 정하면서 이동하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 미로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 행과 열에 해당하는 사탕
        int[] a = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] b = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 뒤에 나오는 행에 더 많은 사탕이 있는지, 같은지 적은지를 표현한다.
        int[] aRight = new int[n];
        int idx = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            // i보다 뒤에 더 많은 사탕이 있는 경우
            if (a[idx] > a[i])
                aRight[i] = 2;
            // i보다 뒤에 많아봤자 같은 수의 사탕이 있는 경우.
            else if (a[idx] == a[i])
                aRight[i] = 1;
            // i보다 더 뒤에 더 적은 사탕밖에 없는 경우
            else
                idx = i;
        }
        // 열에 대해서도 똑같이 계산
        int[] bRight = new int[m];
        idx = m - 1;
        for (int i = m - 2; i >= 0; i--) {
            if (b[idx] > b[i])
                bRight[i] = 2;
            else if (b[idx] == b[i])
                bRight[i] = 1;
            else
                idx = i;
        }

        // 행과 열의 idx
        int aIdx = 0;
        int bIdx = 0;
        // 누적된 행과 열의 사탕 개수
        int aSum = a[0];
        int bSum = b[0];
        // 둘 다 맵을 벗어나지 않는 범위에서만 진행
        while (aIdx < n || bIdx < m) {
            // aIdx가 아직 끝에 도달하지 않았고,
            // bIdx가 끝에 도달했거나, aIdx 뒤에 더 많은 사탕이 있는 경우,
            // 혹은 a는 같은 수의 사탕이 뒤에있지만 b는 최대 사탕에 도달해 더 적은 사탕만 있는 경우
            // aIdx 증가
            if (aIdx < n - 1 && (bIdx == m - 1 || aRight[aIdx] == 2 || aRight[aIdx] > bRight[bIdx]))
                aIdx++;
            // 그 외의 경우에는 bIdx 증가
            else
                bIdx++;

            // 맵을 벗어났다면 종료.
            if (aIdx == n || bIdx == m)
                break;

            // 그렇지 않다면 현재 칸의 사탕을 더해준다.
            aSum += a[aIdx];
            bSum += b[bIdx];
        }

        // 원래 값에 맞게 행의 누적된 사탕 수엔 10억을 곱하고
        // 계산된 열의 사탕수와 합쳐 정답을 출력한다.
        System.out.println(aSum * tenBillion + bSum);
    }
}