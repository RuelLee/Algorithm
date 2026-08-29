/*
 Author : Ruel
 Problem : Jungol 8017번 가로등
 Problem address : https://jungol.co.kr/problem/8017
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8017_가로등;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] delta = {-1, 1};

    public static void main(String[] args) throws IOException {
        // N개의 가로등이 x축 위에 주어진다.
        // 각 0 ~ L까지의 정수 위치 중 가로등과의 거리가 K번째인 지점까지 거리들을 출력하라
        //
        // BFS, 해쉬맵
        // L이 최대 10^18까지 주어지지만 K가 최대 50만으로 주어지므로
        // 범위는 크지만 실제로 탐색해야하는 범위는 최대 50만으로 작다.
        // 따라서 해쉬맵으로 가로등의 거리를 기록해나가면서 BFS로 탐색한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 0 ~ L까지의 범위에 가로등의 수 N, K번째 거리까지 출력
        long L = Long.parseLong(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 해쉬맵으로 거리 기록
        HashMap<Long, Integer> hashMap = new HashMap<>();
        // BFS
        Queue<Long> queue = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        // 가로등의 위치 기록
        for (int i = 0; i < N; i++) {
            long num = Long.parseLong(st.nextToken());
            hashMap.put(num, 0);
            queue.add(num);
        }

        // 가로등의 수만큼의 거리는 0
        int cnt = 0;
        for (long key : hashMap.keySet()) {
            sb.append(0).append("\n");
            if (++cnt == K)
                break;
        }
        // 나머지는 BFS 탐색하며 기록한다.
        while (!queue.isEmpty() && cnt < K) {
            long num = queue.poll();
            for (int d = 0; d < delta.length && cnt < K; d++) {
                long next = num + delta[d];
                if (!hashMap.containsKey(next) && next >= 0 && next <= L) {
                    hashMap.put(next, hashMap.get(num) + 1);
                    queue.offer(next);
                    cnt++;
                    sb.append(hashMap.get(next)).append("\n");

                }
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}