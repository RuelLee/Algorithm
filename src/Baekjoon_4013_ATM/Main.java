/*
 Author : Ruel
 Problem : Baekjoon 4013번 ATM
 Problem address : https://www.acmicpc.net/problem/4013
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4013_ATM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connection;
    static boolean[] isSCC;
    static int[] atm;
    static int[] visited;
    static int[] group;
    static int groupCounter = 1;
    static int visitCounter = 1;
    static Stack<Integer> stack;

    public static void main(String[] args) throws IOException {
        // 시간 조건이며 메모리 조건이 어어어엄청 타이트해서 Scanner를 BufferedReader로 바꾸고
        // 중복 연산을 막기 위해 HashSet을 사용해야했다
        // 문제 자체는 SCC와 그로 인해 생기는 트리로 최대값을 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        init(br, st);       // input과 할당 처리
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int[] restaurant = new int[p];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < p; i++)
            restaurant[i] = Integer.parseInt(st.nextToken());

        stack = new Stack<>();
        tarjan(s);          // 타잔 알고리즘으로 SCC를 만들어준다.
        HashSet<Integer> groupRestaurant = new HashSet<>();     // 각 지점이 아닌 그룹 별로 음식점이 존재하는지 나타낸다.
        for (int i = 0; i < p; i++)
            groupRestaurant.add(group[restaurant[i]]);

        List<HashSet<Integer>> groupRoute = new ArrayList<>();      // 그룹에서 -> 그룹으로 가는 루트를 만든다.
        for (int i = 0; i < groupCounter; i++)
            groupRoute.add(new HashSet<>());
        for (int i = 1; i < connection.size(); i++) {
            for (int next : connection.get(i)) {
                if (group[i] != group[next])
                    groupRoute.get(group[i]).add(group[next]);
            }
        }

        int[] groupAtm = new int[groupCounter];     // 그룹 내의 ATM 인출 가능액을 더해 그룹별로 나눠준다.
        for (int i = 1; i < atm.length; i++)
            groupAtm[group[i]] += atm[i];

        Queue<Integer> queue = new LinkedList<>();
        int[] pointMoney = new int[groupCounter];
        pointMoney[group[s]] = groupAtm[group[s]];      // 스타트 지점부터 시작해서
        queue.add(group[s]);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : groupRoute.get(current)) {      // 다음 지점으로 가되
                if (pointMoney[next] < pointMoney[current] + groupAtm[next]) {      // 각 지점에서의 최대보유액이 갱신될 때만 간다.
                    queue.add(next);
                    pointMoney[next] = pointMoney[current] + groupAtm[next];
                }
            }
        }
        int answer = 0;
        for (int loc : groupRestaurant)     // 음식점이 있는 지점들에서의 최대보유액 중 가장 큰 값이 답
            answer = Math.max(answer, pointMoney[loc]);
        System.out.println(answer);
    }

    static int tarjan(int current) {        // 타잔 알고리즘
        visited[current] = visitCounter++;      // 방문순서를 남겨주고,
        stack.add(current);         // 스택에 담기
        int minAncestor = visited[current];     // 현재 자신으로 생기는 서브트리가 방문한 노드 중 가장 낮은 값의 visitCounter를 저장해줄 것이다.
        for (int next : connection.get(current)) {
            if (isSCC[next])        // 이미 SCC를 이룬 노드라면 패스
                continue;
            else if (visited[next] != 0)        // 방문한 적이 있다면
                minAncestor = Math.min(minAncestor, visited[next]);     // 현재 minAncestor 값을 갱신
            else
                minAncestor = Math.min(minAncestor, tarjan(next));      // 없다면 tarjan 함수 자체를 불러서 값을 갱신하자
        }

        if (minAncestor == visited[current]) {      // minAncestor가 자신이라면 SCC 만들어주기
            while (stack.peek() != current) {       // 자신이 나올 때까지
                group[stack.peek()] = groupCounter;     // 스택에 담긴 값들의 그룹을 같게 만들어주고
                isSCC[stack.pop()] = true;          // SCC를 이루었다고 표시
            }
            group[stack.peek()] = groupCounter++;       // 마지막으로 자신까지 위와 같이 처리
            isSCC[stack.pop()] = true;
        }
        return minAncestor;     // current의 서브트리가 방문한 가장 낮은 visitCounter 값을 리턴한다.
    }

    static void init(BufferedReader br, StringTokenizer st) throws IOException {
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());

        isSCC = new boolean[n + 1];
        atm = new int[n + 1];
        visited = new int[n + 1];
        group = new int[n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            connection.get(Integer.parseInt(st.nextToken())).add(Integer.parseInt(st.nextToken()));
        }

        for (int i = 1; i < atm.length; i++)
            atm[i] = Integer.parseInt(br.readLine());
    }
}