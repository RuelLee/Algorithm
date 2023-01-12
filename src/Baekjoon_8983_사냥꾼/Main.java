/*
 Author : Ruel
 Problem : Baekjoon 8983번 사냥꾼
 Problem address : https://www.acmicpc.net/problem/8983
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8983_사냥꾼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 사냥터가 있다.
        // m개의 사대, n개의 사냥감, 사정거리 l이 주어진다.
        // 각 사대에서 거리 l 이하의 사냥감만 사냥할 수 있으며,
        // 사대의 위치 xi와 동물의 위치 (aj, bj)의 거리는 |xi-aj| + bj로 계산한다.
        // 사냥할 수 있는 모든 사냥감의 수는?
        //
        // 정렬 문제
        // 사냥감들과 사대를 정렬하여 순서대로 살펴본다.
        // 큐를 통해, 앞으로 주어질 사대를 통해 사냥할 수 있는 사냥감들을 관리하며
        // 더 이상 사냥할 수 있는 사냥감들은 제외해나가며 사냥할 수 있는 사냥감들을 찾아간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 주어지는 사대, 사냥감, 사정 거리
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 사대 오름차순 정렬
        int[] launchers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(launchers);

        // 사냥감들
        int[][] preys = new int[n][2];
        for (int i = 0; i < preys.length; i++)
            preys[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 사냥감들을 두 좌표의 합으로 정렬한다
        Arrays.sort(preys, (o1, o2) -> Integer.compare(o1[0] + o1[1], o2[0] + o2[1]));

        int j = 0;
        // 큐 안에 앞으로 사냥될 수 있는 사냥감들을 관리한다.
        Queue<Integer> queue = new LinkedList<>();
        // 사냥할 수 있는 사냥감의 수
        int count = 0;
        // 사대를 차례대로 살펴본다.
        for (int launcher : launchers) {
            // 사냥감들 중 현재 사대에서 사냥할 수 있는 사정 거리 내의 사냥감이라면 큐에 추가.
            while (j < n && preys[j][0] + preys[j][1] <= launcher + l)
                queue.offer(j++);

            int size = queue.size();
            // 큐의 모든 사냥감들을 살펴본다.
            while (size-- > 0) {
                // 거리 계산
                int distance = Math.abs(launcher - preys[queue.peek()][0]) + preys[queue.peek()][1];
                
                // 사냥할 수 있다면
                // 큐에서 제거하고 count 증가
                if (distance <= l) {
                    queue.poll();
                    count++;
                } else if (preys[queue.peek()][0] <= launcher)      // 사냥할 수 없는데, 사대보다 적은 x 좌표를 갖고 있다면
                    // 앞으로는 거리가 더 멀어질 것이므로 사냥할 가능성이 없다. 큐에서 제거
                    queue.poll();
                else        // 현재 사대보다 큰 x좌표 값을 갖고 있다면 다음 사대에서 사냥할 수 있는 가능성이 있다. 큐의 마지막으로 추가.
                    queue.offer(queue.poll());
            }
        }
        // 전체 사냥한 사냥감의 수 출력.
        System.out.println(count);
    }
}