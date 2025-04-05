/*
 Author : Ruel
 Problem : Baekjoon 12919번 A와 B 2
 Problem address : https://www.acmicpc.net/problem/12919
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12919_A와B2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

class State {
    int start;
    int end;
    int inorder;

    public State(int start, int end, int inorder) {
        this.start = start;
        this.end = end;
        this.inorder = inorder;
    }
}

public class Main {
    static String s, t;

    public static void main(String[] args) throws IOException {
        // A와 B로만 이루어진 두 문자열 s, t가 주어진다.
        // s에
        // 1. 문자열 뒤에 A를 추가한다.
        // 2. 문자열 뒤에 B를 추가하고, 문자열을 뒤집는다.
        // 두 연산만 가지고서 s 문자열을 t 문자열로 만들 수 있는지 판별하라
        //
        // BFS
        // S 문자열에서 두 연산을 모두 행해나가면 그 가짓수가 2의 제곱으로 불어난다.
        // s와 t의 최소, 최대 문자열의 길이가 1 ~ 50에 해당하므로
        // 최악의 경우 약 2^49의 연산에 해당할 수도 있다.
        // 하지만 t에서 s로 줄여나갈 경우, 해당하지 않는 경우가 많이 줄어드므로 연산을 줄일 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 두 문자열 s와 t
        s = br.readLine();
        t = br.readLine();
        
        // 방문 체크
        boolean[][][] visited = new boolean[t.length()][t.length()][2];
        // BFS
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, t.length() - 1, 0));
        boolean possible = false;
        while (!queue.isEmpty()) {
            // 현재 t 문자열의 상태
            State current = queue.poll();
            // 방문했거나, s문자열보다 길이가 짧아졌다면 해당 상태는 건너뜀..
            if (visited[current.start][current.end][current.inorder] ||
                    current.end - current.start + 1 < s.length())
                continue;

            visited[current.start][current.end][current.inorder] = true;
            // s와 같은 문자열을 만들었다면 종료
            if (s.length() == current.end - current.start + 1 && isSame(current.start, current.end, current.inorder)) {
                possible = true;
                break;
            }
            
            // 뒤에서 문자열 A를 제거하는 경우
            if (t.charAt(current.inorder == 0 ? current.end : current.start) == 'A')
                queue.offer(new State(current.inorder == 0 ? current.start : current.start + 1,
                        current.inorder == 0 ? current.end - 1 : current.end, current.inorder));
            // 앞에서 문자열 B를 제거하고, 뒤집는 경우.
            if (t.charAt(current.inorder == 0 ? current.start : current.end) == 'B')
                queue.offer(new State(current.inorder == 0 ? current.start + 1 : current.start,
                        current.inorder == 0 ? current.end : current.end - 1, (current.inorder + 1) % 2));
        }
        // 답을 찾았다면 1 아니라면 0을 출력
        System.out.println(possible ? 1 : 0);
    }

    // s문자열과 현재 start ~ end까지의 t문자열이 inorder 순서일 때
    // 두 문자열을 비교한다.
    static boolean isSame(int start, int end, int inorder) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(inorder == 0 ? start + i : end - i))
                return false;
        }
        return true;
    }
}