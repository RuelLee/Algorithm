/*
 Author : Ruel
 Problem : Jungol 5930번 슬픔을 나누면 2
 Problem address : https://jungol.co.kr/problem/5930
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5930_슬픔을나누면2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 10^18이하의 자연수 A, B가 주어진다.
        // A를 나눌 때, A가 짝수일 때는 A / 2, A / 2가 되지만
        // 홀수인 경우 A / 2 와 A / 2 + 1이 된다고 한다.
        // A가 나눠 B가 될 수 있는지 여부를 출력하라
        //
        // 그래프 탐색
        // A를 조건에 맞게 나눠가며, B까지 도달할 수 있는지 확인하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 수 A, B
        long A = Long.parseLong(st.nextToken());
        long B = Long.parseLong(st.nextToken());

        // B까지 도달 여부
        boolean found = false;
        // BFS
        Queue<Long> queue = new LinkedList<>();
        queue.offer(A);
        // 방문 체크
        HashSet<Long> hashSet = new HashSet<>();
        while (!queue.isEmpty()) {
            long current = queue.poll();
            // current가 B이하가 됐고
            if (current <= B) {
                // current가 B라면, found 체크
                if (current == B)
                    found = true;
                // 그 외의 경우 이제 B가 될 수 없으므로 큐 탐색 종료
                break;
            }

            // current가 홀수인 경우, (current + 1) / 2 == current / 2 + 1에 도달할 수 있다.
            if (current % 2 == 1 && !hashSet.contains((current + 1) / 2)) {
                hashSet.add((current + 1) / 2);
                queue.offer((current + 1) / 2);
            }

            // 홀수든 짝수든 current / 2에는 도달할 수 있다.
            if (!hashSet.contains(current / 2)) {
                hashSet.add(current / 2);
                queue.offer(current / 2);
            }
        }
        // B까지 도착 여부 출력
        System.out.println(found ? 1 : 0);
    }
}