/*
 Author : Ruel
 Problem : Baekjoon 12913번 매직 포션
 Problem address : https://www.acmicpc.net/problem/12913
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12913_매직포션;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Seek {
    int city;
    int magicPotion;
    double time;

    public Seek(int city, int magicPotion, double time) {
        this.city = city;
        this.magicPotion = magicPotion;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시와 각 도시를 이동하는데 걸리는 시간이 주어진다.
        // k개의 매직 포션이 주어지며, 도시 간을 이동할 때 하나 소모하며, 이동 시간을 반으로 줄여준다.
        // 0번 도시에서 1번 도시로 이동하는데 매직 포션을 사용하여 도착할 수 있는 최소 시간은?
        //
        // dijkstra, 최단 경로
        // 간단한 다익스트라 문제
        // n과 k가 크지 않으므로, minTimes[도시][사용한포션수] = 최소 시간
        // 도시와 포션 수에 따라 도착할 수 있는 최소 시간을 각각 구한다.
        // 그 후, 1번 도시에 도시에 도달하는 최소 시간을 사용한 포션 개수에 상관없이 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, k개의 포션
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 도시를 이동하는데 소요하는 시간
        int[][] adjMatrix = new int[n][n];
        for (int i = 0; i < adjMatrix.length; i++) {
            String input = br.readLine();
            for (int j = 0; j < adjMatrix[i].length; j++)
                adjMatrix[i][j] = input.charAt(j) - '0';
        }

        // minTimes[도시][사용한포션수] = 최소 시간
        double[][] minTimes = new double[n][k + 1];
        for (double[] mt : minTimes)
            Arrays.fill(mt, Double.MAX_VALUE);
        Arrays.fill(minTimes[0], 0);

        // dijkstra
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(o -> o.time));
        priorityQueue.offer(new Seek(0, 0, 0.0));
        while (!priorityQueue.isEmpty()) {
            Seek current = priorityQueue.poll();
            // 이전에 더 작은 값으로 해당 도시, 포션을 방문했다면 건너뜀.
            if (minTimes[current.city][current.magicPotion] < current.time)
                continue;

            for (int i = 0; i < n; i++) {
                // 도착 도시가 출발 도시와 같다면 건너뜀
                if (i == current.city)
                    continue;

                // 포션을 마실 때의 시간.
                double time = current.time + adjMatrix[current.city][i] / 2.0;
                // 최소 시간을 갱신하는 경우
                if (current.magicPotion < k && minTimes[i][current.magicPotion + 1] > time) {
                    // 더 많은 포션을 마시는 경우도 당연히 time보다 작거나 같아야하므로
                    // time보다 더 큰 값인 경우는 time으로 채움
                    for (int j = current.magicPotion + 1; j <= k; j++) {
                        if (minTimes[i][j] < time)
                            break;
                        minTimes[i][j] = time;
                        priorityQueue.offer(new Seek(i, current.magicPotion + 1, time));
                    }
                }
                
                // 포션을 마시지 않고 이동하는 경우
                time = current.time + adjMatrix[current.city][i];
                if (minTimes[i][current.magicPotion] > time) {
                    minTimes[i][current.magicPotion] = time;
                    priorityQueue.offer(new Seek(i, current.magicPotion, time));
                }
            }
        }
        
        // 1번 도시에 도달하는 최소 시간을 사용한 포션 개수에 상관없이 구함
        double answer = Double.MAX_VALUE;
        for (int i = 0; i < minTimes[1].length; i++)
            answer = Math.min(answer, minTimes[1][i]);
        // 답 출력
        System.out.println(answer);
    }
}