/*
 Author : Ruel
 Problem : Baekjoon 1506번 경찰서
 Problem address : https://www.acmicpc.net/problem/1506
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1506_경찰서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int[] costs;
    static char[][] adjMatrix;
    static int ancestorCount = 1;
    static int[] nodeNum;
    static boolean[] isSSC;
    static boolean[] visited;
    static int sum = 0;

    public static void main(String[] args) throws IOException {
        // n개의 도시가 주어지고, 각 도시에 경찰서를 설치할 때의 비용이 주어진다
        // 각 경찰서는 출발 도시에 다시 돌아올 수 있는 도시들에 대해 치안력을 발휘할 수 있다고 한다.
        // 전체 도시에 치안력을 미치기 위한 최소 경찰서 설치 비용을 구하라.
        // 오랜만에 풀어본 강한 연결 요소 문제
        // 강한 연결 요소 -> 방향 그래프에서 시작점에서 다시 시작점으로 돌아오는 루트가 존재하는 경우, 그러한 요소들을 묶어 하나의 그룹으로 생각한다.
        // 위의 치안력을 미치는 범위와 같다
        // 따라서 강한 연결 요소를 통해 치안력을 발휘할 수 있는 도시들을 묶어 하나의 그룹으로 생각하고, 이중에서 가장 경찰서 설치 비용이 적은 도시에 경찰서를 설치한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        costs = new int[n];     // 각 도시의 경찰서 설치 비용.
        for (int i = 0; i < costs.length; i++)
            costs[i] = Integer.parseInt(st.nextToken());
        adjMatrix = new char[n][n];     // 도시 간 연결 도로를 표시.
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = br.readLine().toCharArray();
        nodeNum = new int[n];       // tarjan 알고리즘을 위한 각 노드의 등장 순서.
        isSSC = new boolean[n];         // 강한 연결 요소로 그룹에 묶였는지 여부.
        visited = new boolean[n];       // 방문했는지 여부 체크.

        for (int i = 0; i < adjMatrix.length; i++) {
            if (!isSSC[i])      // 강한 연결 요소로 묶이지 않았다면
                tarjan(i, new Stack<>());       // tarjan 알고리즘을 돌린다.
        }
        System.out.println(sum);
    }

    // tarjan 알고리즘은 dfs와 스택을 통해 강한 연결 요소를 판별한다.
    // 각 노드들을 방문할 때 등장 순서를 기록해두고, 스택에 해당 노드를 담고, 연결된 노드들을 방문한다
    // 연결된 노드들을 방문하다가, 이전에 방문한 노드가 등장한다면, 최소한 그 노드부터, 현재 노드까지가 하나의 요소가 될 수 있다.(이후에 방문할 노드가 해당 그룹에 추가될 가능성도 있다.)
    // 따라서 다른 노드들을 탐색하며 이미 그룹으로 묶인 노드를 제외하고, 가장 일찍 등장했던 노드를 기록한다.
    // 그렇게 하면 이 값을 토대로 그룹으로 묶인 인원들을 스택에 남겨둘 수 있다
    // 만약 탐색을 시작한 노드에 minAncestor 값이 초기값보다 적은 값이 나타났다면 자신보다 상위노드까지 연결되는 그룹이 발견된 것이므로, 스택에 남겨둔다.
    // 그렇지 않다면, 자신만 포함되는 그룹이거나, 그룹을 이루는 가장 상위 노드이므로, 스택에서 자신이 나올 때까지가 하나의 그룹으로 묶인 것이다.
    // 위와 같은 계산을 아직 그룹으로 묶이지 않은 노드들에 대해 모두 시도하면 된다.
    static int tarjan(int node, Stack<Integer> stack) {
        int minAncestor = nodeNum[node] = ancestorCount++;      // 현재 노드의 등장 순서를 기록하고, 현재 그룹에 대해 최소 조상의 번호를 기록한다.
        visited[node] = true;       // 방문 체크
        stack.push(node);       // 스택에 현재 노드 푸쉬.
        for (int i = 0; i < adjMatrix[node].length; i++) {
            if (adjMatrix[node][i] == '1' && !isSSC[i]) {       // node와 i 도시 간에 도로가 있고, i가 아직 그룹으로 묶이지 않았다면
                if (visited[i])     // 이미 등장한 적이 있다면, i - > node로의 사이클이 발생했다. 그룹의 한 요소이다.(이후의 탐색을 통해 해당 그룹원이 추가될 수 있다.)
                    minAncestor = Math.min(minAncestor, nodeNum[i]);        // ancestorNum과 i의 nodeNum를 비교해 최소값 기록.
                else        // 그렇지 않다면 tarjan 알고리즘을 통해 계속 탐색한다.
                    minAncestor = Math.min(minAncestor, tarjan(i, stack));      // tarjan 알고리즘을 통해 반환 받은 값과 비교하여 minAncestor 값 갱신.
            }
        }

        // 만약 자신이 연결된 도시들을 살펴보았는데 minAncestor가 자신으로 기록되어있다면
        // 자신만 포함되었건, 다른 도시들이 포함되었건 하나의 그룹이다.
        if (minAncestor == nodeNum[node]) {
            int minCost = costs[node];
            while (stack.peek() != node) {          // 스택에 자신이 나올 때까지
                isSSC[stack.peek()] = true;     // 그룹으로 묶였음을 표시.
                minCost = Math.min(minCost, costs[stack.pop()]);        // 해당 도시의 경찰서 설치 비용 비교.
            }
            // node도 그룹 표시
            isSSC[node] = true;
            // 스택에서 node 제거.
            stack.pop();
            // 경찰서 설치.
            sum += minCost;
        }
        // 현재 갖고 있는 minAncestor 값 반환.
        return minAncestor;
    }
}