/*
 Author : Ruel
 Problem : Baekjoon 3977번 축구 전술
 Problem address : https://www.acmicpc.net/problem/3977
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 축구전술;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static int[] groups;
    static int groupCounter;
    static int nodeCounter;
    static boolean[] isSCC;
    static boolean[] visited;
    static int[] nodeNum;
    static List<List<Integer>> route;
    static Stack<Integer> stack;

    public static void main(String[] args) {
        // SCC와 위상정렬이 살짝 섞인 문제
        // 각 지점을 SCC으로 묶어 그룹으로 나타내고
        // 위상정렬을 하여 진입차수가 0인 곳이 한 곳인 경우, 그 지점으로 부터 모든 지점에 방문할 수 있다
        // 진입차수가 0인 곳이 여러곳이라면 모든 지점을 방문할 수 없다.
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tc; t++) {
            init(sc);
            for (int i = 0; i < isSCC.length; i++) {
                if (!isSCC[i])
                    findSCC(i);
            }

            int[] inDegree = new int[groupCounter];
            for (int i = 0; i < route.size(); i++) {
                for (int next : route.get(i)) {
                    if (groups[i] != groups[next])      // 다른 그룹에서 진입할 때만 증가
                        inDegree[groups[next]]++;
                }
            }

            int count = 0;
            int idx = -1;
            for (int i = 0; i < inDegree.length; i++) {
                if (inDegree[i] == 0) {
                    count++;
                    idx = i;
                }
            }

            if (count == 1) {       // 0인 곳이 하나일 때
                for (int i = 0; i < groups.length; i++) {
                    if (groups[i] == idx)       // 그 그룹원들을 추가
                        sb.append(i).append("\n");
                }
                sb.append("\n");
            } else      // 아니라면 Confused
                sb.append("Confused").append("\n").append("\n");
        }
        System.out.println(sb);
    }

    static int findSCC(int current) {       // SCC 찾는 함수
        visited[current] = true;
        int minAncestor = nodeNum[current] = nodeCounter++;
        stack.push(current);

        for (int next : route.get(current)) {
            if (isSCC[next])
                continue;
            else if (visited[next])
                minAncestor = Math.min(minAncestor, nodeNum[next]);
            else
                minAncestor = Math.min(minAncestor, findSCC(next));
        }

        if (minAncestor == nodeNum[current]) {
            while (stack.peek() != current) {
                isSCC[stack.peek()] = true;
                groups[stack.pop()] = groupCounter;
            }
            isSCC[stack.peek()] = true;
            groups[stack.pop()] = groupCounter++;
        }
        return minAncestor;
    }

    static void init(Scanner sc) {      // 할당과 입력 처리
        int n = sc.nextInt();
        int m = sc.nextInt();
        groupCounter = 0;
        nodeCounter = 1;
        isSCC = new boolean[n];
        visited = new boolean[n];
        groups = new int[n];
        stack = new Stack<>();
        nodeNum = new int[n];

        route = new ArrayList<>();
        for (int i = 0; i < n; i++)
            route.add(new ArrayList<>());

        for (int i = 0; i < m; i++)
            route.get(sc.nextInt()).add(sc.nextInt());
    }
}