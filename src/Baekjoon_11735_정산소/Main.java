/*
 Author : Ruel
 Problem : Baekjoon 11735번 정산소
 Problem address : https://www.acmicpc.net/problem/11735
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11735_정산소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[][] fenwickTrees;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 사각형이 주어지고
        // 각 배열의 원소 (x, y)는 x  + y로 채워져있다.
        // 다음 두 타입의 쿼리가 총 q개 들어온다.
        // R r -> r행의 모든 값들을 합한 결과를 출력하고, r행을 모두 0으로 바꾼다.
        // C c -> c열의 모든 값들을 합한 결과를 출력하고, c행을 모두 0으로 바꾼다.
        // 쿼리의 결과를 출력하라
        //
        // 누적합, 펜윅 트리 문제
        // 로 풀었다.
        // 각 칸의 값이 x + y이므로
        // 결국 r행의 모든 합을 구한다는 것은 (1 ~ n)까지의 합 + (r * 지워지지 않은 열의 개수)로 볼 수 있고
        // 이는 c열 또한 마찬가지다.
        // 따라서 누적합을 통해 각 칸에 1 ~ n을 채워둔 뒤, 각 행이나 열이 지워질 때 해당 값을 0으로 바꿔
        // (1 ~ n)까지의 합을 누적합을 통해 빠르게 구한다.
        // 또한 따로 지워진 행과 열의 개수를 계산하여 (r * 지워지지 않은 행 또는 열의 개수)를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 행렬의 크기 n, 쿼리 개수 q
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 펜윅 트리
        // fenwickTress[행과 열 구분][1 ~ n]
        fenwickTrees = new long[2][n + 1];
        for (int i = 1; i < n + 1; i++) {
            // i번째 행과 열에 i값을 넣는다.
            putValue(0, i, i);
            putValue(1, i, i);
        }

        StringBuilder sb = new StringBuilder();
        // 지워진 행과 열의 개수
        int deletedRows = 0;
        int deletedCols = 0;
        // 쿼리 처리
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 행 또는 열
            char line = st.nextToken().charAt(0);
            // 그 번호
            int num = Integer.parseInt(st.nextToken());
            
            // 들어온 쿼리가 행일 경우
            if (line == 'R') {
                // 먼저 지워진 행인지 살펴본다.
                // fenWickTrees[열][num] - fenWickTrees[열][num-1]을 할 경우
                // num번째 열의 값을 구할 수 있는데 이 값이 0이라면 해당 행이 지워진 경우
                // 0을 기록
                if (getSum(1, num) - getSum(1, num - 1) == 0)
                    sb.append(0).append("\n");
                else {
                    // 그렇지 않을 경우
                    // 0 ~ n까지의 합을 구하고, n에서 지워진 열의 개수만큼을 제하고 num을 곱해준 값을 더한다.
                    // 해당 값이 해당 쿼리의 값.
                    // 쿼리 결과 기록
                    sb.append(getSum(0, n) + (long) (n - deletedCols) * num).append("\n");
                    // fenwickTrees[열][num]에 -num 값을 더해 0으로 만든다.
                    putValue(1, num, -num);
                    // 지워진 행의 개수 증가
                    deletedRows++;
                }
            } else {
                // 들어온 쿼리가 열일 경우
                // 행과 동일한 작업을 한다.
                if (getSum(0, num) - getSum(0, num - 1) == 0)
                    sb.append(0).append("\n");
                else {
                    sb.append(getSum(1, n) + (long) (n - deletedRows) * num).append("\n");
                    putValue(0, num, -num);
                    deletedCols++;
                }
            }
        }
        // 전체 쿼리 결과 출력
        System.out.print(sb);
    }
    
    // 누적합을 구한다.
    // line이 0일 경우 행, 1일 경우 열
    static long getSum(int line, int idx) {
        long sum = 0;
        while (idx > 0) {
            sum += fenwickTrees[line][idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // idx칸에 value 값을 더한다.
    // line이 0일 경우 행, 1일 경우 열
    static void putValue(int line, int idx, int value) {
        while (idx < fenwickTrees[line].length) {
            fenwickTrees[line][idx] += value;
            idx += (idx & -idx);
        }
    }
}