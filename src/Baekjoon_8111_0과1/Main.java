/*
 Author : Ruel
 Problem : Baekjoon 8111번 0과 1
 Problem address : https://www.acmicpc.net/problem/8111
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8111_0과1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // 테스트케이스마다 n이 주어진다.
        // n의 배수이며, 1로 시작하며, 1과 0으로만 이루어져있고, 길이가 100이하인 수를 아무거나 출력하라
        // 그러한 수가 없다면 BRAK을 출력한다.
        //
        // BFS, 나머지 문제
        // 1과 0으로만 이루어진 100자리 이하의 수를 생각해보면
        // 총 2^99 + 1개 이다.(0으로 시작하는 수는 없으므로)
        // 이를 직접 계산하는 것은 말이 안된다.
        // 나머지 계산에 대해 생각해보면,
        // 어떠한 수를 더하거나 곱해도 나머지 연산의 값은 유지된다.
        // 따라서 n으로 나눈 나머지 값이 최초로 등장하는 수에 대해서만 기록하고 BFS 탐색을 하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        HashMap<Integer, BigInteger> hashMap = new HashMap<>();
        for (int testCase = 0; testCase < t; testCase++) {
            // n 값이 바뀌므로 매번 해쉬맵 초기화
            hashMap.clear();

            // 주어진 n
            int n = Integer.parseInt(br.readLine());
            // BFS
            Queue<BigInteger> queue = new LinkedList<>();
            // 1부터 탐색 가능
            queue.offer(BigInteger.ONE);
            hashMap.put(1 % n, BigInteger.ONE);
            // 큐가 비어있지 않고, 나머지가 0인 수가 아직 발견되지 않은 동안
            while (!queue.isEmpty() && !hashMap.containsKey(0)) {
                // 현재 수 current
                BigInteger current = queue.poll();
                // 길이가 100이상이 되었다면 탐색 종료
                if (current.toString().length() >= 100)
                    continue;

                // current의 10배
                BigInteger multi10 = current.multiply(BigInteger.valueOf(10));
                // 나머지
                int mod = multi10.mod(BigInteger.valueOf(n)).intValue();
                // 해당 나머지가 처음 등장한 경우
                // 해쉬맵에 기록 후, 큐에 추가
                if (!hashMap.containsKey(mod)) {
                    hashMap.put(mod, multi10);
                    queue.offer(multi10);
                }

                // current의 10배 후 + 1
                BigInteger multi10plus1 = current.multiply(BigInteger.valueOf(10)).add(BigInteger.ONE);
                // 나머지
                mod = multi10plus1.mod(BigInteger.valueOf(n)).intValue();
                // 마찬가지로 나머지가 처음 등장한 경우에만 해쉬맵에 등록 후, 큐에 추가
                if (!hashMap.containsKey(mod)) {
                    hashMap.put(mod, multi10plus1);
                    queue.offer(multi10plus1);
                }
            }
            // 반복문이 끝난 후
            // 나머지가 0인 수를 찾았다면 해당 수를 기록하고
            // 그렇지 못했다면 BRAK을 기록
            sb.append(!hashMap.containsKey(0) ? "BRAK" : hashMap.get(0)).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}