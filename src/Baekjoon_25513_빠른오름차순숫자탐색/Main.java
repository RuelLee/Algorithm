/*
 Author : Ruel
 Problem : Baekjoon 25513번 빠른 오름차순 숫자 탐색
 Problem address : https://www.acmicpc.net/problem/25513
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25513_빠른오름차순숫자탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 5 * 5 크기의 격자에서 -1 ~ 6까지의 수들이 적혀있다.
        // -1로는 이동할 수 없으며
        // 시작 위치로부터 1 ~ 6까지의 칸을 모두 밟으려고 한다.
        // 숫자칸을 밟는 도중 다른 숫자를 밟아도 된다.
        // 모든 숫자칸을 방문하는데 걸리는 최소 이동 횟수는?
        //
        // BFS 문제
        // BFS를 통해 다음 숫자를 밟는데 필요한 최소 이동 횟수를 각각 계산하여 합한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 격자
        int[][] map = new int[5][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 시작 위치
        int start = Integer.parseInt(st.nextToken()) * 5 + Integer.parseInt(st.nextToken());
        // 이동 횟수 합
        int sum = 0;
        // 1 ~ 6까지의 숫자칸을 순서대로 밟는다.
        for (int i = 1; i <= 6; i++) {
            int[] answer = calcDistance(start, i, map);
            // i번 칸을 밟는 것이 불가능하다면
            if (answer[0] == Integer.MAX_VALUE) {
                // sum에 -1 대입 후, 반복문 종료
                sum = -1;
                break;
            }
            // 그 외의 경우는 이동 횟수 합산
            sum += answer[0];
            // 시작 위치 변경
            start = answer[1];
        }

        // 답 출력
        // 이동이 가능하다면 이동 횟수 최소 합을
        // 불가능하다면 -1을 출력한다.
        System.out.println(sum);
    }

    // startIdx 위치에서 targetNum이 적힌 칸으로 이동하는데 
    // 필요한 최소 이동 횟수와 도착 위치를 반환한다.
    static int[] calcDistance(int startIdx, int targetNum, int[][] map) {
        // 이동 횟수와 도착 위치가 담기는 배열
        int[] answer = new int[2];
        Arrays.fill(answer, Integer.MAX_VALUE);
        
        // 각 칸의 최소 이동 호시수
        int[][] distances = new int[5][5];
        for (int[] d : distances)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 시작점 
        distances[startIdx / 5][startIdx % 5] = 0;
        
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIdx);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / 5;
            int col = current % 5;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                
                // 맵 범위를 벗어나지 않으며
                // -1 칸을 지나지 않고
                // 최소 이동 횟수가 갱신되는 칸일 경우 탐색
                if (checkArea(nextR, nextC) && map[nextR][nextC] != -1
                        && distances[nextR][nextC] > distances[row][col] + 1) {
                    // 거리 갱신
                    distances[nextR][nextC] = distances[row][col] + 1;
                    // 큐 추가
                    queue.offer(nextR * 5 + nextC);
                    // 목적지일 경우
                    // 답안 기록 후 반복문 종료
                    if (map[nextR][nextC] == targetNum) {
                        answer[0] = distances[nextR][nextC];
                        answer[1] = nextR * 5 + nextC;
                        break;
                    }
                }
            }
            // 목적지에 도달한 경우 반복문 종료
            if (answer[0] != Integer.MAX_VALUE)
                break;
        }
        // 결과값 반환.
        return answer;
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < 5 && c >= 0 && c < 5;
    }
}