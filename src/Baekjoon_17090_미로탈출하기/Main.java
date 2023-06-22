/*
 Author : Ruel
 Problem : Baekjoon 17090번 미로 탈출하기
 Problem address : https://www.acmicpc.net/problem/17090
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17090_미로탈출하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static char[] order = {'U', 'R', 'D', 'L'};
    static HashMap<Character, Integer> hashMap;
    static char[][] map;
    static boolean[][] visited, escapable;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 미로가 주어진다.
        // 각 칸에는 U, R, D, L 중 하나의 문자가 적혀있고
        // U : 위쪽, R : 오른쪽, D : 아랫쪽, L : 왼쪽
        // 으로 한 칸씩만 이동할 수 있다고 한다.
        // 맵 밖으로 나가는 경우를 미로에서 탈출한다할 때
        // 미로에서 탈출할 수 있는 칸은 총 몇 칸인가?
        //
        // DP, DFS 문제
        // 각 칸에서 다음으로 이동할 수 있는 칸이 정해져있으므로
        // 이미 결과값이 계산된 칸에 도착했다면 항상 해당 결과값만 나온다.
        // 각 칸에서 방문 여부와 탈출 여부를 기록해두고
        // 이미 방문했다면 더 이상 계산하지 않고 기록된 탈출 여부 값을 읽는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 미로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 알파벳과 방향을 해쉬맵을 통해 매칭시켜준다.
        hashMap = new HashMap<>();
        for (int i = 0; i < order.length; i++)
            hashMap.put(order[i], i);
        
        // 미로
        map = new char[n][m];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 방문 여부
        visited = new boolean[n][m];
        // 해당 칸에서 탈출이 가능한지 여부
        escapable = new boolean[n][m];

        // 탈출 가능 칸을 센다.
        int count = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // i, j에서 시작해서 탈출이 가능하다면 count를 증가시킨다.
                if (dfs(i, j))
                    count++;
            }
        }
        // 답안 출력
        System.out.println(count);
    }

    // DFS를 통해 현재 칸에서 탈출 가능한지 계산한다.
    static boolean dfs(int row, int col) {
        // 만약 현재 칸이 미로 밖으로 나왔다면
        // true을 반환.
        if (row < 0 || row >= map.length || col < 0 || col >= map[row].length)
            return true;
        // 만약 이미 계산된 결과값이 있다면 해당 결과값을 반환.
        else if (visited[row][col])
            return escapable[row][col];
        
        // 그렇지 않다면 방문 체크 후
        visited[row][col] = true;
        // 현재 칸에서 이동 가능한 다음 칸을 파라미터로 넘겨주며 dfs 메소드를 호출한다.
        // 다음 칸에서의 결과값이 현재 칸에서의 결과값과 동일하므로
        // 해당 결과값을 escapable[row][co]에 기록해주고 반환한다.
        return escapable[row][col] = dfs(row + dr[hashMap.get(map[row][col])], col + dc[hashMap.get(map[row][col])]);
    }
}