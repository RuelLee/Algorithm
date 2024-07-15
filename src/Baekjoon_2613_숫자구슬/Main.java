/*
 Author : Ruel
 Problem : Baekjoon 2613번 숫자구슬
 Problem address : https://www.acmicpc.net/problem/2613
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2613_숫자구슬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int m;

    public static void main(String[] args) throws IOException {
        // n개의 구슬을 m개의 그룹으로 나누고자 한다.
        // 그 때 그룹의 합 중 최대값을 최소로 만들고자 한다.
        // 그 때의 최소 최대값과 각 그룹의 구슬 개수를 출력하라
        //
        // 이분탐색 문제
        // 이분 탐색으로 합 최대값의 최소값을 찾는다.
        // 그 후 찾은 값을 바탕으로 각 그룹의 속한 구슬 수를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구슬, m개의 그룹
        int n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 구슬들
        int[] bids = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 이분 탐색
        // 각 그룹 합 중 최대값 중 가능한 가장 작은 값을 구한다.
        int start = 1;
        int end = 300 * 100;
        while (start < end) {
            int mid = (start + end) / 2;
            if (separateValue(mid, bids))
                end = mid;
            else
                start = mid + 1;
        }

        // 찾은 값을 토대로 구슬을 나눈다.
        Queue<Integer> queue = new LinkedList<>();
        int sum = 0;
        int count = 0;
        for (int bid : bids) {
            // 합이 찾은 값보다 커질 경우 그룹을 나눈다.
            if (sum + bid > start) {
                queue.offer(count);
                sum = bid;
                count = 1;
            } else {        // 아니면 이전 그룹에 더한다.
                count++;
                sum += bid;
            }
        }
        // 마지막 그룹 추가
        queue.offer(count);

        StringBuilder sb = new StringBuilder();
        sb.append(start).append("\n");
        // 나눈 그룹의 수가 m보다 작을 수도 있다.
        // 이 때는 구슬 수가 2개 이상인 임의의 그룹을 여러개의 그룹으로 분리한다.
        // 추가로 필요한 그룹의 수 count
        count = m - queue.size();
        while (!queue.isEmpty()) {
            // 구슬 수
            int current = queue.poll();
            // 만약 필요한 추가 그룹의 수가 있고
            // 현재 그룹에 속한 구슬이 2개 이상일 경우
            // 여러개의 그룹으로 분리한다.
            while (count > 0 && current > 1) {
                sb.append(1).append(" ");
                count--;
                current--;
            }
            sb.append(current).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }

    // 그룹의 합을 value이하의 m개 그룹으로 나눌 수 있는지 확인한다.
    static boolean separateValue(int value, int[] bids) {
        int count = 0;
        int sum = 0;
        for (int bid : bids) {
            if (bid > value)
                return false;
            else if (sum + bid > value) {
                count++;
                sum = bid;
            } else
                sum += bid;
        }
        return count + 1 <= m;
    }
}