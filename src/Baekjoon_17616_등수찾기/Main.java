/*
 Author : Ruel
 Problem : Baekjoon 17616번 등수 찾기
 Problem address : https://www.acmicpc.net/problem/17616
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17616_등수찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생이 대회에 참가했다.
        // 등수를 발표하는 대신 두 학생이 찾아가면 두 학생 중 어느 학생이 더 잘했는지를 알려준다.
        // m번의 질문 결과가 있을 때
        // 학생 x의 가능한 등수 범위를 출력하라
        //
        // 그래프 탐색 문제
        // 자신보다 확실히 앞에 있는 사람들의 수와, 확실히 뒤에 있는 사람이 수를 완전탐색으로 센다.
        // 그 후 자신보다 앞에 있는 사람 + 1이 자신이 가능한 최대 성적이고
        // 전체 인원 - 자신보다 뒤에 있는 사람이 자신이 가능한 최소 성적이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 처리
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        // 리스트에는 해당하는 학생보다 확실히 성적이 좋은 사람을 저장한다.
        List<List<Integer>> predecessors = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            predecessors.add(new ArrayList<>());

        // 리스트에 해당하는 학생보다 확실히 성적이 나쁜 학생을 저장한다.
        List<List<Integer>> successors = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            successors.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a > b이므로 해당하는 리스트들에 저장한다.
            predecessors.get(b).add(a);
            successors.get(a).add(b);
        }

        // 자신보다 성적이 좋은 학생이 몇 명인지 센다.
        int predecessorCounter = 0;
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        // 질문을 통해 자신보다 직접적으로 성적이 좋은 학생들 추가.
        Queue<Integer> queue = new LinkedList<>(predecessors.get(x));
        for (int predecessor : predecessors.get(x)) {
            visited[predecessor] = true;
            predecessorCounter++;
        }
        // BFS를 통해 자신보다 성적이 좋은 '학생' 보다 좋은 학생들을 모두 찾는다.
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int predecessor : predecessors.get(current)) {
                if (!visited[predecessor]) {
                    visited[predecessor] = true;
                    queue.offer(predecessor);
                    predecessorCounter++;
                }
            }
        }
        
        // 자신보다 성적이 낮은 학생 카운트
        int successorCounter = 0;
        visited = new boolean[n + 1];
        // 질문을 통해 아는 자신보다 직접적으로 성적이 낮은 학생들
        queue = new LinkedList<>(successors.get(x));
        for (int successor : successors.get(x)) {
            visited[successor] = true;
            successorCounter++;
        }
        // BFS를 통해 자신보다 성적이 낮은 '학생'보다 낮은 학생들을 모두 찾는다.
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int successor : successors.get(current)) {
                if (!visited[successor]) {
                    visited[successor] = true;
                    queue.offer(successor);
                    successorCounter++;
                }
            }
        }

        // 최종적으로 x가 가능한 등수 범위를 출력한다.
        System.out.println((predecessorCounter + 1) + " " + (n - successorCounter));
    }
}