/*
 Author : Ruel
 Problem : Baekjoon 9328번 열쇠
 Problem address : https://www.acmicpc.net/problem/9328
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9328_열쇠;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 2차원 지도가 주어진다.
        // .은 빈 공간, '*'은 벽, '$'은 기밀문서
        // 알파벳 대문자는 문, 알파벳 소문자는 열쇠
        // 소문자의 열쇠를 갖고서 대문자의 문을 열 수있으며,
        // 최대한 많은 기밀문서를 훔치고자 한다.
        // 훔칠 수 있는 기밀 문서의 수는?
        //
        // BFS 문제
        // BFS 탐색을 돌리되, 새로운 열쇠를 획득한 경우에는 한 번 더 탐색을 반복한다.(새로운 경로를 찾을 수 있으므로)
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트 케이스
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 주어지는 높이와 너비
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            // 지도 초기화
            // 가장자리에 빈 공간을 하나 더 만듦으로써
            // (0, 0)에서 시작하여, 외부에서 내부로 들어가는 경로가 있을 경우에 찾아서 들어가도록 한다.
            char[][] map = new char[h + 2][w + 2];
            for (int i = 0; i < map.length; i++)
                map[i][0] = map[i][w + 1] = '.';
            for (int i = 0; i < map[0].length; i++)
                map[0][i] = map[h + 1][i] = '.';
            for (int i = 1; i < map.length - 1; i++) {
                String row = br.readLine();
                for (int j = 1; j < map[i].length - 1; j++)
                    map[i][j] = row.charAt(j - 1);
            }

            // 갖고 있는 열쇠의 상태를 비트마스킹으로 표현한다.
            int keys = 0;
            String key = br.readLine();
            for (int i = 0; i < key.length(); i++)
                keys |= 1 << key.charAt(i) - 'a';
            
            // 새로운 키를 얻었는가
            boolean getNewKey = true;
            // 훔친 기밀 문서의 수
            int count = 0;
            // 새로운 키를 얻은 경우 반복
            while (getNewKey) {
                getNewKey = false;
                
                // BFS 탐색
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(0);
                boolean[][] visited = new boolean[h + 2][w + 2];
                visited[0][0] = true;
                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    int row = current / (w + 2);
                    int col = current % (w + 2);
                    
                    // 사방탐색
                    for (int d = 0; d < 4; d++) {
                        int nextR = row + dr[d];
                        int nextC = col + dc[d];
                        
                        // 다음 위치가 벽이 아닐 때
                        if (checkArea(nextR, nextC, map) && !visited[nextR][nextC] && map[nextR][nextC] != '*') {
                            // 열쇠인 경우
                            if (map[nextR][nextC] >= 'a' && map[nextR][nextC] <= 'z') {
                                // 비트마스킹에 해당 열쇠 추가
                                keys |= (1 << (map[nextR][nextC] - 'a'));
                                // 지도에서 열쇠 제거
                                map[nextR][nextC] = '.';
                                // 새로운 키를 얻었다고 표시.
                                getNewKey = true;
                            } else if (map[nextR][nextC] == '$') {      // 기밀 문서인 경우
                                // 카운터 증가
                                count++;
                                // 지도에서 기밀 문서 지우기.
                                map[nextR][nextC] = '.';
                            } else if (map[nextR][nextC] != '.' && !(map[nextR][nextC] >= 'A' && map[nextR][nextC] <= 'Z' && (keys & (1 << (map[nextR][nextC] - 'A'))) != 0)) {
                                // 빈 공간도 아니고 열린 문도 아닐 경우 건너뛴다.
                                // 아래 방문 표시와 큐 추가를 하지 않기 위해서.
                                continue;
                            }
                            
                            // 다음 위치에 방문 표시
                            visited[nextR][nextC] = true;
                            // 큐에 추가.
                            queue.offer(nextR * (w + 2) + nextC);
                        }
                    }
                }
            }
            // 전체 얻은 기밀 문서의 수 기록
            sb.append(count).append("\n");
        }
        // 답안 출력.
        System.out.print(sb);
    }

    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}