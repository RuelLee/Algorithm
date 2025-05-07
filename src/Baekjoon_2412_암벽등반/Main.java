/*
 Author : Ruel
 Problem : Baekjoon 2412번 암벽 등반
 Problem address : https://www.acmicpc.net/problem/2412
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2412_암벽등반;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int x;
    int y;
    int turn;

    public Seek(int x, int y, int turn) {
        this.x = x;
        this.y = y;
        this.turn = turn;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n (1 <= n <= 50000)개의 홈이 파여있는 암벽이 있다.
        // 각 홈은 (x, y) 좌표로 표현되며, |a - x| <=2, |b - y|<=2 이면 (x,y)에서 (a,b)로 이동할 수 있다고 한다.
        // 암벽의 정상 y = t(1 <= t <= 200000)으로 이동하고자할 떄
        // 최소 이동 횟수는? 불가능하다면 -1을 출력한다.
        // 좌표는 x좌표는 1이상 100만 이하, y좌표는 t이하이다.
        // 처음 출발은 (0, 0)에서 한다.
        //
        // BFS, 맵
        // 이차원 배열로 각 지점에서 최소 이동 횟수를 기록하고자하면 너무 많은 메모리를 쓰게 된다.
        // 따라서 맵을 통해서 즉각적으로 접근하도록 하자.
        // 그 외에는 기본적인 BFS 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 홈, 정상의 높이 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 맵을 통해 각 홈에 이르는 최소 이동 횟수 정리
        HashMap<Integer, HashMap<Integer, Integer>> hashMap = new HashMap<>();
        hashMap.put(0, new HashMap<>());
        // 초기 출발 지점
        hashMap.get(0).put(0, 0);
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if (!hashMap.containsKey(x))
                hashMap.put(x, new HashMap<>());
            hashMap.get(x).put(y, Integer.MAX_VALUE);
        }
        
        // BFS
        Queue<Seek> queue = new LinkedList<>();
        queue.offer(new Seek(0, 0, 0));
        int answer = -1;
        while (!queue.isEmpty()) {
            Seek current = queue.poll();
            // 정상에 도달한 경우, 답 기록하고 while 문 종료
            if (current.y == t) {
                answer = current.turn;
                break;
            }

            // current에서 이동 가능한 다른 홈들을 살펴본다.
            for (int i = current.x - 2; i <= current.x + 2; i++) {
                if (!hashMap.containsKey(i))
                    continue;

                for (int j = current.y - 2; j <= current.y + 2; j++) {
                    if (hashMap.get(i).containsKey(j) && hashMap.get(i).get(j) > current.turn + 1) {
                        hashMap.get(i).put(j, current.turn + 1);
                        queue.offer(new Seek(i, j, current.turn + 1));
                    }
                }
            }
        }
        // 최소 이동 횟수 출력
        System.out.println(answer);
    }
}