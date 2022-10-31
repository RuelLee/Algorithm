/*
 Author : Ruel
 Problem : Baekjoon 16978번 수열과 쿼리 22
 Problem address : https://www.acmicpc.net/problem/16978
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16978_수열과쿼리22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수와 m개의 쿼리가 주어진다
        // 1 i v : Ai = v로 변경한다.
        // 2 k i j :  k번째 1번 쿼리까지 적용되었을 때, Ai, Ai+1, ..., Aj의 합을 출력한다.
        // 1번 쿼리의 경우 1 ≤ i ≤ N, 1 ≤ v ≤ 1,000,000 이고, 2번 쿼리의 경우 1 ≤ i ≤ j ≤ N이고, 0 ≤ k ≤ (쿼리가 주어진 시점까지 있었던 1번 쿼리의 수)이다.
        //
        // 오프라인 쿼리, 누적합 문제
        // 오프라인 쿼리는 쿼리의 순서를 변경하여 계산한다.
        // 2번 쿼리의 경우, k번째 1번 쿼리가 적용됐을 때를 기준으로 답을 도출하므로
        // 2번 쿼리의 순서를 변경하여 k가 낮은 순부터 쿼리를 처리해나가자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 주어지는 n개 수
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 기본적으로는 누적합 문제이므로 펜윅 트리를 사용했다.
        long[] fenwickTree = new long[n + 1];
        for (int i = 0; i < nums.length; i++)
            modifyValue(i + 1, nums[i], fenwickTree);

        int m = Integer.parseInt(br.readLine());
        // 1번 쿼리들
        Queue<int[]> firstQueries = new LinkedList<>();
        // 2번 쿼리들은 k에 대해 최소 힙 우선순위큐를 통해 처리한다.
        PriorityQueue<int[]> secondQueries = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

        // 1번 쿼리의 순서를 세고
        int firstCount = 1;
        // 2번 쿼리의 순서 또한 센다(-> 답의 순서)
        int secondCount = 0;
        for (int j = 0; j < m; j++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            if (Integer.parseInt(st.nextToken()) == 1)
                firstQueries.offer(new int[]{firstCount++, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
            else
                secondQueries.offer(new int[]{secondCount++, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});

        }

        // 2번 쿼리가 순서대로 계산되는 것이 아니기 때문에 일단 배열에 담자.
        long[] answers = new long[secondQueries.size()];
        // 2번 쿼리를 모두 처리할 때까지
        while (!secondQueries.isEmpty()) {
            int[] query = secondQueries.poll();
            // 2번 쿼리의 k보다 같거나 작은 1번 쿼리들을 모두 처리한다.
            while (!firstQueries.isEmpty() &&
                    query[1] >= firstQueries.peek()[0]) {
                int[] q = firstQueries.poll();
                modifyValue(q[1], q[2] - nums[q[1] - 1], fenwickTree);
                nums[q[1] - 1] = q[2];
            }
            // 2번 쿼리를 처리한다.
            answers[query[0]] = getPsum(query[3], fenwickTree) - getPsum(query[2] - 1, fenwickTree);
        }

        // answer 배열에 기록된 값들을 순차적으로 출력해준다.
        StringBuilder sb = new StringBuilder();
        for (long answer : answers)
            sb.append(answer).append("\n");
        System.out.print(sb);
    }

    // 펜윅 트리의 값을 차이값을 바탕으로 갱신한다.
    static void modifyValue(int loc, int diff, long[] fenwickTree) {
        while (loc < fenwickTree.length) {
            fenwickTree[loc] += diff;
            loc += (loc & -loc);
        }
    }

    // 1 ~ loc까지의 누적합을 구한다.
    static long getPsum(int loc, long[] fenwickTree) {
        long sum = 0;
        while (loc > 0) {
            sum += fenwickTree[loc];
            loc -= (loc & -loc);
        }
        return sum;
    }
}