/*
 Author : Ruel
 Problem : Baekjoon 18235번 지금 만나러 갑니다
 Problem address : https://www.acmicpc.net/problem/18235
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18235_지금만나러갑니다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 공간에서 두 오리의 위치가 주어진다.
        // 각 오리는 1일차에는 1, 2일차에는 2, 3일차에는 4, ...
        // 와 같이 2의 제곱으로 이동할 수 있다.
        // 두 오리가 만나는데 걸리는 최소 일은?
        //
        // 너비 우선 탐색 문제
        // 간단하게 각 지점에 도달하는 최소일만을 기록해서는 안된다.
        // 시간이 조금 더 걸리더라도 같은 위치에 동시에 두 오리가 도착해야하기 때문
        // 따라서 날짜에 따라 각 오리가 이동할 수 있는 좌표를 모두 계산하고
        // 이를 토대로 겹치는 좌표가 있는지 확인.
        // 없다면 다음 날로 진행한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 공간에 대한 제약 n
        int n = Integer.parseInt(st.nextToken());
        // 오리들의 위치
        int a = Integer.parseInt(st.nextToken()) - 1;
        int b = Integer.parseInt(st.nextToken()) - 1;

        // 왼쪽 오리가 위치할 수 있는 좌표들
        List<Integer> left = new ArrayList<>();
        left.add(a);
        // 오른쪽 오리가 위치할 수 있는 좌표들
        List<Integer> right = new ArrayList<>();
        right.add(b);
        int day = 0;
        while (true) {
            // 만약 두 리스트가 중 하나라도 비었다면
            // 해당 날짜에는 위치할 수 있는 좌표가 없는 경우.
            // 불가능한 경우이므로 반복문 종료
            if (left.isEmpty() || right.isEmpty()) {
                day = -1;
                break;
            }

            // 두 좌표들을 정렬하여 보다 편하게 비교한다.
            Collections.sort(left);
            Collections.sort(right);
            boolean find = false;
            int idx = 0;
            // 왼쪽 오리의 위치를 하나씩 살펴보며
            for (int l : left) {
                // 오른쪽 오리의 위치들 중 l보다 작은 것은 건너뛴다.
                while (idx < right.size() && l > right.get(idx))
                    idx++;

                // 오른쪽 오리의 모든 좌표를 다 살펴본 경우.
                // = 겹치는 좌표가 존재하지 않는 경우.
                if (idx == right.size())
                    break;
                    // 겹치는 좌표가 존재하는 경우.
                else if (l == right.get(idx)) {
                    find = true;
                    break;
                }
            }
            // day에 겹치는 좌표를 찾았다면 반복문 종료
            if (find)
                break;

            // 그렇지 못한 경우, 다음날에 가능한 두 오리들의 좌표를 계산한다.
            List<Integer> nextLeft = new ArrayList<>();
            boolean[] visited = new boolean[n];
            // 왼쪽 오리가 day + 1에 위치할 수 있는 좌표들
            for (int l : left) {
                int small = l - (1 << day);
                if (small >= 0 && !visited[small]) {
                    nextLeft.add(small);
                    visited[small] = true;
                }
                int big = l + (1 << day);
                if (big < n && !visited[big]) {
                    nextLeft.add(big);
                    visited[big] = true;
                }
            }

            // 마찬가지로 오른쪽 오리에 대해서도 계산
            List<Integer> nextRight = new ArrayList<>();
            visited = new boolean[n];
            for (int r : right) {
                int small = r - (1 << day);
                if (small >= 0 && !visited[small]) {
                    nextRight.add(small);
                    visited[small] = true;
                }
                int big = r + (1 << day);
                if (big < n && !visited[big]) {
                    nextRight.add(big);
                    visited[big] = true;
                }
            }

            // left와 right 리스트들을 교체
            left = nextLeft;
            right = nextRight;
            // 날짜 진행
            day++;
        }

        // 두 오리가 동시에 위치하는 좌표가 찾아진 경우
        // 해당 날짜에서 반복문 종료하였으므로 day에 기록
        // 찾지 못한 경우에는 -1이 기록되어있다.
        // 해당 값 출력.
        System.out.println(day);
    }
}