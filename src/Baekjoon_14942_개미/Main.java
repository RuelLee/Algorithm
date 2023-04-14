/*
 Author : Ruel
 Problem : Baekjoon 14942번 개미
 Problem address : https://www.acmicpc.net/problem/14942
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14942_개미;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Road {
    int end;
    int distance;

    public Road(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 개미집은 n개의 방으로 구성되어있으며, 1 ~ n까지의 번호가 부여되어있다.
        // 1번방은 지면에 연결되어있으며, 각 방들은 서로 연결되어있다.
        // 또한 방을 이동할 때는 거리에 따라 에너지가 소모된다.
        // 각 방에는 개미들이 있고, 지면에 도달하고자 한다.
        // 각 방에 있는 개미의 에너지가 주어질 때, 각 방에 있는 개미가 도달할 수 있는
        // 가장 지면에 가까운 방을 출력하라
        //
        // 희소 배열 문제
        // 1번 방을 루트로 삼고, 트리를 만든 후, 희소배열을 만든다.
        // 희소 배열에 따라 도달할 수 있는 가장 지면에 가까운 방을 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 방의 개수와 각 방에 있는 개미의 에너지
        int n = Integer.parseInt(br.readLine());
        int[] energies = new int[n + 1];
        for (int i = 1; i < energies.length; i++)
            energies[i] = Integer.parseInt(br.readLine());

        // 방들의 연결 정보가 주어진다.
        List<List<Road>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            child.get(a).add(new Road(b, c));
            child.get(b).add(new Road(a, c));
        }

        // 트리의 랭크
        int[] ranks = new int[n + 1];
        ranks[1] = 1;
        // 희소 배열
        // sparseArray[n][a] = n번 방에서 2^a번째 위의 방은 sparseArray[n][a][0]이며,
        // 이 때 소모되는 에너지는 sparseArray[n][a][1]
        int[][][] sparseArray = new int[n + 1][(int) (Math.log(n - 1) / Math.log(2)) + 1][2];
        // 희소 배열을 채운다.
        fillSparseArray(1, new boolean[n + 1], ranks, sparseArray, child);

        // 각 방에서 도달할 수 있는 지면에 가장 가까운 방을 찾고 기록한다.
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n + 1; i++)
            sb.append(findReachableRoom(i, energies, ranks, sparseArray)).append("\n");
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 도달할 수 있는 가장 지면에 가까운 방을 찾는다.
    static int findReachableRoom(int n, int[] energies, int[] ranks, int[][][] sparseArray) {
        // 남은 에너지
        int remainEnergy = energies[n];
        // 방 번호가 1번보다 크며(= 1이 된 시점에서는 에너지가 남아있더라도 종료)
        // 남은 에너지가 최소 바로 위의 방을 갈 수 있을 정도라면
        while (n > 1 && remainEnergy >= sparseArray[n][0][1]) {
            // 랭크에 따라 갈 수 있는 가장 지면에 가까운 방부터
            for (int i = (int) (Math.log(ranks[n] - 1) / Math.log(2)); i >= 0; i--) {
                // 남은 에너지를 비교하며 실제로 갈 수 있는 방을 찾는다.
                if (remainEnergy >= sparseArray[n][i][1]) {
                    remainEnergy -= sparseArray[n][i][1];
                    n = sparseArray[n][i][0];
                    break;
                }
            }
        }
        // 현재 도달한 방의 번호를 출력.
        return n;
    }

    // 희소 배열을 채운다.
    static void fillSparseArray(int n, boolean[] visited, int[] ranks, int[][][] sparseArray, List<List<Road>> child) {
        // 방문 체크
        visited[n] = true;
        // 할당된 랭크에 따라 희소배열을 채운다.
        for (int i = 1; i < Math.log(ranks[n] - 1) / Math.log(2) + 1; i++) {
            // n번 방부터, 2^i번째 위의 방
            sparseArray[n][i][0] = sparseArray[sparseArray[n][i - 1][0]][i - 1][0];
            // 그 방에 도달하는데 필요한 에너지
            sparseArray[n][i][1] = sparseArray[n][i - 1][1] + sparseArray[sparseArray[n][i - 1][0]][i - 1][1];
        }

        // 자식 노드들에게도 재귀적으로 메소드를 호출한다.
        for (Road next : child.get(n)) {
            // 방문하지 않은 연결된 방에 대해서만(=부모 노드로 가지 않는다)
            if (!visited[next.end]) {
                // 랭크 하나 증가
                ranks[next.end] = ranks[n] + 1;
                // next.end 방의 부모 노드로 n 기록
                sparseArray[next.end][0][0] = n;
                // 이 때의 거리
                sparseArray[next.end][0][1] = next.distance;
                // 그리고 재귀적으로 메소드 호출.
                fillSparseArray(next.end, visited, ranks, sparseArray, child);
            }
        }
    }
}