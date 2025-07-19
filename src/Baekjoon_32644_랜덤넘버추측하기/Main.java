/*
 Author : Ruel
 Problem : Baekjoon 32644번 랜덤 넘버 추측하기
 Problem address : https://www.acmicpc.net/problem/32644
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32644_랜덤넘버추측하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n명의 학생이 1 ~ n까지의 번호를 받는다.
        // 각 학생들이 갖는 추첨권의 개수가 주어진다.
        // 3명의 학생이 1 2 3개의 추첨권을 받는다면
        // 1 2 2 3 3 3 과 같이 리스트에 담는다.
        // 1 ~ 최대 리스트의 크기에 해당하는 수 중 하나를 랜덤하게 뽑아
        // 해당 순서의 학생이 당첨된다. 그리고 해당 학생의 추첨권은 모두 제거한다.
        // m명의 당첨된 학생이 주어질 때, 랜덤하게 뽑은 수가 무엇일지 추측하라.
        // 그러한 경우의 수가 여러가지일 경우, 아무거나 출력한다.
        //
        // 펜윅 트리 문제
        // 각 추첨권의 개수만큼 차례대로 누적되므로, 펜윅 트리를 통해 누적합 처리를 해주고
        // 해당하는 학생이 갖는 범위의 추첨권 순서 중 아무거나 출력하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, m번의 추첨
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 각 학생들이 가진 추첨권의 수
        int[] counts = new int[n + 1];
        for (int i = 1; i < counts.length; i++)
            counts[i] = Integer.parseInt(st.nextToken());
        
        // 펜윅 트리
        fenwickTree = new int[n + 1];
        for (int i = 1; i < counts.length; i++)
            addValue(i, counts[i]);

        StringBuilder sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            // 당첨된 학생
            int num = Integer.parseInt(st.nextToken());
            // 당첨된 학생이 가진 마지막 추첨권이 번호 기록
            sb.append(getSum(num)).append(" ");
            // 해당 학생이 가진 추첨권 모두 제거
            addValue(num, -counts[num]);
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }

    // idx번까지의 누적합을 구한다.
    static int getSum(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // idx번째에 val을 추가한다.
    static void addValue(int idx, int val) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += val;
            idx += (idx & -idx);
        }
    }
}