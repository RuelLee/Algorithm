/*
 Author : Ruel
 Problem : Baekjoon 12083번 Bad Horse (Small2)
 Problem address : https://www.acmicpc.net/problem/12083
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12083_BadHorse_Small2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 한 집단에 관계가 좋지 못한 사이가 m개 주어진다.
        // 이들을 서로 다른 집단 2개로 나누려고 한다.
        // 모든 사람들을 두 집단에 포함시킬 수 있다면 Yes, 그렇지 못한다면 No를 출력한다.
        //
        // BFS, 해쉬맵
        // 좋지 못한 사이가 두 개의 이름으로 주어지므로
        // 해쉬맵을 통해 이름을 숫자로 변경한다.
        // 그 후, BFS를 통해 어느 사람과 사이가 다른 사람을 서로 다른 팀에 배정한다.
        // 그러다가, 같은 팀에 배정되는 경우가 발생하면 불가능한 경우이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        
        StringBuilder sb = new StringBuilder();
        // t개의 테스트케이스
        for (int testCase = 0; testCase < t; testCase++) {
            // m개의 관계
            int m = Integer.parseInt(br.readLine());
            // 해쉬맵을 통해 이름을 숫자와 매칭
            HashMap<String, Integer> id = new HashMap<>();
            // 좋지 못한 관계들
            List<List<Integer>> connections = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                String[] names = br.readLine().split(" ");
                for (int j = 0; j < names.length; j++) {
                    // 해당 이름이 처음 들어왔다면 이름을 할당하고, 리스트 추가
                    if (!id.containsKey(names[j])) {
                        id.put(names[j], id.size());
                        connections.add(new ArrayList<>());
                    }
                }
                // 해당하는 두 사람에게 서로의 관계를 추가.
                connections.get(id.get(names[0])).add(id.get(names[1]));
                connections.get(id.get(names[1])).add(id.get(names[0]));
            }

            // 각 사람이 배정된 팀
            int[] teams = new int[id.size()];
            // 초기값 -1
            Arrays.fill(teams, -1);
            boolean result = true;
            for (String name : id.keySet()) {
                // 아직 초기값인 경우
                if (teams[id.get(name)] == -1) {
                    // 해당 사람을 0번 팀에 배정하고
                    teams[id.get(name)] = 0;
                    // 자신과 관계가 좋지 못한 사람을 BFS로 탐색해나간다.
                    // 그러다 관계가 좋지 못한 두 사람이 같은 팀이 배정되는 경우 false가 반환되어온다.
                    // 그런 경우, result를 false로 고쳐주고 반복문 종료
                    if (!divideTeam(id.get(name), teams, connections)) {
                        result = false;
                        break;
                    }
                }
            }
            // result가 true로 남았다면 좋지 못한 관계인 두 사람을 서로 다른 팀에 배정하는 것이 가능한 경우 Yes 기록
            // false라면 No 기록
            sb.append("Case #").append(testCase + 1).append(": ").append(result ? "Yes" : "No").append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
    
    // BFS
    // idx와 관계가 좋지 못한 사람들을 탐색
    static boolean divideTeam(int idx, int[] teams, List<List<Integer>> connections) {
        for (int next : connections.get(idx)) {
            // 만약 next에 이미 팀이 있는데 idx와 같은 팀이라면 다른 팀에 배정하는 것이 불가능한 경우
            // false 반환
            if (teams[next] == teams[idx])
                return false;
            // 아직 팀 배정이 안된 경우
            else if (teams[next] == -1) {
                // next에게는 idx와 다른 팀을 배정한 후
                teams[next] = (teams[idx] + 1) % 2;
                // next에서 다시 탐색 개시
                // 불가능한 경우가 반환된다면 false 반환
                if (!divideTeam(next, teams, connections))
                    return false;
            }
        }
        // 위에서 false가 반환되지 않았다면, 모든 좋지않은 관계인 사람들에게 다른 팀을 배정하는 것이 가능한 경우
        // true 반환
        return true;
    }
}