/*
 Author : Ruel
 Problem : Baekjoon 17260번 계곡이 넘쳐흘러
 Problem address : https://www.acmicpc.net/problem/17260
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17260_계곡이넘쳐흘러;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int idx;
    int needHeight;

    public Seek(int idx, int needHeight) {
        this.idx = idx;
        this.needHeight = needHeight;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 계곡에서 물이 흐르고 있다.
        // 계곡마다 높이가 주어지며, 다른 계곡으로 물이 흐를 때
        // 현재 (계곡의 높이 - 다음 계곡 높이) + 다음 계곡의 높이 만큼 물이 튀어, 그 다음 계곡으로 갈 수 있게 된다.
        // n - 1 개의 계곡 간의 연결 정보가 주어질 때
        // k번 계곡이 아닌 곳에서 물길을 타고 k번 계곡으로 가는 길이 존재하는지 출력하라
        //
        // 우선순위큐, 그래프 탐색 문제
        // k번 계곡으로부터 역산한다.
        // k번 계곡에 도달하려면 최소 k번 계곡과 같은 높이를 갖고 있어야 도달할 수 있다.
        // 하지만 k번 계곡보다 높이가 낮다하더라도, 물의 튀는 높이가 k번 계곡보다 같거나 높다면 도달할 수 있다.
        // 따라서 k번 계곡에서 탐색을 시작하며, 이전 계곡에서 필요한 최소 높이를 인자로 갖으며 탐색한다.
        // 그러한 계곡이 발견되는 즉시 1을 출력하고 끝내면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 계곡, 도달 계곡 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 계곡의 높이
        st = new StringTokenizer(br.readLine());
        int[] heights = new int[n + 1];
        for (int i = 1; i < heights.length; i++)
            heights[i] = Integer.parseInt(st.nextToken());
        
        // 계곡 간 연결 정보
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            connections.get(u).add(v);
            connections.get(v).add(u);
        }
        
        // 해당 계곡에 도달하기 위해 필요한 최소 높이
        int[] needHeights = new int[n + 1];
        Arrays.fill(needHeights, Integer.MAX_VALUE);
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.needHeight));
        priorityQueue.offer(new Seek(k, heights[k]));
        // k번 계곡에는 k 높이로만 도달하면 된다.
        needHeights[k] = heights[k];
        boolean possible = false;
        while (!priorityQueue.isEmpty() && !possible) {
            // 현재 위치
            Seek current = priorityQueue.poll();
            // 이미 더 낮은 needHeight로 계산했거나
            // needHeight가 백만을 넘어가는 계곡은 없으므로 해당 경우들은
            // 건너뛴다.
            if (needHeights[current.idx] < current.needHeight ||
                    current.needHeight > 1_000_000)
                continue;
            
            for (int next : connections.get(current.idx)) {
                // next 계곡이 needHeight보다 같거나 큰 높이를 갖고 있다면
                // 가능한 계곡 발견!
                // 반복문 종료 후 1 출력
                if (heights[next] >= current.needHeight) {
                    possible = true;
                    break;
                } else if (needHeights[next] > (current.needHeight - heights[next]) + heights[next]) {
                    // 그렇지 않은 경우
                    // next 계곡이 needHeight보다 낮으므로
                    // next 계곡과 needHeight 간의 차이만큼 needHeight에 누적시켜
                    // 다음 계곡에서 필요한 높이를 계산한다.
                    // 그리고 탐색을 계속한다.
                    needHeights[next] = (current.needHeight - heights[next]) + current.needHeight;
                    priorityQueue.offer(new Seek(next, needHeights[next]));
                }
            }
        }
        
        // 결과 출력
        System.out.println(possible ? 1 : 0);
    }
}