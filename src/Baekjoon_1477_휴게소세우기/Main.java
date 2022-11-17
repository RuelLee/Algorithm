/*
 Author : Ruel
 Problem : Baekjoon 1477번 휴게소 세우기
 Problem address : https://www.acmicpc.net/problem/1477
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1477_휴게소세우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 l인 고속도로 위에 n개의 휴게소가 주어져있다.
        // 각 휴게소의 위치가 주어지고, m개의 휴게소를 더 지으려고 한다.
        // 각 휴게소 간의 거리들 중 최대값을 최소로 하고자 한다.
        // 그 때 그 값은?
        //
        // 이분 탐색 문제
        // 처음에 가장 휴게소 간의 거리가 먼 구간을 골라, 중간 지점에 휴게소를 지어나가는
        // 그리디 문제로 접근하려 했으나, 이 경우, 해당 구간을 반으로만 나눌 뿐, n등분 하는 것이 불가능하다.
        // 따라서 이분 탐색을 통해, 목표 거리를 정하고, 해당 거리를 만족하려면 두 휴게소 간에
        // 몇 개의 휴게소를 추가로 건설해야하는지 세어, 이 추가 휴게소의 개수가 m개 이하인지 비교해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        // 휴게소들
        int[] restAreas = new int[n + 2];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++)
            restAreas[i] = Integer.parseInt(st.nextToken());
        // 0번은 0, 마지막은 휴게소 길이.
        restAreas[restAreas.length - 1] = l;
        Arrays.sort(restAreas);

        int left = 1;
        int right = l;
        while (left < right) {
            int mid = (left + right) / 2;
            // 휴게소 간의 거리 최대값을 mid로 만드는 것이 가능하다면
            // 휴게소 간의 거리 최대값을 더 적은 값으로 만드는 것이 가능한지 확인한다.
            if (canMakeMaxDistanceDiff(restAreas, mid, m))
                right = mid;
            // 불가능하다면
            // 더 큰 값에서는 가능한지 살펴본다.
            else
                left = mid + 1;
        }
        System.out.println(left);
    }

    // m개의 휴게소를 추가로 건설하여 휴게소 간의 거리 최대값을 diff로 만들 수 있는지 확인한다.
    static boolean canMakeMaxDistanceDiff(int[] restAreas, int diff, int m) {
        int count = 0;
        // i ~ i + 1 휴게소 간의 거리에 최대 휴게소 간의 거리가 diff가 되려면
        // ((i + 1)의 위치 - i의 위치 - 1) / diff를 올림한 값의 휴게소가 추가로 필요하다.
        // -1을 해주는 이유는 휴게소 간의 거리가 정확히 diff라면 추가 휴게소를 건설할 필요가 없기 때문.
        for (int i = 0; i < restAreas.length - 1; i++)
            count += Math.ceil(((restAreas[i + 1] - restAreas[i] - 1) / diff));
        // 추가 휴게소의 개수에 따라 가불가 여부를 반환한다.
        return count <= m;
    }
}