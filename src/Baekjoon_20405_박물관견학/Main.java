/*
 Author : Ruel
 Problem : Baekjoon 30405번 박물관 견학
 Problem address : https://www.acmicpc.net/problem/30405
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20405_박물관견학;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // 박물관에 m개의 전시관이 있으며, 이는 일렬로 연결되어있다.
        // 각 전시관 사이 거리는 |i - j|로 정의된다.
        // n마리의 고양이들이 있으며, 각 고양이들에 대해서
        // k p1 p2 .. pk가 주어지며 출입구로 들어와서 p1 ~ pk까지 순서대로 방문 후 출입구를 통해 다시 나간다.
        // 고양이들의 이동 거리 합을 최소가 되도록 전시관 중 하나에 출입구를 만들고 싶다.
        // 어느 전시관에 출입구를 만드는 것이 좋은가?
        //
        // 누적합, 펜윅 트리
        // 고양이들의 이동 경로는 중요하지 않고, 처음 방문하는 전시관과 마지막 방문하는 전시관만이 중요하다
        // 해당 위치에서 출입구까지 이동하는 거리만이 변하기 때문
        // 따라서 전체 고양이들의 처음과 마지막 전시관에 대해서 펜윅 트리로 누적합을 계산한다.
        // 그 후 가상의 0번 전시관에 출입구를 만들었을 때, 출입구까지의 이동 거리 합을 구한다.
        // 그 후, 해당 값을 가지고서 1번 전시관에 출입구를 만들었을 때는
        // 0번 전시관에 입구를 만들었을 때보다 
        // 0번 전시관이 첫 혹은 마지막 전시관이었던 경우들에 대해서 거리가 1 증가하지만
        // 1 ~ m번 전시관이 첫 혹은 마지막 전시관이었던 경우들에 대해서는 거리가 1 감소한다.
        // 위와 같은 방법으로 1 ~ m까지 모든 경우에 대해 계산하고 이동 거리 합이 최소가 되는 곳을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 고양이, m개의 전시관
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 고양이들의 이동 경로
        int[][] cats = new int[n][];
        // 첫 혹은 마지막 전시관들의 누적합
        fenwickTree = new int[m + 1];
        for (int i = 0; i < cats.length; i++) {
            st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());
            cats[i] = new int[size];
            for (int j = 0; j < cats[i].length; j++)
                cats[i][j] = Integer.parseInt(st.nextToken());

            // 첫 방문 전시관
            accumulate(cats[i][0]);
            // 마지막 방문 전시관
            accumulate(cats[i][size - 1]);
        }
        
        // 값이 커질 수 있으므로 Long 타입으로 정의
        long psum = 0;
        // 가상의 0번 전시관까지 이르는 거리를 모두 계산.
        for (int i = 1; i <= m; i++)
            psum += (long) (getSum(i) - getSum(i - 1)) * i;
        // 최소 이동 거리
        long minPsum = Long.MAX_VALUE;
        // 그 때의 전시관 번호
        int answer = 0;

        // 순차적으로 1번부터 모든 전시관들에 대해
        // 출입구를 만들었을 때, 이동 거리 변화를 살펴본다.
        for (int i = 1; i <= m; i++) {
            // (i - 1)에서 i번으로 출입구를 옮긴다면
            // 1 ~ (i - 1)번 전시관들에 대해서는 거리가 1 증가하고
            // i ~ m번 전시관들에 대해서는 거리가 1 감소한다.
            psum += (2 * getSum(i - 1) - getSum(m));
            // 이동 거리가 감소한다면
            if (minPsum > psum) {
                // 최소 이동 거리값 갱신 및 그 때의 전시관 기록
                minPsum = psum;
                answer = i;
            }
        }
        // 답안 출력
        System.out.println(answer);
    }
    
    // 1 ~ idx까지의 전시관을 첫 혹은 마지막 방문 전시관인 경우의 수
    static int getSum(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // idx번째 전시관을 첫 혹은 마지막 전시관으로 방문하는 경우의 수를 하나 증가.
    static void accumulate(int idx) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx]++;
            idx += (idx & -idx);
        }
    }
}