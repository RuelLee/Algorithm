/*
 Author : Ruel
 Problem : Baekjoon 31477번 양갈래 구하기
 Problem address : https://www.acmicpc.net/problem/31477
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31477_양갈래구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Vine {
    int end;
    int thickness;

    public Vine(int end, int thickness) {
        this.end = end;
        this.thickness = thickness;
    }
}

public class Main {
    static List<List<Vine>> vines;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        // n개의 방이 존재하는 마을에 독이 퍼지고 있다.
        // 독은 하나의 덩굴이 연결된 방에서부터 시작하여 양갈래가 있는 방까지 이동한다.
        // 이에 덩굴을 끊어 양갈래에게 독에 닿지 않게 하고자 한다.
        // 모든 방의 쌍 사이에는 1개 이상의 덩굴을 지나는 경로는 유일하며, 덩굴은 총 n-1개 존재한다.
        // 덩굴들은 각 두께가 다르며, 두께만큼씩 힘이 든다.
        // 양갈래를 구하기 위한 최소 힘 소모량은?
        //
        // 트리, DFS, 트리에서의 DP 문제
        // 모든 방 쌍 사이에는 유일한 경로가 존재하며, 총 n-1개의 덩굴이 주어진다 -> 트리 형태
        // '독은 하나의 덩굴이 연결된 방에서 시작' -> 단말 노드에서 시작
        // 양갈래가 존재하는 노드 -> 루트 노드
        // 단말 노드에서 루트 노드로 가는 경로 내에서
        // 자식 노드들과의 덩굴을 모두 끊는 경우 or 부모 노드와의 덩굴을 끊는 경우
        // 두 경우의 힘 차이를 비교하여 더 적은 힘을 소모하는 경우를 택하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 방
        int n = Integer.parseInt(br.readLine());

        // 덩굴들
        vines = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            vines.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            vines.get(a).add(new Vine(b, v));
            vines.get(b).add(new Vine(a, v));
        }

        // 트리에서의 DP
        dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        System.out.println(fillDp(1, new boolean[n + 1]));
    }

    // BFS, 트리에서의 DP
    static int fillDp(int idx, boolean[] visited) {
        // 방문 체크
        visited[idx] = true;

        // 자식 노드들을 끊는 힘 총합을 구한다.
        int sum = 0;
        for (Vine next : vines.get(idx)) {
            if (visited[next.end])
                continue;
            
            // idx와 next.end를 끊는데 드는 힘
            dp[next.end] = next.thickness;
            // sum에는 idx와 next.end를 끊거나
            // next.end가 자식 노드들을 모두 끊는데 드는 힘 중 더 적은 힘이 결과값으로 온다.
            sum += fillDp(next.end, visited);
        }

        // idx 노드의 부모 노드와 연결을 끊는데 드는 힘 dp[idx]와
        // idx 노드와 자식 노드들의 연결 끊는데 드는 힘의 합 sum 중 더 적은 값을 가져간다.
        // sum이 만약 0이라면 idx 노드가 단말 노드이므로 sum 값은 버린다.
        return dp[idx] = Math.min(dp[idx], sum == 0 ? Integer.MAX_VALUE : sum);
    }
}