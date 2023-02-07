/*
 Author : Ruel
 Problem : Baekjoon 13160번 최대 클리크 구하기
 Problem address : https://www.acmicpc.net/problem/13160 
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13160_최대클리크구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 그래프 이론에서 클리크란, 완전 그래프인 부분 그래프를 의미한다.
        // N개의 구간이 있다. i번 구간의 시작점은 Si, 끝점은 Ei이며, 어떤 두 구간이 한 점 이상을 공유하면 이 두 구간을 ‘겹친다’고 표현한다.
        //  구간 그래프란, N개의 정점이 있고, i번 구간과 j번 구간이 겹칠 때, i번 정점과 j번 정점 사이에 간선이 존재하는 그래프다.
        // N개의 구간이 주어졌을 때, 이에 대한 구간 그래프의 최대 클리크를 구하시오.
        //
        // 정렬, 우선순위큐 문제
        // n개의 구간이 주어질 때 서로 중복되는 구간이 있는 구간들의 집합을 구하라는 문제
        // 구간들의 시작점에 대해 정렬한 뒤
        // 순차적으로 구간을 살펴보며, 현재 구간의 시작점보다 작은 끝점을 갖는 구간들을 제외해나간다.
        // 이때 집합의 최대 크기를 구하고, 그 집합에 속한 구간들을 출력해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // n개의 구간
        int n = Integer.parseInt(br.readLine());
        int[][] lines = new int[n][3];
        for (int i = 0; i < lines.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            lines[i][0] = i + 1;
            lines[i][1] = Integer.parseInt(st.nextToken());
            lines[i][2] = Integer.parseInt(st.nextToken());
        }
        // 시작점에 대해 오름차순 정렬
        Arrays.sort(lines, Comparator.comparingInt(value -> value[1]));

        // 클리크를 이루는 구간들을
        // 끝점에 대해 최소 힙 우선순위큐로 저장한다.
        PriorityQueue<Integer> clique = new PriorityQueue<>(Comparator.comparingInt(value -> lines[value][2]));
        // 클리크의 최대 크기
        int maxSize = 0;
        // 그 때의 마지막 추가된 구간의 idx
        int idx = 0;
        for (int i = 0; i < lines.length; i++) {
            // i번째 구간의 시작점보다 작은 끝점을 갖는 구간들을 clique에서 제거한다.
            while (!clique.isEmpty() && lines[clique.peek()][2] < lines[i][1])
                clique.poll();
            // i 추가
            clique.offer(i);
            
            // 클리크의 크기가 최대 크기를 갱신하면
            if (clique.size() > maxSize) {
                // maxSize와 idx값 갱신.
                maxSize = clique.size();
                idx = i;
            }
        }

        // maxSize와 idx를 기반으로 답안을 작성한다.
        StringBuilder sb = new StringBuilder();
        // 클리크의 최대 크기
        sb.append(maxSize).append("\n");
        // 끝점이 idx의 시작점보다 작은 구간들을 모두 제거되었다.
        // 따라서 idx의 시작점을 포함하고 있는 구간들을 모두 답안에 추가시켜준다.
        for (int[] line : lines) {
            if (line[1] <= lines[idx][1] && lines[idx][1] <= line[2])
                sb.append(line[0]).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답안 출력
        System.out.println(sb);
    }
}