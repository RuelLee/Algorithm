/*
 Author : Ruel
 Problem : Baekjoon 23254번 나는 기말고사형 인간이야
 Problem address : https://www.acmicpc.net/problem/23254
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23254_나는기말고사형인간이야;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 기말고사까지 24 * n 시간이 남아있고
        // m개의 과목에 대해, 현재 a1, a2, ... , am만큼의 점수를 받을 수 있으며
        // 각 과목을 한 시간 공부할 때마다 b1, b2, ... , bm만큼의 점수를 더 받을 수 있다고 한다.
        // 각 점수는 최대 100점을 초과할 수 없다.
        // 전체 과목 점수의 합이 최대로 되도록 공부하려할 때,
        // 전체 과목 점수의 합은?
        //
        // 우선순위큐 문제
        // 먼저, 시간당 점수 효율이 가장 좋은 과목부터 공부를 한다.
        // 다만, 점수가 100점을 넘을 수 없기 때문에, 100점을 초과하는 시간에 대해서는
        // 따로 효율을 변경시켜줘야한다.
        // 예를 들어, ai가 50, bi 4라고 한다면
        // 나머지 50점 중에 48점은 시간 당 효율이 4인 공부를 할 수 있으나
        // 나머지 2점을 올리기 위해서는 1시간을 온전히 공부해야하기 때문에 효율이 2로 변경된다는 점을 유의하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 시험까지 남은 n일, m개의 과목
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 현재 받을 수 있는 점수
        int[] as = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 과목의 시간 당 성적 향상 점수
        int[] bs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 효율에 따라 내림차순 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(bs[o2], bs[o1]));
        for (int i = 0; i < bs.length; i++)
            priorityQueue.offer(i);
        
        // 남은 시간
        int remainTime = n * 24;
        // 공부할 과목이 없어지거나, 남은 시간이 없어질 때까지
        while (!priorityQueue.isEmpty() && remainTime > 0) {
            int current = priorityQueue.poll();
            
            // 점수와 효율을 비교하여 최대 시간을 구하고
            // 그 시간과 남은 시간을 비교하여 
            // 현재 효율로 공부할 수 있는 최대 시간을 구한다.
            int studyTime = Math.min((100 - as[current]) / bs[current], remainTime);
            // 잔여 시간 차감
            remainTime -= studyTime;
            // 점수 향상
            as[current] += bs[current] * studyTime;
            
            // 남은 시간이 있는데 해당 과목 점수가 100점이 되지 않은 경우
            if (remainTime != 0 && as[current] != 100) {
                // 남은 점수를 위해 온전히 시간을 투자해야만 한다.
                bs[current] = 100 - as[current];
                // 다시 큐에 추가
                priorityQueue.offer(current);
            }
        }
        
        // 전체 점수의 합 출력
        System.out.println(Arrays.stream(as).sum());
    }
}