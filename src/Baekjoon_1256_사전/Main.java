/*
 Author : Ruel
 Problem : Baekjoon 1256번 사전
 Problem address : https://www.acmicpc.net/problem/1256
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1256_사전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 a, m개의 z로 이루어진 문자열 사전을 만든다.
        // 사전의 k번째 단어를 출력하라
        // a와 m은 최대 100, k는 최대 10억으로 주어진다.
        //
        // 조합, 파스칼의 삼각형 문제
        // 각 순서에 a와 z를 배치하는 두 가지 경우가 생긴다.
        // a를 배치하는 경우 순서가 미뤄지진 않지만
        // z를 배치하는 경우, 그 자리에 a를 배치한 모든 경우의 수보다 순서가 늦춰진다.
        // 남은 순서를 비교하며 해당 자리에 a를 놓을지 z를 놓을지 비교한다.
        // n개의 a와 m개의 z로 만들 수 있는 단어의 종류는
        // n + m개의 칸에 a를 n개 배치하는 경우 혹은 z를 m개 배치하는 경우와 같다.
        // 따라서 이는 n+mCn = n+mCm 이며 해당 값은 파스칼의 삼각형을 이용하여 미리 구해두자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 a, m개의 z
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // k번째 순서
        int k = Integer.parseInt(st.nextToken());
        
        // 파스칼의 삼각형
        long[][] pascalsTriangle = new long[n + m + 1][n + m + 1];
        // oCo은 1
        pascalsTriangle[0][0] = 1;
        for (int i = 1; i < pascalsTriangle.length; i++) {
            // iC0 = iCi = 1
            pascalsTriangle[i][0] = pascalsTriangle[i][i] = 1;
            // k가 최대 10억으로 주어지므로
            // 10억보다 큰 값을 굳이 계산하지 않아도 된다. 그냥 10억보다 큰 값이다.
            // 또한 주어지는 n, m 크기를 고려하면 long 범위도 벗어난다.
            // 따라서 그냥 10억보다 큰 값은 10억 + 1로 대충 뭉뚱그려도 상관없다.
            for (int j = 1; j < i; j++)
                pascalsTriangle[i][j] = Math.min(pascalsTriangle[i - 1][j - 1] + pascalsTriangle[i - 1][j], 1_000_000_001);
        }

        StringBuilder sb = new StringBuilder();
        // 순서상 0부터 계산하게 되므로 k에서 하나 뺀 값으로 계산한다.
        k--;
        // 배치할 a 혹은 z가 남은 동안
        while (n + m > 0) {
            // 배치할 a가 남아있고
            // 해당 자리에 z를 배치할 경우, k 순서를 넘어가서, a를 배치해야하는 경우.
            if (n > 0 && k < pascalsTriangle[n - 1 + m][m]) {
                // a 개수 차감
                n--;
                // a 기록
                sb.append('a');
            } else if (m == 0)      // 순서 상 a를 배치하면 안되는데, z가 남아있지 않은 경우. 불가능한 경우이므로 일단 반복문 종료.
                break;
            else {
                // 그 외의 경우는 z가 남아있어 z를 배치하면 되는 경우
                // 순서에서 a를 배치하는 모든 경우보다 늦어지므로
                // 해당 경우의 수를 차감
                k -= (int) pascalsTriangle[n - 1 + m][m];
                // z 개수 차감.
                m--;
                sb.append('z');
            }
        }
        
        // 반복문을 모두 마쳤지만, k가 아직 남아있는 경우는
        // 주어진 a와 z로는 k번째 순서가 만들어지지 않는 경우이므로 -1을 출력
        if (k > 0)
            System.out.println(-1);
        else        // 그 외의 경우는 만들어진 단어 출력
            System.out.println(sb);
    }
}