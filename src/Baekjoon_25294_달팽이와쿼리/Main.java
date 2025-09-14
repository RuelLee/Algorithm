/*
 Author : Ruel
 Problem : Baekjoon 25294번 달팽이와 쿼리
 Problem address : https://www.acmicpc.net/problem/25294
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25294_달팽이와쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n차원 달팽이 배열이 주어진다.
        // n은 1보다 큰 홀 수 이다.
        // 크기는 n * n이다.
        // 1보다 크거나 같고, n^2보다 같거나 작은 자연수가 중복없이 오름차순으로 시계방향 소용돌이 패턴으로 한칸씩 들어있다.
        // 가장 왼쪽 윗 칸은 1이다.
        // 크기가 3인 경우
        // 1 2 3
        // 8 9 4
        // 7 6 5 이다.
        // 두 종류의 쿼리가 q개 주어진다.
        // 1 n x y : 크기가 n인 달팽이 배열에서 x행 y열에 들어있는 수
        // 2 n z : 크기가 n인 달팽이 배열에서 z가 들어있는 행과 열
        //
        // 수학 문제
        // 중앙의 idx는 n*n이고, 중앙으로부터, 왼쪽 윗칸으로 하나씩 진행할 때마다 8, 16, 24, ...
        // 8의 배수로 idx가 적어지는 점을 이용하여 각각의 쿼리를 처리하는 메소드를 만들어준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // q개의 쿼리
        int q = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            // 1번 쿼리
            if (o == 1) {
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                sb.append(query1(n, x, y));
            } else {        // 2번 쿼리
                int z = Integer.parseInt(st.nextToken());
                int[] returned = query2(n, z);
                sb.append(returned[0]).append(" ").append(returned[1]);
            }
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
    
    // 1번 쿼리
    // 크기 n인 달팽이 배열에서 (x, y)의 값을 출력한다.
    static int query1(int n, int x, int y) {
        // 먼저 겉에서부터 깊이를 구한다.
        int depth = Math.min(Math.min(x, n - x + 1), Math.min(y, n - y + 1));
        // 그리고 기준이 되는 가장 왼쪽 윗쪽의 idx를 구한다.
        int idx = n * n;
        idx -= (n / 2 - depth + 1) * (8 + 8 * (n / 2 - depth + 1)) / 2;
        
        // 만약 x가 depth와 같다면
        // x가 현재 윗 변에 존재한다면
        if (x == depth)     // 열의 차이 만큼을 더해 반환
            return idx + (y - depth);
        else        // 그 외의 경우, 오른쪽 변으로 이동하기 위해 변의 길이 만큼 idx 추가
            idx += (n - (depth - 1) * 2 - 1);
        
        // y가 오른쪽 변에 해당한다면
        // 부족한 x 값 만큼을 idx에 추가해 반환
        if (y == n - (depth - 1))
            return idx + (x - depth);
        else        // 그 외의 경우 아랫변으로 이동하기 위해 변의 길이만큼을 더함
            idx += (n - (depth - 1) * 2 - 1);
        
        // x가 아랫변에 해당한다면
        // 만찬가지로 부족한 y값만큼을 idx에 추가해 반환
        if (x == n - (depth - 1))
            return idx + (n - (depth - 1) - y);
        else        // 아닐 경우, 왼쪽 변으로 이동하기 위해 변의 길이만큼 값을 더함
            idx += (n - (depth - 1) * 2 - 1);
        
        // 이제 무조건 왼쪽 변에 해당하므로, 부족한 값만큼 더해 반환
        return idx + (n - (depth - 1) - x);
    }

    // 두번째 쿼리를 처리
    // 크기가 n인 달팽이 배열에서 z값의 위치를 반환한다.
    static int[] query2(int n, int z) {
        // 왼쪽 윗점을 기준으로
        // z가 해당 idx보다 작은지를 비교하며 depth를 측정한다.
        int depth = 0;
        int idx = 1;
        while ((n / 2) > depth && idx + (n / 2 - depth) * 8 <= z)
            idx += (n / 2 - depth++) * 8;
        
        // z가 윗변에 속하는 경우
        if (z <= idx + n - 1 - depth * 2)
            return new int[]{depth + 1, depth + 1 + (z - idx)};
        else        // 아닐 경우 변의 길이만큼을 더함
            idx += n - 1 - depth * 2;
        
        // z가 오른쪽 변에 속하는 경우
        if (z <= idx + n - 1 - depth * 2)
            return new int[]{depth + 1 + (z - idx), n - depth};
        else
            idx += n - 1 - depth * 2;
        
        // z가 아랫변에 속하는 경우
        if (z <= idx + n - 1 - depth * 2)
            return new int[]{n - depth, n - depth - (z - idx)};
        else
            idx += n - 1 - depth * 2;
        // z가 왼쪽 변에 속하는 경우
        return new int[]{n - depth - (z - idx), depth + 1};
    }
}