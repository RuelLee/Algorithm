/*
 Author : Ruel
 Problem : Baekjoon 1846번 장기
 Problem address : https://www.acmicpc.net/problem/1846
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1846_장기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n크기의 격자가 주어진다.
        // 그 위에 장기말 중 하나인 차를 놓는다.
        // 차는 자기 위치로부터 가로나 세로 위치로 공격할 수 있다.
        // 놓인 차들이 다른 말을 공격할 수 없어야한다.
        // 그리고 각 꼭지점에서 시직하는 대각선의 위치에는 말을 놓을 수는 없다
        // 예를 들어 n이 4인 경우
        // ■□□■
        // □■■□
        // □■■□
        // ■□□■ 검정색인 위치는 말을 놓을 수 없다.
        // 차를 놓을 수 있는 위치를 출력하라
        // 불가능하다면 -1을 출력한다.
        //
        // 이분 매칭
        // 을 사용하지 않고, 직접 해봄으로써 규칙을 찾아낼 수 있지만
        // 이분 매칭을 통해 풀어보았다.
        // 대각선의 위치는 행과 열의 값이 일치하는 경우
        // 혹은 행과 열의 값이 크기 +1인 경우이다.
        // 따라서 해당 위치에는 말을 놓지 않으며 중복되지 않게,
        // 각 행과 열에 하나씩 차를 놓으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n크기의 격자
        int n = Integer.parseInt(br.readLine());
        
        // 해당 행에 매칭된 열의 번호
        int[] matched = new int[n + 1];
        // n개의 차를 놓는 것이 가능한가.
        boolean possible = true;
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        for (int i = 1; i < matched.length; i++) {
            // 방문 배열 초기화
            Arrays.fill(visited, false);
            // i번째 열 중에서 차를 놓을 수 있는 행을 찾는다.
            // 불가능하다면 반복문 종료.
            if (!bipartiteMatching(i, matched, visited, n)) {
                possible = false;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        // 불가능 했다면 -1 출력
        if (!possible)
            sb.append(-1).append("\n");
        else {      // 가능했다면
            // 매칭된 행을 순서대로 기록
            for (int i = 1; i < matched.length; i++)
                sb.append(matched[i]).append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
    
    // 이분 매칭
    static boolean bipartiteMatching(int idx, int[] matched, boolean[] visited, int size) {
        // idx 열에서 차를 놓을 수 있는 행을 찾는다.
        // 방문 체크
        visited[idx] = true;

        // i번 행이 비어있는지 확인.
        for (int i = 1; i < matched.length; i++) {
            // 대각선은 건너뜀
            if (idx == i || idx + i == size + 1)
                continue;

            // 비어있다면 i번째 행에 차를 둔다.
            if (matched[i] == 0) {
                matched[i] = idx;
                return true;
            }
        }

        // 비어있는 행이 없는 경우.
        // idx열에서 놓을 수 있는 행 중에서
        // 놓여있는 차를 다른 위치로 옮기는 것이 가능한지 살펴본다.
        for (int i = 1; i < matched.length; i++) {
            // 대각선 건너뜀.
            if (idx == i || idx + i == size + 1)
                continue;

            // matched[i] 열에 방문한 적이 없고
            // i행에 있는 차를 다른 위치로 옮기는 것이 가능하다면
            if (!visited[matched[i]] && bipartiteMatching(matched[i], matched, visited, size)) {
                // i행 idx열에 차를 둔다.
                matched[i] = idx;
                return true;
            }
        }
        // 다른 열에 있는 차를 옮겨도, idx열에 차를 두는 것이 불가능하다면
        // false 반환
        return false;
    }
}