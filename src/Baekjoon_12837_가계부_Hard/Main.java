/*
 Author : Ruel
 Problem : Baekjoon 12837번 가계부 (Hard)
 Problem address : https://www.acmicpc.net/problem/12837
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12837_가계부_Hard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static long[] segmentTree;

    public static void main(String[] args) throws IOException {
        // 월곡이가 살아온 날 n, 쿼리의 개수 q가 주어진다
        // 쿼리는
        // 1 p x -> 생후 p일에 x를 추가한다
        // 2 p q -> 생후 p일부터 q일까지의 변화량을 출력한다
        // n, q와 쿼리들이 주어질 때 2쿼리에 계산된 값을 출력하라
        //
        // 세그먼트 트리 문제
        // 문제를 따라서 풀면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 단말 노드의 개수가 n보다 큰 2의 제곱수 중 가장 작은 제곱수를 찾아 할당한다.
        segmentTree = new long[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 1번 쿼리라면 변동량을 추가.
            if (Integer.parseInt(st.nextToken()) == 1)
                inputValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            // 2번 쿼리라면 주어진 구간의 변동량을 출력.
            else
                sb.append(getValue(1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 1, n)).append("\n");
        }
        System.out.print(sb);
    }

    // targetStart부터 targetEnd까지의 변동량을 출력하는 메소드.
    static long getValue(int loc, int targetStart, int targetEnd, int seekStart, int seekEnd) {
        // 이전 세그먼트 트리와 다르게 한 메소드 내에서 최대한 범위를 줄인 후,
        // 값이 분리될 때만 재귀적으로 메소드를 호출하도록 바꾸어보았다.
        while (true) {
            // 구간이 일치한다면 해당 세그먼트 트리의 값 리턴.
            if (targetStart == seekStart && targetEnd == seekEnd)
                return segmentTree[loc];

            int mid = (seekStart + seekEnd) / 2;
            // mid보다 targetEnd가 같거나 작다면 왼쪽 자식 노드
            if (targetEnd <= mid) {
                loc *= 2;
                seekEnd = mid;
            } else if (targetStart > mid) {
                // mid보다 크다면 오른쪽 자식 노드
                loc = loc * 2 + 1;
                seekStart = mid + 1;
            } else {
                // targetStart ~ mid ~ targetEnd의 범위라면 범위를 분할해서 재귀적으로 메소드를 태워보낸다.
                // targetStart ~ mid, mid + 1 ~ targetEnd 두 범위로 나눈다.
                // 그 때의 합이므로 두 값의 합을 리턴.
                return getValue(loc * 2, targetStart, mid, seekStart, mid) +
                        getValue(loc * 2 + 1, mid + 1, targetEnd, mid + 1, seekEnd);
            }
        }
    }

    // 변동량을 추가하는 메소드.
    static void inputValue(int day, int value) {
        int loc = 1;
        int start = 1;
        int end = n;
        // 반복문을 통해 자식 노드를 타고 내려가며 값을 갱신해준다.
        while (start < end) {
            segmentTree[loc] += value;
            int mid = (start + end) / 2;
            if (day <= mid) {
                end = mid;
                loc *= 2;
            } else {
                start = mid + 1;
                loc = loc * 2 + 1;
            }
        }
        // 마지막 단말 노드에 값 추가.
        segmentTree[loc] += value;
    }
}