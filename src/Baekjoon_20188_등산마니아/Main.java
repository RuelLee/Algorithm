/*
 Author : Ruel
 Problem : Baekjoon 20188번 등산 마니아
 Problem address : https://www.acmicpc.net/problem/20188
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20188_등산마니아;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] subTreeSize;

    public static void main(String[] args) throws IOException {
        // n개의 오두막과 n - 1개의 오솔길이 이어진 트리 형태가 등산로가 주어진다.
        // 정상에는 1번 오두막이 있다.
        // 각 오두막에서 다른 오두막으로 이동할 때는 반드시 정상을 들린 후 지나간다고 한다.
        // 모든 i에서 j(i < j) 오두막으로 이동할 때, 이용하는 서로 다른 오솔길의 개수의 합을 구하고 싶다.
        //
        // 트리 DP 문제
        // 문제 해결 방법을 떠올리는데 시간이 좀 걸리지, 코딩 자체는 간단했다.
        // 자신과 부모를 연결하는 간선이 전체 총 몇 번 이용하는지에 대해 계산한다.
        // 1. 자신이 포함된 서브 트리 내에서 두 오두막을 이동한다면, 반드시 정상을 찍고 와야하기 때문에 이용된다.
        // 2. 자신의 서브 트리 내 < - > 밖인 경우도 마찬가지로 정상을 찍고 와야하므로 이용한다.
        // 3. 자신의 서브 트리 밖에서의 두 오두막인 경우에만 자신과 부모 노드를 연결하는 간선을 이용하지 않는다.
        // 따라서 자신을 루트로 갖는 서브 트리에서의 전체 노드의 수를 계산해준다.
        // 그 후, 전체에서 두 오두막을 선택하는 경우 - 자신을 루트로 갖는 서브 트리 밖에서 두 오두막을 선택하는 경우를 빼주면
        // 자신과 부모 노드를 연결하는 간선을 이용하는 총 횟수가 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 오두막
        int n = Integer.parseInt(br.readLine());
        // n - 1개의 간선 처리
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            child.get(a).add(b);
            child.get(b).add(a);
        }

        // 각 노드를 루트로 갖는 서브 트리에서의 노드 개수
        subTreeSize = new int[n + 1];
        calcSubTreeSize(1, new boolean[n + 1], child);

        long answer = 0;
        // 1번을 제외한 모든 노드에서 자신과 부모 노드를 잇는 간선을 이용하는 총 횟수를 구해 더한다.
        // 전체 오두막들에서 두 오두막을 선택하는 경우 - 자신을 루트로 갖는 서브 트리 밖에서 두 오두막을 선택하는 경우.
        for (int i = 2; i < subTreeSize.length; i++)
            answer += (long) n * (n - 1) / 2 - (long) (n - subTreeSize[i]) * (n - subTreeSize[i] - 1) / 2;

        // 답안 출력
        System.out.println(answer);
    }

    // 자신을 루트로 갖는 서브 트리에서 노드의 개수를 구한다.
    static int calcSubTreeSize(int n, boolean[] visited, List<List<Integer>> child) {
        // 방문 체크
        visited[n] = true;

        // 먼저 자신을 포함하므로 초기값 1
        subTreeSize[n] = 1;
        for (int next : child.get(n)) {
            // 미방문 노드(= 자식 노드)를 루트로 갖는 서브 트리에서의 노드 개수를 반환 받아
            // 모두 더해주면 그 개수가 자신을 루트로 갖는 서브 트리에서 총 노드의 개수
            if (!visited[next])
                subTreeSize[n] += calcSubTreeSize(next, visited, child);
        }
        // n을 루트로 갖는 서브 트리에서의 총 노드 개수 반환.
        return subTreeSize[n];
    }
}