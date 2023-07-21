/*
 Author : Ruel
 Problem : Baekjoon 28018번 시간이 겹칠까?
 Problem address : https://www.acmicpc.net/problem/28018
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28018_시간이겹칠까;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n명의 학생이 도서관을 이용한다.
        // 1 ~ 1_000_000까지 각 학생이 이용하는 시간이 주어졌을 때
        // q개의 시간들에 대해 이용할 수 없는 좌석의 수(=학생의 수)를 출력하라
        //
        // 누적합 문제
        // fenwick 트리를 이용해서, 특정 시작 시간에는 +1, 종료 시간 + 1에는 -1을 입력한다
        // 그런 후, 1 ~ 해당 시간까지의 누적합을 구하면 현재 이용하고 있는 학생의 수가 계산된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 학생
        int n = Integer.parseInt(br.readLine());
        
        // 펜윅 트리
        fenwickTree = new int[1_000_000 + 1];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            // 시작 시간에는 +1
            inputValue(s, 1);
            // 나가는 시간 +1에는 -1
            inputValue(e + 1, -1);
        }
        
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            // 특정 시간
            int time = Integer.parseInt(st.nextToken());
            // 에 이용하는 학생의 수는 1 ~ time까지의 누적합을 구한다.
            sb.append(getSum(time)).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // idx 까지의 누적합
    static int getSum(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // idx에 value를 누적시킨다.
    static void inputValue(int idx, int value) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += value;
            idx += (idx & -idx);
        }
    }
}