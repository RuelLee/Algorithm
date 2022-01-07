/*
 Author : Ruel
 Problem : Baekjoon 2212번 센서
 Problem address : https://www.acmicpc.net/problem/2212
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 센서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문제를 이해하기가 어려웠던 문제
        // n개의 센서가 주어지고, 그 중 k개의 집중국을 세울 수 있으며, 각 센서는 집중국에 연결되어야한다
        // 이 때 집중국들의 센서 수신 가능 영역의 최소 범위를 구하는 문제
        // 이게 무슨 문제인지 고민해봤는데 결국에는 주어진 n개의 센서들을
        // k개의 범위로 나누는 문제였다
        // 첫 센서부터 마지막 센서까지 모두 연결되어있다고 생각하고
        // 그 중에서 거리가 가장 만 두 센서 간의 연결 k - 1개를 골라 끊는다고 생각하자
        // 그럼 n개의 센서들을 k개의 최소 길이 구간으로 나눌 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());

        int[] nums = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());
        // 정렬해서 순차적으로 센서들의 거리를 살펴봐야한다.
        Arrays.sort(nums);

        // 이웃한 센서 간의 길이를 계산하고, 내림차순으로 정렬.
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
        for (int i = 1; i < nums.length; i++)
            pq.offer(nums[i] - nums[i - 1]);

        // k - 1개의 연결을 끊어준다.
        for (int i = 1; i < k; i++) {
            if (!pq.isEmpty())
                pq.poll();
        }

        // 남아있는 연결들의 길이를 모두 더해준다.
        int sum = 0;
        while (!pq.isEmpty())
            sum += pq.poll();
        System.out.println(sum);
    }
}