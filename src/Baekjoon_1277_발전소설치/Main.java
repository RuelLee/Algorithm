/*
 Author : Ruel
 Problem : Baekjoon 1277번 발전소 설치
 Problem address : https://www.acmicpc.net/problem/1277
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1277_발전소설치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Seek {
    int station;
    double distance;

    public Seek(int station, double distance) {
        this.station = station;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 발전소가 주어진다.
        // 1번 발전소로부터 n번 발전소를 잇는 전선이 도중에 끊어졌다.
        // 새로 설치하는 전선의 길이를 최소로 하며 다시 잇고 싶다.
        // 하나의 전선의 최대 길이는 m이하이다.
        // 각 발전소의 (x, y) 좌표가 주어진다.
        // w개의 남아있는 전선이 잇는 두 발전소가 주어진다.
        //
        // dijkstra 문제
        // 각 발전소에서 다른 발전소를 잇는 전선의 길이를 계산하고 이 값이 m을 넘는지 확인한다.
        // 이미 있는 전선을 통하여 갈 경우는 길이가 0이다.
        // 그 후, 1번 발전소부터 다익스트라를 통해 n번 발전소로 잇는 최소 길이를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 발전소, w개의 남아 있는 전선
        int n = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        
        // 전선 하나의 최대 길이 m
        double m = Double.parseDouble(br.readLine());
        
        // 각 발전소의 위치
        int[][] stations = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                stations[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 발전소 사이의 거리
        double[][] distances = new double[n][n];
        for (double[] distance : distances)
            Arrays.fill(distance, Double.MAX_VALUE);
        
        // 새로운 전선으로 발전소들을 이을 때의 거리
        for (int i = 0; i < distances.length; i++) {
            for (int j = i + 1; j < distances.length; j++) {
                double d = calcDistance(stations, i, j);
                distances[i][j] = distances[j][i] = d <= m ? d : Double.MAX_VALUE;
            }
        }

        // 현재 남아있는 전선들.
        // 이를 통할 경우 길이는 0
        for (int i = 0; i < w; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            distances[a][b] = distances[b][a] = 0;
        }
        
        // dijkstra
        // 1번 발전소에서 각 발전소에 이르는 최소 길이
        Double[] minDistances = new Double[n];
        Arrays.fill(minDistances, Double.MAX_VALUE);
        minDistances[0] = (double) 0;
        // 방문 체크
        boolean[] visited = new boolean[n];
        // 거리에 따른 최소값을 우선적으로 계산.
        PriorityQueue<Seek> pq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.distance));
        pq.offer(new Seek(0, 0));
        while (!pq.isEmpty()) {
            Seek current = pq.poll();
            if (visited[current.station])
                continue;
            
            // current -> next로 가는 길이가 최소로 갱신될 때
            // 값 갱신 후, 우선순위큐에 추가
            for (int next = 0; next < distances[current.station].length; next++) {
                if (distances[current.station][next] != Double.MAX_VALUE &&
                        minDistances[next] > minDistances[current.station] + distances[current.station][next]) {
                    minDistances[next] = minDistances[current.station] + distances[current.station][next];
                    pq.offer(new Seek(next, minDistances[next]));
                }
            }
            // 방문 체크
            visited[current.station] = true;
        }
        // 최소 거리를 1000배 한 후, 소수부는 버린 값 출력
        System.out.println((int) (minDistances[n - 1] * 1000));
    }
    
    // start와 end 발전소 사이의 거리 계산
    static double calcDistance(int[][] stations, int start, int end) {
        return Math.sqrt(Math.pow(stations[start][0] - stations[end][0], 2) + Math.pow(stations[start][1] - stations[end][1], 2));
    }
}