/*
 Author : Ruel
 Problem : Baekjoon 27715번 우표 구매하기 (Easy)
 Problem address : https://www.acmicpc.net/problem/27715
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27715_우표구매하기Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1원짜리 우표가 n종류가 있고, 2원짜리 우표가 m종류가 있다.
        // 정확히 k원의 우표를 사는 가짓수를 p로 나눈 나머지를 출력하라
        //
        // 중복 조합, 파스칼의 삼각형
        // 중복 조합은 할 때마다 헷갈리는 거 같다.
        // a종류에서 b개를 중복을 허락하여 뽑는다면
        // a + b - 1 개의 칸에 (a - 1)개의 칸막이를 설치하는 것과 같다.
        // 첫번째 칸막이가 나오기 전까지는 첫번째 종류, 첫번째와 두번째 칸막이 사이는 두번째 종류..
        // 마지막 칸막이 이후는 a번째 종류를 선택하는 경우와 같다.
        // 따라서 a+b-1C(a-1) = a+b-1Cb와 같이 계산할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 1원짜리 우표 n 종류, 2원짜리 우표 m 종류
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 채워야하는 금액 k, 나누는 값 p
        int k = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        
        // 미리 계산해둘 파스칼 삼각형의 크기
        int size = Math.max(k + n + 1, k / 2 + m + 1);
        // 조합을 파스칼 삼각형을 통해 미리 계산
        int[][] pascalsTriangle = new int[size][size];
        pascalsTriangle[0][0] = 1;
        for (int i = 1; i < pascalsTriangle.length; i++) {
            pascalsTriangle[i][0] = pascalsTriangle[i][i] = 1;
            for (int j = 1; j < i; j++)
                pascalsTriangle[i][j] = (pascalsTriangle[i - 1][j - 1] + pascalsTriangle[i - 1][j]) % p;
        }

        // p원의 우표를 구매하는데
        // mCount개 만큼을 2원 우표를 사고, 나머지 개수를 1원 우표로 채운다.
        int mCount = 0;
        long answer = 0;
        // mCount가 k/2원보다 같거나 작은 동안
        while (mCount <= k / 2) {
            // 1원짜리 우표를 사는 개수
            int nCount = k - mCount * 2;
            // 1원짜리 우표를 n종류에서 nCount개를 뽑는 경우 * 2원짜리 우표를 m종류에서 mCount개 뽑는 경우의 수
            answer += (long) pascalsTriangle[Math.max(0, n + nCount - 1)][nCount] *
                    pascalsTriangle[Math.max(0, m + mCount - 1)][mCount];
            // p로 나머지 처리
            answer %= p;
            // mCount 증가
            mCount++;
        }
        // 답 출력
        System.out.println(answer);
    }
}