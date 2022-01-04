/*
 Author : Ruel
 Problem : Baekjoon 3043번 장난감 탱크
 Problem address : https://www.acmicpc.net/problem/3043
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 장난감탱크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Tanks {
    int n;
    int r;
    int c;
    int target = -1;

    public Tanks(int n, int r, int c) {
        this.n = n;
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int count = 0;

    public static void main(String[] args) throws IOException {
        // 탱크의 개수 n이 주어진다
        // 그리고 각 탱크의 위치가 주어질 때, n * n 크기의 전장에
        // 각 행과 열에는 하나의 탱크만 존재하도록 이동시키는 최소 이동 횟수를 구하고
        // 그 때 각 탱크의 움직이는 방법을 출력한다. 단, 이동 도중 한 칸에 복수의 탱크가 존재할 수 없다
        // 그리디문제
        // 탱크들을 행과 열에 대해서 정렬한 후
        // 가장 왼쪽에 있는 탱크는 비어있는 왼쪽 열로 다가가고, 가장 오른쪽에 있는 탱크는 비어있는 가장 오른쪽 열로 간다
        // 따라서 탱크를 정렬하여, 첫번째 탱크는 1열로, 두번째 탱크는 2열로, ... , 5번째 탱크는 n열로 간다
        // 다만 가는데 왼쪽으로 이동하는 탱크는 정렬순이 이른 순으로 먼저 가야하고
        // 오른쪽으로 이동하는 탱크는 정렬순이 느린 순으로 먼저 가야 서로 중복되는 위치가 발생하지 않는다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int k = Integer.parseInt(br.readLine());

        Tanks[] tanks = new Tanks[k];
        StringTokenizer st;
        for (int i = 0; i < tanks.length; i++) {
            st = new StringTokenizer(br.readLine());
            tanks[i] = new Tanks(i + 1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(tanks, Comparator.comparingInt(o -> o.r));        // 행에 대해서 오른차순 정렬.
        Queue<Tanks> up = new LinkedList<>();
        Stack<Tanks> down = new Stack<>();
        for (int i = 0; i < tanks.length; i++) {
            tanks[i].target = i + 1;        // i번째 탱크는 i+1 위치로 갈 것이다.
            if (i + 1 < tanks[i].r)     // 만약 자신의 위치가 i+1보다 크다면 왼쪽으로 이동하는 경우.
                up.offer(tanks[i]);     // 큐에 넣어준다.
            else        // 반대라면 오른쪽으로 이동하는 경우.
                down.push(tanks[i]);        // 스택에 넣어준다.
        }
        StringBuilder sb = new StringBuilder();
        // 큐와 스택에서 차근차근 뽑아가며 탱크를 이동시켜준다.
        while (!up.isEmpty())
            sb.append(move(up.peek().target, up.peek().r, up.poll().n, true));
        while (!down.isEmpty())
            sb.append(move(down.peek().target, down.peek().r, down.pop().n, true));

        Arrays.sort(tanks, Comparator.comparingInt(o -> o.c));        // 탱크들을 열에 대해서 오름차순 정렬
        Queue<Tanks> left = new LinkedList<>();
        Stack<Tanks> right = new Stack<>();
        for (int i = 0; i < tanks.length; i++) {
            tanks[i].target = i + 1;
            if (i + 1 < tanks[i].c)     // 탱크가 왼쪽으로 이동한다면 큐에 넣어준다
                left.offer(tanks[i]);
            else            // 오른쪽으로 이동하는 경우
                right.push(tanks[i]);       // 스택에 넣어준다.
        }
        // 역시 큐와 스택에서 차근차근 뽑아가며 이동시켜준다.
        while (!left.isEmpty())
            sb.append(move(left.peek().target, left.peek().c, left.poll().n, false));
        while (!right.isEmpty())
            sb.append(move(right.peek().target, right.peek().c, right.pop().n, false));

        System.out.println(count);
        System.out.println(sb);
    }

    static String move(int to, int from, int n, boolean isVertical) {
        StringBuilder sb = new StringBuilder();
        if (isVertical) {   // 상하로 이동할 경우
            if (from < to)      // 아래로 이동
                for (int i = from; i < to; i++)
                    sb.append(n).append(" ").append("D").append("\n");
            else    // 위로 이동
                for (int i = from; i > to; i--)
                    sb.append(n).append(" ").append("U").append("\n");
        } else {        // 좌우로 이동할 경우
            if (from < to)      //  우로 이동
                for (int i = from; i < to; i++)
                    sb.append(n).append(" ").append("R").append("\n");
            else        //좌로 이동
                for (int i = from; i > to; i--)
                    sb.append(n).append(" ").append("L").append("\n");
        }
        // 움직인 횟수를 count에 반영
        count += Math.abs(to - from);
        // 이동 방법을 string으로 반환.
        return sb.toString();
    }
}