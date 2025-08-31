/*
 Author : Ruel
 Problem : Baekjoon 2191번 들쥐의 탈출
 Problem address : https://www.acmicpc.net/problem/2191
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2191_들쥐의탈출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static double[][] mice, holes;
    static List<List<Integer>> connections;

    // n마리의 들쥐와, m개의 땅굴이 주어진다.
    // 각 쥐들의 위치와 땅굴의 위치가 2차원 좌표로 주어진다.
    // 땅굴 하나에는 하나의 쥐만 숨을 수 있다.
    // 하늘에는 매가 있고, s초 뒤에 지면에 도달한다.
    // 쥐들은 v의 속도로 이동한다.
    // 잡아먹히는 쥐의 최소수를 구하라
    //
    // 이분 매칭
    // 들쥐들이 갈 수 있는 땅굴이 거리와 속도, 매의 지면 도달 시간에 따라 제한적이다.
    // 따라서 들쥐와 땅굴로 이분그래프를 만들어, 이분 매칭을 하여
    // 잡아먹히는 들쥐의 최소 수를 구하라
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 들쥐 n마리, 땅굴 m개, 매의 지면 도달 시간 s, 쥐의 속도 v
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        
        // 쥐의 위치
        mice = new double[n][2];
        for (int i = 0; i < mice.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < mice[i].length; j++)
                mice[i][j] = Double.parseDouble(st.nextToken());
        }
        
        // 땅굴의 위치
        holes = new double[m][2];
        for (int i = 0; i < holes.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < holes[i].length; j++)
                holes[i][j] = Double.parseDouble(st.nextToken());
        }
        
        // 쥐가 도달할 수 있는 땅굴을 구한다.
        connections = new ArrayList<>();
        for (int i = 0; i < n; i++)
            connections.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 매가 도달 전에 들쥐가 해당 땅굴에 도달할 수 있다면
                if (calcDistance(i, j) <= s * v)
                    connections.get(i).add(j);
            }
        }

        boolean[] visited = new boolean[n];
        int[] matched = new int[m];
        Arrays.fill(matched, -1);
        // 숨지 못한 쥐의 수
        int cnt = n;
        for (int i = 0; i < n; i++) {
            Arrays.fill(visited, false);
            // 땅굴과 매칭이 됐다면 숨은 것. cnt에서 1 차감
            if (bipartiteMatching(i, visited, matched))
                cnt--;
        }
        System.out.println(cnt);
    }
    
    // 이분 매칭
    static boolean bipartiteMatching(int idx, boolean[] visited, int[] matched) {
        visited[idx] = true;
        for (int hole : connections.get(idx)) {
            // hole 땅굴에 아직 쥐가 숨지 않았거나
            // 숨은 쥐를 다른 땅굴로 이동시킬 수 있다면
            if (matched[hole] == -1 ||
                    (!visited[matched[hole]] && bipartiteMatching(matched[hole], visited, matched))) {
                // 해당 땅굴에 idx 쥐가 숨는다.
                matched[hole] = idx;
                return true;
            }
        }
        // 땅굴을 숨지 못한 경우 false 반환
        return false;
    }

    // 들쥐와 땅굴 사이의 거리를 구한다.
    static double calcDistance(int mouseIdx, int holeIdx) {
        return Math.sqrt(Math.pow(mice[mouseIdx][0] - holes[holeIdx][0], 2)
                + Math.pow(mice[mouseIdx][1] - holes[holeIdx][1], 2));
    }
}