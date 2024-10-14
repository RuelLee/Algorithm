/*
 Author : Ruel
 Problem : Baekjoon 24461번 그래프의 줄기
 Problem address : https://www.acmicpc.net/problem/24461
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24461_그래프의줄기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 트리 형태가 주어진다.
        // 트리의 형태가 일직선 형태가 될 때까지
        // 단말 노드를 일시에 없애는 작업을 반복한다.
        // 최종적으로 남아있는 노드의 번호를 오름차순으로 출력하라
        //
        // 위상 정렬, 트리 문제
        // 위상 정렬을 통해 단말 노드 여부를 계산하고
        // 단말 노드를 한번에 없애나가는 작업을 반복한다.
        // 직선 형태가 되려면 전체 노드의 진입 차수가 2이하가 되어야한다.
        // 위 작업을 최대 진입 차수가 2가 될 때까지 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 진입 차수
        int[] inDegrees = new int[n];
        // 진입 차수의 수
        int[] counts = new int[n];
        // 가장 큰 진입 차수
        int max = 0;
        
        // 연결
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n; i++)
            connections.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
            if (inDegrees[a] != 0)
                counts[inDegrees[a]]--;
            counts[++inDegrees[a]]++;
            max = Math.max(max, inDegrees[a]);

            if (inDegrees[b] != 0)
                counts[inDegrees[b]]--;
            counts[++inDegrees[b]]++;
            max = Math.max(max, inDegrees[b]);
        }

        // 진입 차수가 1인 단말 노드들을 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegrees.length; i++) {
            if (inDegrees[i] == 1)
                queue.offer(i);
        }

        Queue<Integer> nextQueue;
        // 제외된 노드들
        boolean[] except = new boolean[n];
        // 최대 진입차수가 2가 될 때까지 반복
        while (max > 2) {
            nextQueue = new LinkedList<>();
            
            // 현재 단말 노드들을 모두 제거
            while (!queue.isEmpty()) {
                // 현재 단말 노드
                int current = queue.poll();
                // 제거
                except[current] = true;
                
                // 연결된 노드들 진입 차수 감소
                for (int next : connections.get(current)) {
                    // 만약 next가 최대 진입 차수인 노드이고
                    // 최대 진입 차수인 노드의 개수가 1개라면
                    // max를 하나 감소 시킨다.
                    if (inDegrees[next] == max && counts[max] == 1)
                        max--;
                    
                    // 진입 차수 노드의 개수 및 진입 차수 수정
                    counts[inDegrees[next]--]--;
                    counts[inDegrees[next]]++;

                    // 만약 next가 단말 노드가 됐다면
                    // 다음 큐에 추가
                    if (inDegrees[next] == 1)
                        nextQueue.offer(next);
                }
            }
            // 다음 큐는 nextQueue에 담긴 노드들을 제거한다.
            queue = nextQueue;
        }
        
        // 남아있는 노드들을 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < except.length; i++) {
            if (!except[i])
                sb.append(i).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}