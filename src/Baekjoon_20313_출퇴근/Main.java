/*
 Author : Ruel
 Problem : Baekjoon 20313번 출퇴근
 Problem address : https://www.acmicpc.net/problem/20313
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20313_출퇴근;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Seek {
    int magic;
    int end;
    long time;

    public Seek(int magic, int end, long time) {
        this.magic = magic;
        this.end = end;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 건물, m개의 도로가 주어진다.
        // 가는 동안 건물 내에서 최대 k번의 마법을 사용할 수 있으며
        // 사용하게 되면, 모든 도로에 마법이 적용되어 이동 시간이 변하게 된다.
        // a 건물에서 b 건물을 가는 동안 최대 k번의 마법을 사용하여
        // 도달할 수 있는 최소 시간은?
        //
        // dijkstra 문제
        // minTimes[건물][마법사용횟수] = 최소 도달 시간으로 계산하여
        // 건물과 마법 사용 횟수에 따른 최소 도착 시간을 계산한다.
        // 그리곤 해당 건물에서 다음 건물로 갈 때 현재 마법 사용 횟수 이상에 대해서 지속적으로 계산하며
        // b건물에 도달할 때 최소 시간을 계산한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 도로 연결 정보
        int[][] roads = new int[m][];
        for (int i = 0; i < m; i++)
            roads[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 마법을 사용할 때마다 변하는 도로의 이동 시간
        int k = Integer.parseInt(br.readLine());
        int[][] times = new int[k + 1][m];
        // 0회 사용했을 때는 위에 도로 연결 정보에서 주어졌으므로
        // 해당 값을 가져온다.
        for (int i = 0; i < times[0].length; i++)
            times[0][i] = roads[i][2];
        // 1 ~ k회 사용했을 때의 정보
        for (int i = 1; i < times.length; i++)
            times[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 건물과 마법 사용 횟수에 따른 최소 도달 시간
        long[][] minTimes = new long[n + 1][k + 1];
        for (long[] mt : minTimes)
            Arrays.fill(mt, Long.MAX_VALUE);
        Arrays.fill(minTimes[a], 0);
        
        // 우선순위큐로 도달 시간이 적은 것부터 우선적 계산
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o.time));
        long answer = -1;
        // 첫 위치에서는 마법을 쓰지 않았건, 몇 회를 썼건 이동 시간 0
        for (int i = 0; i <= k; i++)
            priorityQueue.offer(new Seek(i, a, 0));
        // 우선순위큐가 빌 때까지
        while (!priorityQueue.isEmpty()) {
            Seek current = priorityQueue.poll();
            // 만약 이미 계산한 값이라면 건너뛴다.
            if (minTimes[current.end][current.magic] < current.time)
                continue;
            // 목표한 지점에 도달했다면 답을 기록하고 반복문 종료
            else if (current.end == b) {
                answer = current.time;
                break;
            }

            // 모든 도로를 살펴본다.
            for (int i = 0; i < roads.length; i++) {
                // 한 쪽 끝이 current.end가 아니라면 해당 도로는 건너뜀.
                if (roads[i][0] != current.end && roads[i][1] != current.end)
                    continue;
                
                // 맞다면 다른 한 쪽을 기록
                int next = (roads[i][0] == current.end ? roads[i][1] : roads[i][0]);
                // 현재 마법을 current.magic 만큼 사용했으므로
                // next에는 current.magic ~ k만큼 마법을 사용하여 도달할 수 있다.
                // 해당 경우의 수를 모두 살펴본다.
                for (int magic = current.magic; magic < minTimes[next].length; magic++) {
                    if (minTimes[next][magic] > minTimes[current.end][current.magic] + times[magic][i]) {
                        // 만약 최소 도달 시간을 갱신한다면
                        // 해당 값 기록
                        minTimes[next][magic] = minTimes[current.end][current.magic] + times[magic][i];
                        // 우선 순위큐 추가
                        priorityQueue.offer(new Seek(magic, next, minTimes[next][magic]));
                    }
                }
            }
        }
        // b 건물에 도달하는 최소 시간 출력
        System.out.println(answer);
    }
}