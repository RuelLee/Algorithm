/*
 Author : Ruel
 Problem : Baekjoon 17092번 색칠 공부
 Problem address : https://www.acmicpc.net/problem/17092
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17092_색칠공부;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {0, 0, 0, -1, -1, -1, -2, -2, -2};
    static int[] dc = {0, -1, -2, 0, -1, -2, 0, -1, -2};

    public static void main(String[] args) throws IOException {
        // h * w 크기의 모눈종이가 주어진다.
        // 이중 n개의 칸이 검정색일 때, 3 * 3 크기의 모든 부분 모눈종이에 대해
        // 검정 칸의 개수가 0 ~ 9개인 것이 몇 개 있는지 출력하라
        //
        // 해쉬 맵
        // h와 w가 10의 9승으로 이차원 배열로 표현하기엔 너무 크다.
        // n이 10의 5승으로 그리 크지 않으므로
        // 점들이 속하는 3 * 3 크기의 부분 모눈종이를 모두 표시한다.
        // 기준은 가장 왼쪽 윗 점을 기준으로 3 * 3 크기의 부분 모눈종이로 만든다.
        //
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 전체 모눈종이의 크기 h * w
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        // 검정 칸의 수 n
        int n = Integer.parseInt(st.nextToken());

        // 해쉬맵으로 모든 부분 모눈 종이에 대해 표시한다.
        HashMap<Integer, HashMap<Integer, Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 검정 칸의 위치 (r, c)
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            
            // 해당 검정 칸이 3 * 3 크기의 부분 모눈종이에서
            // 각 칸에 속하는 9가지 경우에 모두 계산
            for (int d = 0; d < 9; d++) {
                int modR = r + dr[d];
                int modC = c + dc[d];
                
                // 해당 부분 모눈종이는 (1, 1)보다 같거나 크고, (h - 2, w - 2) 같거나 작은 범위를 갖는다.
                if (modR >= 1 && modC >= 1 && modR <= h - 2 && modC <= w - 2) {
                    if (!hashMap.containsKey(modR))
                        hashMap.put(modR, new HashMap<>());
                    if (!hashMap.get(modR).containsKey(modC))
                        hashMap.get(modR).put(modC, 1);
                    else
                        hashMap.get(modR).put(modC, hashMap.get(modR).get(modC) + 1);
                }
            }
        }

        // 해쉬맵에 계산된 모든 부분모눈종이를 살펴보며
        // 색칠된 검정 칸의 개수에 따라 분류한다.
        long[] counts = new long[10];
        for (int row : hashMap.keySet()) {
            for (int col : hashMap.get(row).keySet())
                counts[hashMap.get(row).get(col)]++;
        }

        // 하나도 색칠되지 않은 칸은
        // 전체 부분 모눈 종이의 수 = (h - 2) * (w - 2)에서
        // 1 ~ 9까지 색칠된 부분 모눈종이의 수를 뺀 값이다.
        long sum = 0;
        for (int i = 1; i < counts.length; i++)
            sum += counts[i];
        counts[0] = (long) (h - 2) * (w - 2) - sum;
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < counts.length; i++)
            sb.append(counts[i]).append("\n");
        // 답 출력
        System.out.print(sb);
    }
}