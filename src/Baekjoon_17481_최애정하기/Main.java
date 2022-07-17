/*
 Author : Ruel
 Problem : Baekjoon 17481번 최애 정하기
 Problem address : https://www.acmicpc.net/problem/17481
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17481_최애정하기;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;
    static int[] members;

    public static void main(String[] args) throws IOException {
        // n명의 친구와 m명의 걸그룹 멤버, 그리고 각 친구들이 최애로 선택할 수 있는 멤버들이 주어진다.
        // 친구들은 서로 다른 최애 멤버를 정하기로 하였다.
        // 친구들이 모두 서로 다른 최애를 정할 수 있는지, 그렇지 않다면 최대한 겹치지 않게 정한 친구들의 수는 몇 명인지 출력하라.
        //
        // 이분매칭 문제!
        // 걸 그룹 멤버들이 이름으로 주어지기 때문에 해쉬맵을 통해 int 값으로 연결해주자.
        // 그리고 각 친구들이 선택할 수 있는 멤버들을 토대로 이분매칭을 진행한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 친구의 수
        int n = Integer.parseInt(st.nextToken());
        // 걸그룹 멤버의 수
        int m = Integer.parseInt(st.nextToken());

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < m; i++)
            hashMap.put(br.readLine(), i);

        // 각 친구들과 최애 선택 가능 걸그룹 멤버들을 토대로 이분 그래프를 작성.
        connections = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            connections.add(new ArrayList<>(num));
            for (int j = 0; j < num; j++)
                connections.get(i).add(hashMap.get(st.nextToken()));
        }

        // 걸그룹 멤버 한명을 최애로 선택한 친구를 값으로 저장해둔다.
        members = new int[m];
        // 초기화
        Arrays.fill(members, -1);

        // 최애를 선택한 친구의 수를 센다.
        int count = 0;
        for (int i = 0; i < n; i++) {
            // i번 친구가 최애 선택에 성공했다면 count 증가.
            if (bipartiteMatching(i, new boolean[n]))
                count++;
        }
        StringBuilder sb = new StringBuilder();
        // 모든 친구가 선택을 마쳤다면 YES, 그렇지 않다면 NO 
        sb.append(count == n ? "YES" : "NO");
        // 모든 친구가 선택을 못했다면, 선택을 한 최대 친구의 수
        if (count != n)
            sb.append("\n").append(count);
        System.out.println(sb);
    }

    // 이분 매칭
    static boolean bipartiteMatching(int friend, boolean[] visited) {
        // 방문 체크.
        visited[friend] = true;

        // friend가 최애로 선택할 수 있는 모든 걸그룹 멤버를 살펴본다.
        for (int girl : connections.get(friend)) {
            // 아직 girl을 최애로 선택한 친구가 없거나,
            // girl을 최애로 선택한 친구가 다른 걸그룹 멤버를 최애로 선택할 수 있다면
            if (members[girl] == -1 ||
                    (!visited[members[girl]]) && bipartiteMatching(members[girl], visited)) {
                // friend는 girl를 최애로 선택하고 true 리턴.
                members[girl] = friend;
                return true;
            }
        }
        // 선택을 하지 못했으므로 false 리턴.
        return false;
    }
}