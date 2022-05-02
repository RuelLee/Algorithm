/*
 Author : Ruel
 Problem : Baekjoon 8096번 Monochromatic Triangles
 Problem address : https://www.acmicpc.net/problem/8096
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8096_MonochromaticTriangles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 조합을 활용한 삼각형 문제
        // 여러 점들이 있고, 세 점이 직선상에 위치한 경우는 없다고 한다
        // 이 때 점들은 빨간색 혹은 검정색 선으로 연결되어있고, 단색으로만 연결된 삼각형을 Monochromatic Triangle이라 부른다
        // 빨간 선들이 주어질 때 Monochromatic Triangles의 개수를 구하라.
        // 삼각형을 선택할 때, 한 선을 빨간색, 다른 선을 검정색으로 선택하면, 나머지가 무슨 색이든 Monochromatic Triangle이 아니다
        // 따라서 한 점에 모이는 빨간 선의 개수를 세자. 그러면 자연히 검정 선의 개수도 알 수 있다(한 점에 모이는 선분은 n-1 개이므로, 여기서 빨간 선분을 빼면 검정 선분의 수)
        // 각 한 선분을 모아 삼각형이 되는 가지수를, 모든 점에 대해서 시행하자
        // 그렇게 계산된 삼각형의 수는 빨간색2, 검정색1일 때는 빨간색으로 두번, 빨간색1, 검정색2일 때는 검정색으로 두번 셌을 것이다.
        // 결과적으로 각각의 삼각형을 모두 두번씩 셌으므로, 2로 나눠주면 Monochromatic Triangle이 아닌 삼각형의 수가 나온다
        // 전체 삼각형은 nC3으로 전체 점의 개수 중 3개의 점을 선택하는 가지 수이므로, 여기서 위 값을 빼면 답이 나온다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        int[] counts = new int[n + 1];      // 각 점에 모이는 빨간색 선의 개수를 센다.
        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            counts[Integer.parseInt(st.nextToken())]++;     // 선분을 이루는 한 점과
            counts[Integer.parseInt(st.nextToken())]++;     // 다른 한 점
        }

        int count = 0;
        // 한 점에 counts[i]개의 빨간 선과, (n - counts[i] - 1)개의 검정 선이 모인다.
        // 빨간 선 하나와, 검정 선을 하나 선택하면, 다른 선분은 자연스레 선택되어 삼각형을 이룬다.
        // 이 때 Monochromatic Triangle이 아닌 삼각형의 개수는 각 색의 선분 개수의 곱
        for (int i = 1; i < counts.length; i++)
            count += counts[i] * (n - counts[i] - 1);

        // 전체 삼각형의 개수는 nC3
        int totalTriangle = n * (n - 1) * (n - 2) / 6;
        // 이를 위에서 센 Monochromatic Triangle이 아닌 삼각형의 개수(count / 2)를 빼주면
        // Monochromatic Triangles인 삼각형의 개수가 나온다.
        System.out.println(totalTriangle - (count / 2));
    }
}