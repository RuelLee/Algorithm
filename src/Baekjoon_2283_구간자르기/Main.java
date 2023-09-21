/*
 Author : Ruel
 Problem : Baekjoon 2283번 구간 자르기
 Problem address : https://www.acmicpc.net/problem/2283
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2283_구간자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n개의 선분들이 주어진다.
        // 지정한 구역에 포함되는 선분들의 길이 합을 k가 되고자할 때
        // 해당 구역을 구하라
        //
        // 누적합, 두 포인터 문제
        // 최대 1000개의 선분, 좌표의 위치는 0이상 1,000,000이하로 주어진다.
        // 따라서 100만개의 공간에 일일이 해당 좌표에 속하는 선분들의 개수를 표시하는 건 비효율적이다.
        // 펜윅트리를 활용하여 해당 해당 지점에 속하는 선분의 개수들을 구하고
        // 두 포인터를 활용하여 포함되는 구간에 속한 선분들의 길이 합을 구하며
        // k값과 일치하는 경우가 있는지 살펴본다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 선분
        int n = Integer.parseInt(st.nextToken());
        // 원하는 길이 합 k
        int k = Integer.parseInt(st.nextToken());
        
        // 펜윅 트리
        fenwickTree = new int[1_000_002];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 선분
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            // 선분의 시작점에 +1
            inputValue(left + 1, 1);
            // 끝난 지점에 -1을 한다
            inputValue(right + 1, -1);
            // 해당 구간에 속하는 선분의 개수를 표현한다.
        }

        int left = 1;
        int right = 1;
        boolean findAnswer = false;
        // 0위치에 속하는 선분의 개수
        int lineSum = getSum(1);
        for (; left < fenwickTree.length - 1; left++) {
            // 현재 길이 합이 k를 넘지 않는다면 right + 1로 오른쪽 범위를 확장한다.
            while (right + 1 < fenwickTree.length && lineSum < k)
                lineSum += getSum(++right);
            
            // 만약 원하는 길이 합이 찾아졌다면 표시하고 반복문 종료
            if (lineSum == k) {
                findAnswer = true;
                break;
            }
            // left가 left + 1로 넘어가므로
            // left에 속하는 선분의 개수만큼을 줄여준다.
            lineSum -= getSum(left);
        }
        
        // 답을 찾았다면 해당하는 구간
        // 그렇지 않다면 0 0을 출력한다
        System.out.println(findAnswer ? (left - 1) + " " + right : "0 0");
    }

    // 0 ~ idx까지의 합을 구한다.
    // idx에 걸쳐있는 선분들의 개수를 구할 수 있따.
    static int getSum(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // 펜윅 트리 idx위치에 value 값을 추가한다.
    static void inputValue(int idx, int value) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += value;
            idx += (idx & -idx);
        }
    }
}