/*
 Author : Ruel
 Problem : Baekjoon 17179번 케이크 자르기
 Problem address : https://www.acmicpc.net/problem/17179
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17179_케이크자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 l인 롤케이크가 주어진다.
        // 롤케이크에 자를 수 있는 m개의 지점이 있다.
        // 롤케이크를 q번 자를 때, 가장 작은 크기를 최대화시키고자 할 때, 그 크기는?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해 최소 크기를 정해두고, 해당 크기로 q번 자를 수있는지 확인한다.
        // 그 과정을 반복해 최소 크기의 최대값을 찾으면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 자르는 횟수는 n번 주어진다.
        int n = Integer.parseInt(st.nextToken());
        // m개의 롤케이크 지점
        int m = Integer.parseInt(st.nextToken());
        // 롤케이크의 길이
        int l = Integer.parseInt(st.nextToken());
        
        // 자를 수 있는 지점들
        int[] cutLines = new int[m];
        for (int i = 0; i < cutLines.length; i++)
            cutLines[i] = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        // n개의 자르는 횟수 처리.
        for (int i = 0; i < n; i++) {
            // q번 자른다.
            int q = Integer.parseInt(br.readLine());

            // 이분 탐색
            int start = 0;
            int end = l;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (isPossibleMinPiece(q, mid, cutLines, l))
                    start = mid + 1;
                else
                    end = mid - 1;
            }
            sb.append(end).append("\n");
        }
        System.out.print(sb);
    }

    // 길이가 l인 롤케이크에서 자르는 지점들이 cutLines로 주어졌을 때
    // 최소 크기가 minPiece 이상되도록 cycle회 이상 자를 수 있는지 확인한다.
    static boolean isPossibleMinPiece(int cycle, int minPiece, int[] cutLines, int l) {
        // 이전에 자른 지점
        int preLine = 0;
        // 자른 횟수
        int count = 0;
        for (int cutLine : cutLines) {
            // 자른 횟수가 cycle을 이미 지났다면 그만
            if (count >= cycle)
                break;

            // 이번 자를 지점에서 저번 지점 간의 길이가 minPiece 이상이라면 자른다.
            if (cutLine - preLine >= minPiece) {
                count++;
                preLine = cutLine;
            }
        }

        // 자른 횟수가 cycle회 이상이며
        // 마지막으로 조각의 길이(preLine ~ l까지)가 minPiece 이상일 경우
        // 가능한 경우이므로 true 리턴.
        // 그렇지 않다면 fasle
        return count >= cycle && l - preLine >= minPiece;
    }
}