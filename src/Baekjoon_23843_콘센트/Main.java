/*
 Author : Ruel
 Problem : Baekjoon 23843번 콘센트
 Problem address : https://www.acmicpc.net/problem/23843
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23843_콘센트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 전자기기와 m개의 콘센트가 주어진다.
        // 각 전자기기는 2^k 만큼의 충전시간이 필요하다
        // 모든 전자기기를 충전하는데 드는 최소 시간을 멀마인가?
        //
        // 그리디, 우선순위큐 문제
        // 전자기기를 충전하는데 정해진 순서가 있지 않으므로
        // 우리가 원하는대로 정렬하여 충전하는 것이 가능하다.
        // 따라서 콘센트에 충전 시간이 가장 오래 걸리는 것부터 충전하면서
        // 먼저 충전된 것은 제외해나가며 계속해서 다음 전자기기를 충전한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 전자기기와 m개의 콘센트
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 전자기기를 충전하는데 걸리는 시간
        int[] electronics = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬
        Arrays.sort(electronics);
        
        // 콘센트
        PriorityQueue<Integer> consents = new PriorityQueue<>();
        // 경과 시간
        int time = 0;
        // 오름차순 정렬되어있으므로 역순으로 계산해나간다.
        for (int i = n - 1; i >= 0; i--) {
            // 만약 콘센트가 모두 가득차 있다면
            // 가장 먼저 충전이 완료되는 전자기기의 시간으로 시간을 넘기고
            // 해당 전자기기를 콘센트에서 분리한다.
            if (consents.size() == m)
                time = consents.poll();

            // 비어있는 콘센트에 남은 전자기기들 중 가장 시간이 오래 걸리는
            // 기기(electronics[i])를 충전한다.
            consents.offer(time + electronics[i]);
        }

        // 콘센트에 꽂혀있는 모든 전자기기의 충전시간으로 넘긴다.
        // 가장 오래 걸리는 충전 시간이 time에 기록된다.
        while (!consents.isEmpty())
            time = consents.poll();

        // 답안 출력.
        System.out.println(time);
    }
}