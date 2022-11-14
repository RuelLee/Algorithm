/*
 Author : Ruel
 Problem : Baekjoon 16287번 Parcel
 Problem address : https://www.acmicpc.net/problem/16287
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16287_Parcel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 소포들이 주어진다.
        // 4개의 소포를 골라 그 무게의 합이 정확히 w가 되는 경우가 있는지 여부를 출력하라
        //
        // DP 문제
        // n이 최대 5000개가 주어지므로 4개를 일일이 골라내선 절대 안된다.
        // 잘 감이 잡히지 않아 다른 사람의 풀이를 참고하였는데 센스가 정말 대단했던 것 같다.
        // 5000개 중 4개를 골라내는 것은 시간이 많이 걸리지만 2개를 골라내는 것은 많은 시간이 걸리지 않는다.
        // 따라서 a + b + c + d = w라는 문제에서
        // a + b = w - (c + d) 라는 문제로 바꾼다.
        // 소포들 중 2개를 골라서 그 합에 해당 하는 dp에 두 소포의 인덱스들 중 큰 값을 기록해둔다.
        // 그러면서 이전에 찾아진 소포들의 쌍 중 이번에 찾은 쌍과의 합이 w인 경우, 해당하는 4개의 소포일 확률이 있다.
        // 이제 고려해봐야할 점은 찾은 4개의 소포들 중 서로 중복되는 것이 있느냐이다.
        // 이에 우리는 DP에 기록해두었던 이전에 찾은 소포들 중 큰 인덱스 값을 참고한다
        // 이전에 찾았던 소포쌍의 인덱스가 x, y(x < y), 이번에 찾은 인덱스가 i, j(i < j)
        // dp에는 y가 기록되어있다. 따라서 y < i만 성립하는지만 살펴본다면
        // 서로 소포들이 중복되지 않음을 알 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 소포들
        int[] parcels = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 무게에 해당하는 소포 쌍들 중 큰 idx 값을 저장해둔다.
        int[] dp = new int[w + 1];
        boolean found = false;
        // 한 쌍이 소포를 찾는다.
        for (int i = 0; i < parcels.length; i++) {
            for (int j = i + 1; j < parcels.length; j++) {
                // 소포 무게의 합.
                int weight = parcels[i] + parcels[j];
                // 만약 원하는 무게 w를 넘는다면 건너 뛴다.
                if (weight > w)
                    continue;
                // 만약 w - weight에 해당하는 소포들의 쌍을 이전에 찾은 적이 있고
                // dp에 기록된 dp[w - weight]값이 i보다 작다면
                // 찾아진 4개의 소포들은 서로 중복되지 않는다.
                // 해당하는 소포 4개를 찾았다.
                else if (dp[w - weight] != 0 && dp[w - weight] < i) {
                    found = true;
                    break;
                }

                // 만약 위의 경우에 해당하지 않는다면 dp에 찾아진 소포들 중 큰 값을 기록한다.
                // 위의 소포찾기를 반복할 수록, 같은 무게에 해당하는 소포 쌍이 중복으로 찾아질 수 있다.
                // 그 때는 찾아진 소포 쌍의 큰 인덱스 중 더 작은 값을 dp에 기록해나가도록 하자.
                dp[weight] = dp[weight] == 0 ? j : Math.min(dp[weight], j);
            }
        }

        // 답안 출력.
        System.out.println(found ? "YES" : "NO");
    }
}