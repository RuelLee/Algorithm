/*
 Author : Ruel
 Problem : Baekjoon 13547번 수열과 쿼리 5
 Problem address : https://www.acmicpc.net/problem/13547
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13547_수열과쿼리5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수와 m개의 쿼리가 주어진다.
        // 쿼리는
        // i j : Ai, ... , Aj에 존재하는 서로 다른 수의 개수를 출력한다
        //
        // mo's 쿼리, 오프라인 쿼리
        // 수가 바뀌는 쿼리가 존재하지 않기 때문에
        // 쿼리를 편한 순서대로 우선적으로 처리하는 것이 가능하다.
        // 따라서 두 포인터를 가지고서 점차 범위를 확장해나가며 쿼리를 순서대로 처리하는 것이 좋다.
        // 어떤 순서에 따라 쿼리를 처리할 것인가라는 문제는 남는다.
        // 단순히 앞 포인터가 작은 것을 우선하되, 같다면 뒷포인터가 작은 순으로 정렬을 해버리면
        // 1 100, 2 5, 2 100 같은 경우를 처리할 때
        // j가 100 -> 5 -> 100으로 많이 왔다갔다거리게 된다.
        // 위 경우에는 2 5 -> 1 100 -> 2 100 순으로 처리하는 것이 가장 빠르게 처리할 수 있다.
        // 그래서 살펴보니 시작 포인터에 대한 정렬을 제곱근으로 처리하는 방법이 있었다.
        // 1과 2의 제곱근을 정수 처리하면 모두 1이므로
        // 뒷 포인터에 대해서만 신경쓰고 정리하는 순이었다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // m개의 쿼리
        int m = Integer.parseInt(br.readLine());
        int[][] queries = new int[m][];
        for (int i = 0; i < queries.length; i++) {
            queries[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            queries[i][0]--;
            queries[i][1]--;
        }
        
        // 우선순위큐에 쿼리들을 담되
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            // i에 대해서는 제곱근 처리
            int o1sqrt = (int) Math.sqrt(queries[o1][0]);
            int o2sqrt = (int) Math.sqrt(queries[o2][0]);
            
            // i가 같다면
            // j에 대해선 오름차순 정렬
            if (o1sqrt == o2sqrt)
                return Integer.compare(queries[o1][1], queries[o2][1]);
            // i에 대해서는 제곱근으로 오름차순
            return Integer.compare(o1sqrt, o2sqrt);
        });
        for (int i = 0; i < queries.length; i++)
            priorityQueue.offer(i);
        
        // 답안
        int[] answer = new int[m];
        // 들어있는 수의 개수
        int[] counts = new int[1_000_001];
        // 초기값으로 0번째 수를 넣어두고 시작.
        counts[nums[0]]++;
        int count = 1;
        int i = 0;
        int j = 0;
        
        // 우선순위큐에서 하나씩 뽑아가며 정렬된 순으로 처리
        while (!priorityQueue.isEmpty()) {
            int[] query = queries[priorityQueue.peek()];
            // 뒷 포인터가 더 뒤로 가야하는 경우
            while (j < query[1]) {
                // 새로 추가된 수가 처음 들어왔다면 count 하나 증가
                if (++counts[nums[++j]] == 1)
                    count++;
            }
            // 뒷 포인터가 앞으로 와야하는 경우
            while (j > query[1]) {
                // 제외된 수가 범위 내에서 사라진 경우
                // count 감소
                if (--counts[nums[j--]] == 0)
                    count--;
            }
            
            // 앞 포인터에 대해서도 동일.
            while (i < query[0]) {
                if (--counts[nums[i++]] == 0)
                    count--;
            }
            while (i > query[0]) {
                if (++counts[nums[--i]] == 1)
                    count++;
            }
            
            // 현재 쿼리에 대한 답을 기록
            answer[priorityQueue.poll()] = count;
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int an : answer)
            sb.append(an).append("\n");
        // 출력
        System.out.print(sb);
    }
}