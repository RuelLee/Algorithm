/*
 Author : Ruel
 Problem : Baekjoon 23326번 홍익 투어리스트
 Problem address : https://www.acmicpc.net/problem/23326
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23326_홍익투어리스트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 구역이 시계 방향 순서로 원형으로 배치되어있다.
        // 현재 1번 위치에서 시계방향으로 돌며, 명소들만 방문하고자 한다.
        // 처음에 n개 구역에 대한 명소 여부가 주어진다.
        // 다음과 같은 쿼리들을 처리한다.
        // 1 i : i번 구역을 명소로 지정하거나, 이미 명소라면 지정을 해제한다.
        // 2 x : 시계 방향으로 x만큼 이동한다.
        // 3 : 명소로 도달하기 위해 시계 방향으로 몇 칸 움직여야하는지 출력한다.
        //
        // 트리셋 문제
        // 현재 위치부터 탐색하여 명소의 위치를 찾아야한다.
        // 현재 위치부터 다음 값까지 빠르게 찾을 수 있다면 좋으므로
        // 이진 트리 형태를 띄는 트리 셋을 활용하여 구현하면 좋다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구역, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 트리셋에 명소인 구역들을 담는다.
        TreeSet<Integer> treeSet = new TreeSet<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            if (Integer.parseInt(st.nextToken()) == 1)
                treeSet.add(i);
        }
        
        // 현재 위치
        int current = 0;
        StringBuilder sb = new StringBuilder();
        // 쿼리 처리
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            if (o == 1) {   // 1번 쿼리
                int num = Integer.parseInt(st.nextToken()) - 1;
                // 이미 명소일 경우 해제
                if (treeSet.contains(num))
                    treeSet.remove(num);
                else        // 아닐 경우 명소 추가
                    treeSet.add(num);
            } else if (o == 2) {        // 2번 쿼리
                // x만큼 시계 방향으로 이동
                int x = Integer.parseInt(st.nextToken());
                current = (current + x) % n;
            } else {        // 3번 쿼리
                // current 이상에서 가장 가까운 명소를 찾는다.
                // current ~ n-1까지 명소가 없다면
                if (treeSet.higher(current - 1) == null) {
                    // 0 ~ current까지 명소를 찾는다.
                    // 그래도 없다면
                    // -1을 기록
                    if (treeSet.higher(-1) == null)
                        sb.append(-1);
                    else        // 있다면 해당하는 current -> n-1 -> 0 -> 다음 명소까지의 거리 기록
                        sb.append(treeSet.higher(-1) + (n - current));
                } else      // 0으로 넘어가지 않아도 찾아진다면 해당 거리 기록
                    sb.append(treeSet.higher(current - 1) - current);
                sb.append("\n");
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}