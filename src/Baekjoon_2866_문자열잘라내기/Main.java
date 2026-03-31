/*
 Author : Ruel
 Problem : Baekjoon 2866번 문자열 잘라내기
 Problem address : https://www.acmicpc.net/problem/2866
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2866_문자열잘라내기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static char[][] table;

    public static void main(String[] args) throws IOException {
        // r개의 행, c개의 열로 이루어진 테이블에 문자가 들어있다.
        // 각 열을 위에서부터 읽어 하나의 문자열을 만들 수 있다.
        // 만약 가장 위의 행을 지워, 만든 문자열들이 서로 중복되지 않는다면 cnt를 증가시키며
        // 위 과정을 반복한다.
        // cnt의 값은?
        //
        // 정렬 문제
        // 각 문자열의 접미사들 중 같은 것이 존재하는 최대 길이를 찾는다.
        // 해당 접미사를 제외한 부분의 길이가 cnt가 증가하는 구간.
        // 접미사이지만, 계산의 편의성을 위한 문자열을 역순으로 읽으며, 접두사로 구한다.
        // 각 역순으로 읽은 문자열을 저장하고, 정렬하여, 인접한 두 문자열의 최대 공통 접두사의 길이를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r개의 행, c개의 열
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        table = new char[r][];
        for (int i = 0; i < r; i++)
            table[i] = br.readLine().toCharArray();

        // 역순으로 문자열을 읽어 저장
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c; i++) {
            sb.setLength(0);
            for (int j = r - 1; j >= 0; j--)
                sb.append(table[j][i]);
            list.add(sb.toString());
        }
        // 정렬
        Collections.sort(list);

        // 인접한 두 문자열의 최대 공통 접두사의 길이를 구한다.
        int maxSamePrefix = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < r; j++) {
                if (list.get(i).charAt(j) != list.get(i + 1).charAt(j)) {
                    maxSamePrefix = Math.max(maxSamePrefix, j);
                    break;
                }
            }
        }
        // 해당 길이를 제외한 구간이 cnt가 증가하는 구간
        System.out.println(r - (maxSamePrefix + 1));
    }
}