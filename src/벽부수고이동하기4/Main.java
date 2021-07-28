/*
 Author : Ruel
 Problem : Baekjoon 16946번 벽 부수고 이동하기 4
 Problem address : https://www.acmicpc.net/problem/16946
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 벽부수고이동하기4;

import java.util.*;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[][] map;
    static int[][] group;
    static int[] groupValue;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 해당 계산 때마다 주어진 방의 갯수를 모두 세는 건 시간이 너무 오래 걸린다!
        // 비어있는 공간을 저장해두고, 연결된 비어있는 공간은 하나의 그룹으로 묶어주고, 연결된 방의 갯수를 그룹마다 저장해주자
        // 나중에 벽을 하나 부수고 사방을 둘러봤을 때, 중복되지 않는 그룹의 연결된 방의 갯수의 합이 답!
        // 처음엔 union-find로 계산하려다 시간초과!
        // 그 이후엔 좌표마다 연결된 방의 갯수를 저장하려다 시간 초과!
        // 계산할 때 그룹만 저장해두고, 해당 그룹의 값은 1차원 배열에 저장해두자.
        // DFS가 BFS보다 빠른가보다. BFS로는 시간초과가 났지만, DFS로 처음부터 다시 짜니 통과!

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        map = new int[n][m];
        group = new int[n][m];
        groupValue = new int[1000000];      // 하나 걸러 빈방이 있는 경우 많은 그룹이 지정될 수 있다. 넉넉히 값을 지정해주자.

        List<Point> vacant = new ArrayList<>();
        List<Point> wall = new ArrayList<>();

        sc.nextLine();
        for (int i = 0; i < map.length; i++) {
            String row = sc.nextLine();
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = row.charAt(j) - '0';

                if (map[i][j] == 0)     // 빈 공간과 벽은 따로 저장해두자
                    vacant.add(new Point(i, j));
                else
                    wall.add(new Point(i, j));
            }
        }
        int groupNum = 1;
        for (Point vc : vacant) {
            if (group[vc.r][vc.c] == 0)     // 아직 그룹이 할당되지 않은 빈 공간을 돌아다니며 그룹을 할당해주고 해당 그룹의 연결된 방의 갯수를 저장하자
                groupValue[groupNum] = allocateGroup(vc.r, vc.c, groupNum++);
        }

        for (Point wl : wall) {     // 각 벽에서는
            int count = 1;
            HashSet<Integer> hashSet = new HashSet<>();
            for (int d = 0; d < 4; d++) {       // 사방을 살펴보고
                int nextR = wl.r + dr[d];
                int nextC = wl.c + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] == 0 && !hashSet.contains(group[nextR][nextC])) {      // 아직 포함되지 않은 그룹이라면 연결된 방의 갯수를 더해주고
                    count += groupValue[group[nextR][nextC]];
                    hashSet.add(group[nextR][nextC]);
                }
            }
            map[wl.r][wl.c] = count;        // map에 값을 바꿔주자.
        }
        StringBuilder sb = new StringBuilder();
        for (int[] mp : map) {      // 그리고 map을 그대로 출력해주면 답안.
            for (int i : mp)
                sb.append(i % 10);
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static int allocateGroup(int r, int c, int groupNum) {
        int count = 1;
        group[r][c] = groupNum;     // 연결되어있는 공간이니 groupNum을 할당해주고
        for (int d = 0; d < 4; d++) {       // 사방을 둘러봐서
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            if (checkArea(nextR, nextC) && map[nextR][nextC] == 0)      // 배열 범위 내이고, 빈 공간일 경우
                count += allocateGroup(nextR, nextC, groupNum);     // 재귀적으로 allocateGroup을 불러주고, 연결된 방의 갯수를 리턴받자.
        }
        return count;       // 최종적으로 쌓인 count 값이 연결된 방의 갯수
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}