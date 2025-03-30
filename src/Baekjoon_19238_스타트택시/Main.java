/*
 Author : Ruel
 Problem : Baekjoon 19238번 스타트 택시
 Problem address : https://www.acmicpc.net/problem/19238
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19238_스타트택시;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int n;
    static int[][] map;
    static int[][] distances;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 지도가 주어진다. 0은 빈 곳, 1은 막힌 곳이다.
        // 택시의 연료는 f만큼 채워져있다.
        // m명의 손님에 대한 현재 위치와 목적지 위치가 주어진다.
        // 택시는 현재 위치에서 가장 가까운 손님을 태운다. 
        // 그러한 손님이 복수라면 행이 더 적은 손님을, 그러한 손님도 여럿이라면 열이 더 적은 손님을 태운다.
        // 택시는 손님을 목적지에 데려다 줄 경우, 손님이 탑승한 거리 * 2 만큼의 연료를 얻는다.
        // 모든 손님을 수송한 뒤, 남은 연료의 양을 출력하라
        // 모두 태울 수 없다면 -1을 출력한다.
        //
        // BFS 문제
        // BFS를 통해 거리를 많이 계산해야하는 문제
        // 함수를 통해 기준 위치에서의 모든 곳의 거리를 계산하도록 짜두면 편하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 맵
        n = Integer.parseInt(st.nextToken());
        // 손님의 수 m, 초기 연료의 양 f
        int m = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());
        
        // 맵 정보
        map = new int[n][n];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 초기 택시의 위치
        st = new StringTokenizer(br.readLine());
        int taxiR = Integer.parseInt(st.nextToken()) - 1;
        int taxiC = Integer.parseInt(st.nextToken()) - 1;

        // 탑승객 정보
        int[][] passengers = new int[m][4];
        for (int i = 0; i < passengers.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < passengers[i].length; j++)
                passengers[i][j] = Integer.parseInt(st.nextToken()) - 1;
        }
        // 손님들을 미리 열, 행에 따라 정렬시켜놓으면
        // 나중에 어떤 손님을 먼저 태울지에 대해 고민하지 않아도 된다.
        Arrays.sort(passengers, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });

        // 거리를 계산할 공간
        distances = new int[n][n];
        // 손님의 수송 여부
        boolean[] check = new boolean[m];
        // 태운 손님의 수
        int delivered = 0;
        // m명을 모두 태울 때까지 반복
        while (delivered < m) {
            // 현재 위치에서 거리 계산
            distances(taxiR, taxiC);
            // 가장 가까운 손님을 찾는다.
            int next = -1;
            for (int i = 0; i < passengers.length; i++) {
                if (!check[i] && distances[passengers[i][0]][passengers[i][1]] != Integer.MAX_VALUE &&
                        (next == -1 || distances[passengers[next][0]][passengers[next][1]] > distances[passengers[i][0]][passengers[i][1]]))
                    next = i;
            }
            
            // 태울 수 있는 손님이 더 이상 없다면
            // 종료
            if (next == -1)
                break;
            // 손님까지의 거리
            int toPassenger = distances[passengers[next][0]][passengers[next][1]];
            distances(passengers[next][0], passengers[next][1]);
            // 손님부터 목적지까지의 거리
            int toDestination = distances[passengers[next][2]][passengers[next][3]];
            // 연료가 해당 거리를 가기엔 부족하다면 종료
            if (f < toPassenger + toDestination)
                break;

            // 갈 수 있다면
            // 소모되는 연료는 toPassenger + toDestination
            // 충전되는 연료는 toDestination * 2
            f += toDestination - toPassenger;
            // next 손님 체크
            check[next] = true;
            // 수송한 손님의 수 증가
            delivered++;
            // 택시의 위치 변경
            taxiR = passengers[next][2];
            taxiC = passengers[next][3];
        }
        // m명 모두 태운다면 남은 연료 f 출력, 그렇지 못했다면 -1 출력
        System.out.println(delivered == m ? f : -1);
    }
    
    // r, c에서 다른 격자로 이르는 거리를 BFS로 계산
    static void distances(int r, int c) {
        // distances 초기화
        for (int[] d : distances)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 시작 위치
        distances[r][c] = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(r * n + c);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int d = 0; d < 4; d++) {
                int nextR = current / n + dr[d];
                int nextC = current % n + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] == 0 && distances[nextR][nextC] > distances[current / n][current % n] + 1) {
                    distances[nextR][nextC] = distances[current / n][current % n] + 1;
                    queue.offer(nextR * n + nextC);
                }
            }
        }
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}