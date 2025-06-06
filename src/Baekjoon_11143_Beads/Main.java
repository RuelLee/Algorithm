/*
 Author : Ruel
 Problem : Baekjoon 11143번 Beads
 Problem address : https://www.acmicpc.net/problem/11143
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11143_Beads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int b;
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 각각의 테스트케이스마다 b p q가 주어지고
        // 이는 박스의 개수 b개, 박스에 구슬을 넣는 행동 p번, 일정 범위 내의 구슬의 합을 구하는 쿼리 q개를 나타낸다.
        // p + q개의 줄에 다음과 같은 명령들이 주어진다.
        // P i a -> i번 박스에 a개의 구슬을 넣는다.
        // Q i j -> i ~ j번 박스의 구슬 합을 구한다.
        // Q의 답을 구하라
        //
        // 세그먼트 트리, 펜윅 트리 문제
        // 값이 변경되는 구간에 대한 누적합을 구하는 문제
        // 세그먼트 트리, 펜윅 트리로 시간 복잡도를 줄여 값을 구할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        
        // 펜윅 트리의 크기 b의 최대 값은 10만.
        fenwickTree = new int[100_001];
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 박스의 개수, 각각 행동의 횟수 p, q
            b = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            // 펜윅 트리에서 0 ~ b까지의 범위를 사용한다.
            // 초기화시켜준다.
            Arrays.fill(fenwickTree, 0, b + 1, 0);
            for (int i = 0; i < p + q; i++) {
                st = new StringTokenizer(br.readLine());
                // p라면 상자에 구슬을 넣고
                if (st.nextToken().charAt(0) == 'P')
                    put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                else {
                    // 그 외의 경우 start ~ end까지 범위의 박스에 들어있는 구슬 합을 기록한다.
                    int start = Integer.parseInt(st.nextToken());
                    int end = Integer.parseInt(st.nextToken());
                    sb.append(getSum(end) - getSum(start - 1)).append("\n");
                }
            }
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // 펜윅 트리를 활용하여 0 ~ box까지의 구슬 합을 구한다.
    static int getSum(int box) {
        int sum = 0;
        while (box > 0) {
            sum += fenwickTree[box];
            box -= (box & -box);
        }
        return sum;
    }

    // box에 beads를 추가한다.
    static void put(int box, int beads) {
        while (box <= b) {
            fenwickTree[box] += beads;
            box += (box & -box);
        }
    }
}