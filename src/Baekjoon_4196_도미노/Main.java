/*
 Author : Ruel
 Problem : Baekjoon 4196번 도미노
 Problem address : https://www.acmicpc.net/problem/4196
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4196_도미노;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static List<List<Integer>> connection;
    static boolean[] visited;
    static int groupCounter;
    static int nodeCounter;
    static int[] nodeNum;
    static int[] groupNum;
    static Stack<Integer> stack;


    public static void main(String[] args) {
        // SCC 강한 연결 요소와 위상 정렬에 관한 복합문제
        // 두 개의 알고리즘이 섞인 문제들이 점점 등장하는 것 같다
        // 도미노의 연결 상태를 알려줬을 때, 모든 블럭을 넘어뜨리기 위해서 손으로 쓰러뜨려야하는 최소 도미노의 개수
        // 강한 연결 요소로 도미노들을 그룹으로 나눈다
        // 나눈 뒤 각 그룹 간의 관계를 위상 정렬로 표시하여 진입차수가 0인 그룹의 개수를 찾으면 된다!
        Scanner sc = new Scanner(System.in);

        int tc = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tc; t++) {

            int n = sc.nextInt();
            int m = sc.nextInt();

            init(n, m, sc);     // 할당, 입력 모두 처리
            for (int i = 1; i < visited.length; i++) {
                if (groupNum[i] == 0)       // 아직 그룹이 할당되지 않은 번호에 대해 findSCC 함수를 부른다!
                    findSCC(i);
            }

            int[] inDegree = new int[groupCounter];     // 진입차수
            for (int i = 1; i < connection.size(); i++) {
                for (int next : connection.get(i)) {
                    if (groupNum[i] != groupNum[next])      // 서로 다른 그룹 간의 연결만 체크
                        inDegree[groupNum[next]]++;
                }
            }

            int answer = 0;
            for (int i = 1; i < inDegree.length; i++) {
                if (inDegree[i] == 0)       // 그룹 중 진입차수가 0인 것만 카운트
                    answer++;
            }
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }

    static int findSCC(int current) {
        visited[current] = true;
        int minAncestor = nodeNum[current] = nodeCounter++;         // 가장 높은 노드를 찾기 위한 값
        stack.push(current);

        for (int next : connection.get(current)) {
            if (groupNum[next] != 0)        // 이미 SCC로 찾아졌다면 패스
                continue;
            else if (visited[next])         // 방문한 적만 있다면(= 조상 노드라면)
                minAncestor = Math.min(minAncestor, nodeNum[next]);     // minAncestor 값만 갱신
            else
                minAncestor = Math.min(minAncestor, findSCC(next));     // 방문하지 않았다면 함수로 값 갱신
        }

        if (minAncestor == nodeNum[current]) {      // 가장 높은 조상이 자신이라면 SCC를 만든다.
            while (stack.peek() != current)
                groupNum[stack.pop()] = groupCounter;
            groupNum[stack.pop()] = groupCounter++;
        }
        return minAncestor;
    }

    static void init(int n, int m, Scanner sc) {
        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());

        for (int i = 0; i < m; i++)
            connection.get(sc.nextInt()).add(sc.nextInt());

        visited = new boolean[n + 1];
        groupCounter = 1;
        nodeCounter = 1;
        nodeNum = new int[n + 1];
        groupNum = new int[n + 1];
        stack = new Stack<>();
    }
}