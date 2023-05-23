/*
 Author : Ruel
 Problem : Baekjoon 22254번 공정 컨설턴트 호석
 Problem address : https://www.acmicpc.net/problem/22254
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22254_공정컨설턴트호석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대 x 시간 동안 n개의 선물을 생산해야한다.
        // 선물들은 자기보다 앞 번호 선물들의 생산 라인이 확정된 후, 
        // 사용 시간이 가장 적은 라인 중 하나에 배정된다.
        // 각 선물들의 생산 시간이 주어질 때
        // 최소 몇 개의 라인이 있어야 x 시간 내에 모든 선물을 생산할 수 있는가?
        //
        // 이분 탐색 문제
        // 1 ~ n개의 생산 라인의 범위를 갖고서 이분 탐색을 통해
        // 모든 선물들의 생산이 가능한 최소 라인의 수를 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 선물
        int n = Integer.parseInt(st.nextToken());
        // x시간의 시간 제한
        int x = Integer.parseInt(st.nextToken());
        // 선물들
        int[] presents = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 이분 탐색을 통해 최소 생산 라인의 수를 구한다.
        int start = 1;
        int end = n;
        while (start < end) {
            int mid = (start + end) / 2;
            // mid개의 생산 라인으로 모든 선물을 생산하는 것이 가능하다면
            // 범위를 start ~ mid까지로 줄인다.
            if (possible(mid, x, presents))
                end = mid;
            // 불가능하다면
            // 범위를 mid + 1 ~ end까지로 줄인다.
            else
                start = mid + 1;
        }

        // 찾은 최소 생산 라인의 수를 출력한다.
        System.out.println(start);
    }

    // 해당 라인과 시간 제한 동안 모든 선물들을 만드는 것이 가능한지 판별한다.
    static boolean possible(int lines, int timeLimit, int[] presents) {
        // 우선순위큐
        // 생산 라인을 의미하며, 가장 이른 시간에 끝나는 생산라인에 우선적으로 다음 선물을 생산한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 모든 선물들을 살펴보며
        for (int present : presents) {
            // 아직 모든 라인들이 생산 중이 아니라면
            // 계속 새 라인에 선물들을 추가한다.
            if (priorityQueue.size() < lines)
                priorityQueue.offer(present);
            else {      // 모든 라인이 생산 중이라면
                // 가장 이른 시간에 생산이 끝나는 라인에서
                // 이번 선물을 생산한다.
                int endTime = priorityQueue.poll() + present;
                // 만약 이번 선물의 생산 종료 시간이 시간 제한을 넘는다면 false 반환.
                if (endTime > timeLimit)
                    return false;
                // 아니라면 생산 라인에 선물 추가
                priorityQueue.offer(endTime);
            }
        }
        // 모든 과정을 마치는 동안 false 반환을 하지 않았다면
        // 선물 생산이 가능한 경우.
        // true 반환.
        return true;
    }
}