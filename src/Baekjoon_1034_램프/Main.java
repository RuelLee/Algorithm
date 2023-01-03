/*
 Author : Ruel
 Problem : Baekjoon 1034번 램프
 Problem address : https://www.acmicpc.net/problem/1034
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1034_램프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 행, m개의 열의 전구들이 주어진다.
        // 각 열에는 스위치가 있으며, 해당 스위치를 누를 경우, 해당 열의 전구들이 반전된다고 한다.
        // 행의 모든 전구들이 켜져있을 때, 해당 행이 켜져있다고 할 때
        // k번 열의 스위치를 누른 뒤, 가장 많은 행의 전구들을 켜고자 했을 때, 총 몇 행의 전구들을 켤 수 있는가?
        //
        // 약간의 발상 전환이 필요한 문제?
        // 처음 문제를 읽었을 때는 브루트 포스로 모든 경우를 따져봐야하나? 싶지만
        // n과 m이 최대 50까지 주어지므로 어림없다.
        // 열의 스위치를 누를 경우, 해당 열의 전구들이 모두 반전되므로 사실상 상태는 2가지
        // 따라서 2가지 선택이 있는 50개의 열이라고 생각하더라도 역시 시간 초과.
        // 좀 더 시간을 들여 찬찬히 생각해보면
        // 같은 모양을 가진 행들만 모두 동시에 전구를 켤 수 있다는 걸 알 수 있다.
        // 모든 열이 유기적으로 켜지고 꺼지므로, 이번 행의 하나를 켠다 -> 다른 행의 하나의 상태를 반전시킨다가 되버리기 때문
        // 따라서 같은 모양을 가진 행이 모두 몇 개인지 살펴보고, 그 수가 많은 행부터 살펴본다.
        // 해당 행을 켤 수 있는가는 먼저
        // 1. 꺼져있는 전구들이 k보다 작거나 같아야하며
        // 2. k값과 꺼져있는 전구의 수의 홀짝수가 일치해야한다.
        // 같은 모양을 가진 행을 크기 내림차순으로 살펴보며 행을 켤 수 있는가를 살펴보면 풀 수 있는 문제였다!
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 입력 처리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 행의 대한 정보.
        // String으로 받아 같은 행의 개수를 세내간다.
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String row = br.readLine();

            if (hashMap.containsKey(row))
                hashMap.put(row, hashMap.get(row) + 1);
            else
                hashMap.put(row, 1);
        }

        // 스위치 조작 횟수.
        int k = Integer.parseInt(br.readLine());

        // 같은 모양의 행이 많은 것부터 내림차순으로 살펴본다.
        PriorityQueue<String> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(hashMap.get(o2), hashMap.get(o1)));
        for (String s : hashMap.keySet())
            priorityQueue.offer(s);

        int answer = 0;
        while (!priorityQueue.isEmpty()) {
            // 현재 행의 모양
            String current = priorityQueue.poll();
            // 꺼진 전구의 수
            int zeroCount = 0;
            for (int i = 0; i < m; i++) {
                if (current.charAt(i) == '0')
                    zeroCount++;
            }
            // 꺼진 전구의 수가 k보다 작고, 홀짝수가 k와 일치한다면
            // 해당 행을 켤 수 있다.
            // 바로 답안으로 해당 행의 개수를 기록하고 반복문을 종료한다.
            if (zeroCount <= k && (zeroCount % 2 == k % 2)) {
                answer = hashMap.get(current);
                break;
            }
        }

        // 답안 출력.
        System.out.println(answer);
    }
}