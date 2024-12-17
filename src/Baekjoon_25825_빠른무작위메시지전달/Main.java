/*
 Author : Ruel
 Problem : Baekjoon 25825번 빠른 무작위 메시지 전달
 Problem address : https://www.acmicpc.net/problem/25825
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25825_빠른무작위메시지전달;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] adjMatrix;

    public static void main(String[] args) throws IOException {
        // 선생님 1명과 12명의 학생이 주어진다.
        // 각 학생들이 서로 연락하는데 필요한 시간이 주어진다.
        // 선생님은 학생에게 연락하는데 필요한 시간은 0이다.
        // 학생들은 번호순으로 두 명씩 그룹을 이루고 있다.
        // 1번과 2번, 3번과 4번, ... , 11번과 12번은 서로 그룹이다.
        // 선생님은 랜덤한 한 그룹에게 긴급 메시지를 전한다.
        // 그룹에서 메시지를 받은 학생은 제일 먼저, 같은 그룹 학생에게 메시지를 전하고
        // 다시 메시지를 전달 받은 같은 그룹 학생은 메시지를 전달받지 못한 다른 그룹에 메시지를 전한다.
        // 전체 학생들이 메시지를 전달 받는 최소 시간은?
        //
        // 브루트 포스, 백 트래킹 문제
        // 학생의 수가 그리 많지 않으므로 모든 경우에 대해 살펴볼 수 있다.
        // 학생들이 그룹을 이루고 있으므로, 계산도 그룹을 기준으로 하면 편하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 각 학생들이 서로 연락하는데 드는 시간
        adjMatrix = new int[12][12];
        StringTokenizer st;
        for (int i = 0; i < adjMatrix.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < adjMatrix[i].length; j++)
                adjMatrix[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 전체 학생들이 메시지를 받는데 걸리는 최소 시간
        int answer = Integer.MAX_VALUE;
        // 연락을 받은 그룹 표시
        boolean[] visited = new boolean[6];
        // 선생님이 0 ~ 5번 그룹 중 하나를 선택하여 메시지를 보낸다.
        for (int i = 0; i < 6; i++) {
            // i번 그룹에 대해 선생님이 연락하는 경우
            // 연락 받은 그룹 표시 체크
            visited[i] = true;
            // i * 2 + 1번 학생에게 메시지를 보내는 경우.
            // i * 2 + 1번 학생에게 메시지를 보내는 시간 0 
            // i * 2 + 1번 학생이 i * 2 학생에게 보내는 시간 adjMatrix[i * 2 + 1][i * 2]
            // 그룹에서 마지막으로 메시지를 전달 받는 학생은 i * 2번 학생이고, 다른 그룹에게 메시지를 보내는 학생도 i * 2번 학생
            answer = Math.min(answer, calcMinTimeStartFromS(i * 2, 1, visited) + adjMatrix[i * 2 + 1][i * 2]);
            
            // 그룹에서 먼저 메시지를 받는 학생이 i * 2번 학생인 경우
            answer = Math.min(answer, calcMinTimeStartFromS(i * 2 + 1, 1, visited) + adjMatrix[i * 2][i * 2 + 1]);
            // 체크 표시 해제
            visited[i] = false;
        }
        // 답 출력
        System.out.println(answer);
    }
    
    // 백트래킹, 브루트 포스
    static int calcMinTimeStartFromS(int s, int finished, boolean[] visited) {
        // 6개 그룹에 모두 메시지가 전달됐다면 종료.
        if (finished == 6)
            return 0;

        // 현재 상황에서 아직 연락받지 못한 그룹들에 대해
        // 메시지를 전달하는데 걸리는 최소 시간
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < visited.length; i++) {
            // i번 그룹이 아직 연락받지 못했다면
            if (!visited[i]) {
                // 연락 받은 그룹 표시 체크
                visited[i] = true;
                // i * 2 + 1번 학생에게 먼저 연락하는 경우.
                min = Math.min(min, calcMinTimeStartFromS(i * 2, finished + 1, visited) + adjMatrix[s][i * 2 + 1] + adjMatrix[i * 2 + 1][i * 2]);
                // i * 2번 학생에게 먼저 연락하는 경우.
                min = Math.min(min, calcMinTimeStartFromS(i * 2 + 1, finished + 1, visited) + adjMatrix[s][i * 2] + adjMatrix[i * 2][i * 2 + 1]);
                // 체크 표시 해제
                visited[i] = false;
            }
        }
        // 남은 그룹들에 연락을 취하는 최소 시간 반환.
        return min;
    }
}