/*
 Author : Ruel
 Problem : Baekjoon 31091번 거짓말
 Problem address : https://www.acmicpc.net/problem/31091
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31091_거짓말;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람들이 자신을 포함하여, 몇 명 이상이 거짓말을 하고 있다고 주장하거나
        // 몇 명 이하가 거짓말을 하고 있다고 주장한다.
        // 거짓말을 하는 사람의 수로 가능한 것을 모두 구하라
        //
        // 누적합 문제
        // 이걸 어떻게 누적합으로...? 라는 생각이 드는 재밌는 문제
        // 어떤 사람이 a명 이상 거짓말을 하고 있다 라고 주장한다고 하자.
        // 위 사람이 거짓말이라면 0 ~ a-1명에 대해서 거짓말을 하고 있는 것이다.
        // 어떤 사람이 b명 이하로 거짓말을 하고 있다고 한다면 b+1 명 이상에 대해서 거짓말을 하고 있는 것이다.
        // 위와 같이 해당 사람이 거짓말을 하는 구간에 대해 누적합을 처리하고
        // 해당 거짓말을 하는 사람의 수와 누적합이 일치하는 곳을 찾아주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 사람
        int n = Integer.parseInt(br.readLine());

        int[] psums = new int[n + 2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());

            // -num 명 이하로 거짓말을 하고 있다고 말한 경우
            // 위 사람이 거짓말을 한 경우 -num + 1 ~ 구간에 대해 거짓말을 함.
            if (num <= 0)
                psums[-num + 1]++;
            else {
                // num 이상이 거짓말을 있다고 한 경우
                // 0 ~ num -1 구간에 대해 거짓말을 함.
                psums[0]++;
                psums[num]--;
            }
        }
        // 누적합
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1];

        // i와 psums[i]가 일치하는 경우를 찾는다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < psums.length; i++) {
            if (psums[i] == i)
                queue.offer(i);
        }

        // 답 작성
        StringBuilder sb = new StringBuilder();
        sb.append(queue.size()).append("\n");
        while (!queue.isEmpty())
            sb.append(queue.poll()).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 출력
        System.out.println(sb);
    }
}