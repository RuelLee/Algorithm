/*
 Author : Ruel
 Problem : Baekjoon 2229번 조 짜기
 Problem address : https://www.acmicpc.net/problem/2229
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 조짜기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] segmentTree;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // 학생들의 실력이 주어질 때, 순차적으로 1명 이상의 조를 짠다.
        // 이 때 조 내의 최소/최대 실력의 차이가 클수록 잘 짜여진 정도가 된다.(조 인원이 1명인 경우 잘 짜여진 정도는 0)
        // 전체 조의 잘 짜여진 정도의 최대 값은 얼마인가
        // DP문제
        // 해당 조와 좌우의 학생을 추가적으로 조에 편입하거나 별개의 조로 계산할 수 있다.
        // 구간에 따른 최소/최대 실력 차이 값이 필요해, 이를 세그먼트 트리로 구성해 보았다. 학생 수가 더 늘어난다면 시간 상 이득을 볼 것이라 생각한다.
        // 전체 학생 수가 그리 크지 않으므로 일일이 계산하는 방법으로 구현했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int size = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1;
        segmentTree = new int[size][2];
        dp = new int[n][n];     // 구간 별로 조들의 잘짜여진 정도의 최대합을 저장할 DP 공간
        for (int i = 0; i < n; i++)     // 학생의 실력 값을 저장한다.
            inputValue(i, Integer.parseInt(st.nextToken()));

        for (int diff = 1; diff < dp.length; diff++) {          // 차이는 1부터
            for (int i = 0; i + diff < dp.length; i++) {            // 조의 마지막 학생의 번호가 범위를 넘지 않는 선에서
                int j = i + diff;       // i는 조의 첫 학생, j는 조의 마지막 학생
                int[] minMax = getValue(i, j, 0, n - 1, 1);
                dp[i][j] = minMax[1] - minMax[0];       // 한 조로 구성할 경우의 잘 짜여진 정도의 값을 일단 넣어둔다.

                for (int k = i; k < j; k++)     // 그 후, i ~ k, k+1 ~ j 두 개의 별개의 조로 따졌을 때의 잘 짜여진 정도의 값이 더 큰지 확인하고 크다면 갱신해준다.
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k + 1][j]);
            }
        }
        // 최종적으로 1번 학생부터 n번 학생까지 조를 이뤘을 때 잘 짜여진 정도의 최대합은 dp[1][n]에 저장된다.
        System.out.println(dp[0][n - 1]);
    }

    static int[] getValue(int targetStart, int targetEnd, int start, int end, int idx) {
        int middle = start;
        while (start < end) {
            middle = (start + end) / 2;
            if (targetStart > middle) {
                start = middle + 1;
                idx = idx * 2 + 1;
            } else if (targetEnd <= middle) {
                end = middle;
                idx = idx * 2;
            } else
                break;
        }
        if (targetStart == start && targetEnd == end)
            return segmentTree[idx];
        int[] leftReturned = getValue(targetStart, middle, start, middle, idx * 2);
        int[] rightReturned = getValue(middle + 1, targetEnd, middle + 1, end, idx * 2 + 1);
        return new int[]{Math.min(leftReturned[0], rightReturned[0]), Math.max(leftReturned[1], rightReturned[1])};
    }

    static void inputValue(int target, int value) {
        int start = 0;
        int end = dp.length - 1;
        int idx = 1;

        while (start < end) {
            int middle = (start + end) / 2;
            if (target > middle) {
                start = middle + 1;
                idx = idx * 2 + 1;
            } else {
                end = middle;
                idx = idx * 2;
            }
        }
        segmentTree[idx][0] = segmentTree[idx][1] = value;
        while (idx > 1) {
            segmentTree[idx / 2][0] = Math.min(segmentTree[idx][0], idx % 2 == 0 ? segmentTree[idx + 1][0] : segmentTree[idx - 1][0]);
            segmentTree[idx / 2][1] = Math.max(segmentTree[idx][1], idx % 2 == 0 ? segmentTree[idx + 1][1] : segmentTree[idx - 1][1]);
            idx /= 2;
        }
    }
}