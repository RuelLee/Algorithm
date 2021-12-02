/*
 Author : Ruel
 Problem : Baekjoon 8907번 네온 사인
 Problem address : https://www.acmicpc.net/problem/8907
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 네온사인;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 컴공은 역시 공대. 수학을 빼놓을 수 없지 라는 생각이 드는 문제.
        // n개의 점이 주어지고, i번째 점과 연결된 i+1 ~ n까지의 변의 색상이 주어진다
        // 같은 색으로만 이루어진 삼각형의 개수는 총 몇개인지 구하는 문제
        // 점이 최대 1000개까지 주어지므로, 일일이 세서 계산한다면,
        // n개의 점에 대해, 자신보다 큰 번호의 점을 하나 선택하고, 그 점으로부터 자신보다 큰 번호의 점을 하나 선택해 3개의 점이 같은 색의 선분으로 이루어져있는지 구해야한다
        // 3개의 선분에 대해 탐색해야하므로 O(n^3)의 시간이 걸린다
        // 여기서 생각을 조금 바꾸어보면, 삼각형의 두 선분이 색이 서로 다르다면 나머지 한 색에는 상관없이 같은 색으로 이루어진 삼각형이 아니다.
        // 따라서 각 점에 모이는 선분들의 색 중 하나를 골라 개수를 센다
        // 한 점에 모이는 두 색에 대한 선분들을 각각 알 수 있다
        // 빨간색에서 한 선분을 고르고, 파란색에서 한 선분을 골라 삼각형을 만든다고 생각하면 무조건 같은 색으로 이루어진 삼각형이 아니다
        // 위와 같은 연산을 모든 점에 대해 수행하고 전체 삼각형에서 위 삼각형의 개수를 빼준다(위와 같은 계산은 빨간색일 때 한번, 파란색일 때 한번 두 번 계산이되므로 2로 나눈 값을 빼준다)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            int[] points = new int[n];
            StringTokenizer st;
            for (int i = 0; i < points.length - 1; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = i + 1; j < points.length; j++) {
                    int color = Integer.parseInt(st.nextToken());   // 색이 0 또는 1로 되어있으므로 그냥 색에 대한 값을 더해줘버리자.
                    points[i] += color;
                    points[j] += color;
                }
            }

            int count = 0;          // 같은 색이 아닌 삼각형을 센다.
            for (int point : points)
                count += (n - 1 - point) * point;       // n - 1 - points[1]은 파란색 선분의 개수, points[i]는 빨간색 선분의 개수. i점에서 파란색과 빨간색 선분으로 만드는 삼각형의 총 개수.
            int answer = n * (n - 1) * (n - 2) / 6 - count / 2;     // 답은 nC3에서 위에서 센 같은 색이 아닌 삼각형의 개수에서 중복 개수를 제거한 count/2를 빼준 값!
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }
}