/*
 Author : Ruel
 Problem : Baekjoon 2207번 가위바위보
 Problem address : https://www.acmicpc.net/problem/2207
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2207_가위바위보;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<HashSet<Integer>> connections;
    static List<List<Integer>> group;
    static Stack<Integer> stack;
    static boolean[] isSSC;
    static int[] nodeNum;
    static int nodeCounter = 1;

    public static void main(String[] args) throws IOException {
        // n명의 학생들과 원장선생님이 m번 가위바위보를 한다
        // 원장 선생님은 가위나 보를 낸다.
        // 각 학생들은 m번의 가위바위보 중 2개를 골라 원장선생님이 무엇을 낼 지 맞춘다. 둘 중 하나라도 맞추면 성공.
        // 모든 학생들이 성공하는 경우가 존재하는지 알아내시오.
        //
        // 2개를 골라 하나라도 성공하면 되는지 확인하면 되므로 2-SAT / 강한 연결 요소 문제이다
        // 학생들이 선택하는 2개를 합집합, 그리고 각 학생들의 선택들을 교집합으로 묶어 생각하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 총 m번을 진행하고, 각 가위와 바위 두 종류가 있으므로 전체 가지수는 2 * m 개이다.
        connections = new ArrayList<>(2 * m + 1);
        for (int i = 0; i < 2 * m + 1; i++)
            connections.add(new HashSet<>());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 학생들의 선택이 음수라면 해당 숫자번째에 바위를 낸다,
            // 양수라면 해당 숫자번째에 가위를 낸다고 추측한 것이다.
            // 따라서 0이상의 수로 바꿔주기 위해 m을 더해주자.
            int a = Integer.parseInt(st.nextToken()) + m;
            int b = Integer.parseInt(st.nextToken()) + m;

            // 만약 a조건이 틀렸다면 b가 성립해야하고
            // b조건이 틀렸다면 a 조건이 성립해야만 한다.
            connections.get(a < m ? a + (m - a) * 2 : a - (a - m) * 2).add(b);
            connections.get(b < m ? b + (m - b) * 2 : b - (b - m) * 2).add(a);
        }

        // 구해진 조건을 토대로 tarjan 알고리즘을 통해 강한 연결 요소들을 찾자.
        stack = new Stack<>();
        nodeNum = new int[2 * m + 1];
        isSSC = new boolean[2 * m + 1];
        group = new ArrayList<>();

        for (int i = 0; i < connections.size(); i++) {
            if (!isSSC[i] && !connections.get(i).isEmpty())
                tarjan(i);
        }
        System.out.println(isPossible(m) ? "^_^" : "OTL");
    }

    static boolean isPossible(int m) {      // SSC를 가지고서 학생들이 모두 이기는 경우가 있는지 살펴본다.
        // 각 그룹에서
        for (List<Integer> list : group) {
            for (int mem : list) {
                // 서로 상반되는 조건이 한 그룹에 있다면 성립이 불가능.
                if (list.contains(mem < m ? mem + (m - mem) * 2 : mem - (mem - m) * 2))
                    return false;
            }
        }
        // 그렇지 않다면 가능.
        return true;
    }

    static int tarjan(int n) {
        // 아직 거쳐오지 않은 연결들 중 상위 노드가 있는지 확인하기 위해 n 노드에 nodeCounter 값을 주자.
        int minAncestor = nodeNum[n] = nodeCounter++;
        stack.push(n);

        for (int next : connections.get(n)) {
            // 이미 SSC가 성립되어있다면 건너뛰기.
            if (isSSC[next])
                continue;
            // 그렇지 않고 상위 노드가 아직 거치지 않은 연결로 발견이 된다면, 해당 노드의 번호를 가져온다.
            else if (nodeNum[next] != 0)
                minAncestor = Math.min(minAncestor, nodeNum[next]);
            // 아직 SSC가 성립되지도, 방문하지도 않은 노드라면 tarjan 메소드를 재귀적으로 불러주자.
            else
                minAncestor = Math.min(minAncestor, tarjan(next));
        }

        // 자신과 연결된 모든 노드들을 방문하는 도중 상위노드를 발견하지 못했다면(= 자신이 가장 상위 노드라면)
        // 그룹을 형성해준다.
        if (minAncestor == nodeNum[n]) {
            List<Integer> list = new ArrayList<>();
            // 스택에서 자신이 나오지 않는 동안.
            while (stack.peek() != n) {
                // SSC 성립 체크
                isSSC[stack.peek()] = true;
                // 리스트 추가.
                list.add(stack.pop());
            }
            // 마지막으로 자신도 SSC성립 체크 및 리스트 추가
            isSSC[stack.peek()] = true;
            list.add(stack.pop());
            // 그룹 리스트에 현재 SSC 리스트 추가.
            group.add(list);
        }
        // 가장 방문한 가장 상위 노드 번호 리턴.
        return minAncestor;
    }
}