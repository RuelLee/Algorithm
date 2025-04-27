/*
 Author : Ruel
 Problem : Baekjoon 18188번 다오의 데이트
 Problem address : https://www.acmicpc.net/problem/18188
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18188_다오의데이트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int r;
    int c;
    StringBuilder sb;

    public Seek(int r, int c, StringBuilder sb) {
        this.r = r;
        this.c = c;
        this.sb = sb;
    }
}

public class Main {
    static int h, w;

    public static void main(String[] args) throws IOException {
        // h * w 크기의 맵이 주어진다.
        // D는 다오의 위치, Z는 디지니의 위치, @는 막힌 공간이다.
        // 다오는 최대 n번 움직일 수 있으며
        // 마리드의 방해로 인해, 매 턴 마리드가 지정하는 두 개의 방향 중 하나로 한 칸 움직일 수 있다.
        // n 턴 안에 디지니를 만날 수 있다면 YES와 해당 경로를
        // 그렇지 못한다면 NO를 출력하라
        // 방향은 WASD로 주어진다.
        //
        // 그래프 탐색, BFS 문제
        // 턴을 바꿔가며 BFS 탐색을 하면 되는 문제
        // w와 h, n 모두 그리 크지 않기 때문에, 경로를 기록하며 가져가자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // h * w 크기의 맵
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        char[][] map = new char[h][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 두 개의 큐로 번갈아가면서 BFS 탐색
        // 턴을 구분하기 위함
        Queue<Seek>[] queues = new Queue[2];
        for (int i = 0; i < queues.length; i++)
            queues[i] = new LinkedList<>();

        // 다오의 위치
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'D')
                    queues[0].offer(new Seek(i, j, new StringBuilder()));
            }
        }

        // n번 움직일 수 있다.
        int n = Integer.parseInt(br.readLine());
        // 디지니와 만났는지 여부
        boolean possible = false;
        // 가능한 경우 그 때의 경로
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < n && !possible; i++) {
            // 이번 턴에
            // 움직일 수 있는 두 방향
            char[] direction = br.readLine().replace(" ", "").toCharArray();
            // BFS 탐색
            while (!queues[i % 2].isEmpty() && !possible) {
                Seek current = queues[i % 2].poll();

                for (int j = 0; j < direction.length; j++) {
                    // 다음 row와 col
                    int nextR = current.r;
                    int nextC = current.c;
                    switch (direction[j]) {
                        case 'W' -> nextR = current.r - 1;
                        case 'A' -> nextC = current.c - 1;
                        case 'S' -> nextR = current.r + 1;
                        case 'D' -> nextC = current.c + 1;
                    }
                    
                    // 맵 범위를 벗어나지 않으며, 막힌 공간도 아니라면
                    if (checkArea(nextR, nextC) && map[nextR][nextC] != '@') {
                        // 디지니를 만난 경우
                        if (map[nextR][nextC] == 'Z') {
                            // possible true 체크 후
                            // 해당 경로를 answer에 기록 후 반복문 종료
                            possible = true;
                            answer.append(current.sb).append(direction[j]);
                            break;
                        }
                        // 그 외의 경우에는 탐색을 이어가야하므로
                        // 다음 큐에 해당 정보를 추가
                        queues[(i + 1) % 2].offer(new Seek(nextR, nextC, new StringBuilder(current.sb).append(direction[j])));
                    }
                }
            }
        }
        
        // 가능한 경우
        if (possible) {
            System.out.println("YES");
            System.out.println(answer);
        } else      // 불가능한 경우
            System.out.println("NO");
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}