/*
 Author : Ruel
 Problem : Baekjoon 21609번 상어 중학교
 Problem address : https://www.acmicpc.net/problem/21609
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12609_상어중학교;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int[][][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어진다.
        // -1은 검은 블록, 0은 무지개 블록 그리고 1 ~ m까지의 색상 블록이 주어진다.
        // 1 ~ m 색상의 블록들은 상하좌우 인접한 같은 색의 블록들로 그룹을 지을 수 있다. 
        // 0의 무지개 블록은 어느 색에도 속할 수 있다. 그룹의 기준은 속한 블록들 중 행이 가장 작으며, 그러한 블록들 중 열이 가장 작은 블록을 기준으로 한다.
        // 그룹은 블록이 2개 이상 속해야한다.
        // 게임을 진행하며, 각 턴마다
        // 1. 가장 많은 블록을 포함하는 그룹을 선택한다. 그러한 그룹이 여러개라면
        // 무지개 블록이 많은 그룹 -> 기준 블록의 행이 가장 큰 그룹 -> 기준 블록의 열이 가장 큰 그룹 순으로 우선권을 갖는다.
        // 해당 블록을 지우며, 그룹에 속한 블록의 개수의 제곱 만큼 점수를 얻는다.
        // 2. 중력이 적용되, -1을 제외한 모든 블록이 아래로 떨어진다.
        // 3. 반시계 방향으로 90도 회전한다.
        // 4. 다시 중력이 적용되어, -1을 제외한 모든 블록이 아래로 떨어진다.
        // 더 이상 삭제되는 그룹이 없을 때까지 진행되며 얻은 점수를 출력한다.
        //
        // BFS, 시뮬레이션 문제
        // 주어진 규칙에 따라 충실히 구현하는 것이 중요한 문제
        // 그룹을 찾을 때, 방문하지 않은 1 ~ m 블록을 기준으로 방문하며, 0번 블록이 인접해있다면 이 역시 포함해야한다.
        // 그러면서, 현재 보드에 모든 그룹을 찾고, 그룹의 크기 순으로 정렬하되, 크기가 같다면 무지개 블록의 수 -> 행의 번호가 큰 순 -> 열의 번호가 큰 순으로
        // 그룹을 선택하는 것을 유의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 보드, 1 ~ m의 색상
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 90도 회전하는 과정에서 많은 수의 자리 변경이 일어난다.
        // 따라서 2개의 보드로 번갈아가며 값을 기록해주자.
        map = new int[2][n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                map[0][i][j] = Integer.parseInt(st.nextToken());
        }

        
        // 방문 체크
        boolean[][] visited = new boolean[n][n];
        // 그룹과 그룹에 속한 멤버들을 나타는 해쉬셋을 해쉬맵으로 관리
        HashMap<Integer, HashSet<Integer>> hashMap = new HashMap<>();
        // 얻은 점수
        int score = 0;
        // BFS로 탐색
        Queue<Integer> queue = new LinkedList<>();
        // 현재 턴
        int turn = 0;
        while (true) {
            // 방문 체크 및 그룹 상황을 초기화
            for (boolean[] v : visited)
                Arrays.fill(v, false);
            hashMap.clear();
            
            // 모든 행과 열을 다니며
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // 미방문이고, 1 ~ m에 속하는 블록들만 계산
                    if (visited[i][j] || map[turn % 2][i][j] <= 0)
                        continue;
                    
                    // 방문 체크
                    visited[i][j] = true;
                    // 큐에 추가하여 BFS 탐색
                    queue.offer(i * n + j);
                    // 속하는 멤버들은 해쉬셋에 관리
                    HashSet<Integer> hashSet = new HashSet<>();
                    hashSet.add(i * n + j);
                    // 무지개 블록의 수
                    int rainbowCnt = 0;
                    // BFS
                    while (!queue.isEmpty()) {
                        int current = queue.poll();
                        int row = current / n;
                        int col = current % n;

                        for (int d = 0; d < 4; d++) {
                            int nextR = row + dr[d];
                            int nextC = col + dc[d];
                            
                            // 보드 범위를 벗어나지 않으며, 멤버에 속하지 않은 칸인 경우
                            if (checkArea(nextR, nextC) && !hashSet.contains(nextR * n + nextC)) {
                                // 무지개 블록인 경우
                                if (map[turn % 2][nextR][nextC] == 0) {
                                    // 멤버 추가 및 큐에 추가하여 탐색
                                    hashSet.add(nextR * n + nextC);
                                    queue.offer(nextR * n + nextC);
                                    // 무지개블록 개수 추가
                                    rainbowCnt++;
                                } else if (map[turn % 2][nextR][nextC] == map[turn % 2][i][j]) {
                                    // 첫 탐색 블록과 같은 블록인 경우
                                    // 멤버 추가 및 큐에 추가
                                    hashSet.add(nextR * n + nextC);
                                    queue.offer(nextR * n + nextC);
                                    // 그리고 방문 처리하여, 다음에 이 칸을 방문하더라도 중복 탐색하지 않도록 한다.
                                    visited[nextR][nextC] = true;
                                }
                            }
                        }
                    }
                    // 블록의 개수가 2개 이상인 경우만 그룹에 추가
                    // 이 때 살짝 트릭으로 idx로 무지개 블럭의 수는 n * n배
                    // 행은 n배 하여 idx를 만들고 추가해준다.
                    // 이렇게 하면, 무지개 블럭이 많은 수록 값이 크고, 같다면, 행이 클수록, 열이 클수록 값이 커진다.
                    if (hashSet.size() > 1)
                        hashMap.put(rainbowCnt * n * n + i * n + j, hashSet);
                }
            }

            int max = -1;
            // 조건에 따라
            // 블록이 가장 많은 그룹 -> 무지개 블록이 가장 많은 그룹 -> 행이 가장 큰 그룹 -> 열이 가장 큰 그룹
            // 순으로 찾는다. 위에 처리한 idx 때문에 멤버의 수가 같다면 크기가 큰 idx를 찾으면 된다.
            for (int key : hashMap.keySet()) {
                if (max == -1 || (hashMap.get(key).size() > hashMap.get(max).size()) || (hashMap.get(key).size() == hashMap.get(max).size() && key > max))
                    max = key;
            }
            
            // 만약 그러한 그룹이 없다면 반복문을 종료
            if (max == -1)
                break;
            
            // 존재한다면 해당 점수 추가
            score += hashMap.get(max).size() * hashMap.get(max).size();
            // 삭제
            for (int idx : hashMap.get(max))
                map[turn % 2][idx / n][idx % n] = -2;
            // 중력
            gravity(turn);
            // 회전
            rotate(turn++);
            // 중력
            gravity(turn);
        }
        // 총 얻은 점수를 출력
        System.out.println(score);
    }
    
    // 해당 위치가 보드 범위 안인지 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
    
    // 반시계 방향으로 90도 회전
    static void rotate(int turn) {
        int from = turn % 2;
        int to = (turn + 1) % 2;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                map[to][i][j] = map[from][j][n - 1 - i];
        }
    }
    
    // 중력 작용으로 1 ~ m까지의 블록을 떨어뜨림
    static void gravity(int turn) {
        int idx = turn % 2;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (map[idx][i][j] != -2 || (i != n - 1 && map[idx][i + 1][j] == -2))
                    continue;

                int r = i - 1;
                while (r >= 0 && map[idx][r][j] == -2)
                    r--;
                if (r >= 0 && map[idx][r][j] > -1) {
                    map[idx][i][j] = map[idx][r][j];
                    map[idx][r][j] = -2;
                }
            }
        }
    }
}