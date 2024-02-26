/*
 Author : Ruel
 Problem : Baekjoon 31423번 신촌 통폐합 계획
 Problem address : https://www.acmicpc.net/problem/31423
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31423_신촌통폐합계획;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 대학교 이름이 주어진다.
        // n - 1개의 i, j가 주어지는데
        // i번 대학 이름에 j번 대학 이름을 이어붙여 i번 대학 이름으로 새로 정하고
        // j번 대학은 i번 대학으로 통폐합된다.
        // 최종적으로 완성되는 대학의 이름은?
        //
        // 연결리스트와 관련이 있는 문제
        // 각 대학의 바로 뒷순서 대학과
        // 마지막 순서의 대학을 계산하여 이어붙여진 대학들의 순서를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 대학
        int n = Integer.parseInt(br.readLine());
        String[] universities = new String[n];
        for (int i = 0; i < n; i++)
            universities[i] = br.readLine();
        
        // 바로 뒷 순서와 현재 대학에서 마지막으로 붙여진 대학의 번호
        int[] backs = new int[n];
        int[] lastBacks = new int[n];
        for (int i = 0; i < backs.length; i++)
            backs[i] = lastBacks[i] = i;
        
        // 가장 마지막 첫번째
        int lastFront = 0;
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 앞 대학의 번호
            int front = Integer.parseInt(st.nextToken()) - 1;
            // 뒷 대학의 번호
            int back = Integer.parseInt(st.nextToken()) - 1;

            // 앞 대학의 마지막 순서 대학 바로 뒤에
            // back을 이어붙인다.
            backs[lastBacks[front]] = back;
            // front의 마지막 대학에 back의 마지막 대학을 넣는다.
            lastBacks[front] = lastBacks[back];
            // 가장 마지막 첫번째 갱신
            lastFront = front;
        }

        StringBuilder sb = new StringBuilder();
        // lastFront부터 순서대로 쫓아가며 대학 이름을 이어붙인다.
        while (lastFront != backs[lastFront]) {
            sb.append(universities[lastFront]);
            lastFront = backs[lastFront];
        }
        sb.append(universities[lastFront]);
        // 답안 출력
        System.out.println(sb);
    }
}