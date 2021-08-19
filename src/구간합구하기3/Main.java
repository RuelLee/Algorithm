/*
 Author : Ruel
 Problem : Baekjoon 11658번 구간 합 구하기 3
 Problem address : https://www.acmicpc.net/problem/11658
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 구간합구하기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] fenwick;
    static int[][] numArray;

    public static void main(String[] args) throws IOException {
        // 세그먼트 트리를 배열로 만들어서 구하려고 했으나 시간 초과..
        // 펜윅 트리라는 것을 이용하여 구간합을 구해야했다.
        // 세그먼트 트리와 유사하나, 비트연산을 통해 주소로 접근하기 때문에 코드가 상당히 간결했다.
        // 가령
        // idx      1       2       3       4       5       6       7       8
        // 값       1       2       3       4       5       6       7       8
        // 트리값   1       3       3       10      5       11      7       36
        // idx가 홀수인 값들(idx & -idx == 1)은 자신의 값만 저장한다.
        // 짝수인 짝수인 값들은 자신을 포함한 앞 (idx & -idx) 값 만큼의 값들의 연산을 저장한다
        // 가령 2일 경우, 2는 2진법으로 나타내면 0...010(2), -2는 음의보수를 취하게 되므로, 10....010(2)이 된다
        // 따라서 둘을 & 연산 시킬 경우 2가 나와, idx 1번과 idx 2번의 합인 3을 갖게 된다.
        // 값이 6일 경우, 0...110(2)이고, -6은 10...010(2)이 되므로 &연산의 결과가 2가 나와, idx 5, 6의 합인 11을 저장한다
        // 값이 8일 경우는 0...1000(2)이고, -8은 10...1000(2)이 되므로 &연산의 결과가 8이 나와, idx 1 ~ 8까지의 합인 36을 저장한다.
        // 먼저 값을 갱신하는 경우를 생각해보자.
        // 먼저 값을 따로 저장해둔 공간(펜윅트리가 아닌)에서 현재 들어온 값과 기존에 저장되어있던 값의 차이를 가져온다
        // 이를 idx번째인 자신뿐만 아니라 자신을 통해 영향을 미치는 다른 위치의 값들도 갱신해줘야한다.
        // 이를 어떻게 찾아가느냐가 문제인데, 위에서와 마찬가지로
        // 위치와 위치의 음의 보수를 & 연산할 경우, 자신이 영향을 미치는 다음 위치와의 차이가 나온다!
        // 가령 idx 1의 값을 갱신했다고 한다면, 2(1~2), 4(1~4), 8(1~8)의 값을 모두 갱신해줘야한다
        // 1 & -1 의 값은 1이다. 따라서 idx + (idx & -idx) 값이 2가 되어 2의 값에도 diff를 더해준다.
        // 2 & -2 의 값은 2이다. 따라서 다음 위치인 4까지의 차이가 나온다.
        // 4 & -4 의 값은 4로, 마찬가지로 다음 위치인 8까지의 차이인 4가 나왔다.
        // 따라서 값을 갱신할 때는 별도의 공간에 저장했던 원래 값과의 차이를 가져와, 펜윅트리에서 자신의 위치와 음의 보수를 & 연산한 값을 계속 더해가며 차이값을 갱신해주면 된다.
        // 이제 구간 합을 구하는 방법을 생각해보자.
        // 4부터 6까지의 합이 필요하다고 생각해보자.
        // 펜윅트리에서 6은 5와 6의 합을 갖고 있고, 4만 따로 저장해둔 곳은 없다
        // 따라서 1 ~ 6까지의 합에서 1 ~ 3까지의 합을 빼는 방식으로 구해야한다.
        // 1부터 6까지의 합을 구하는 방법은 마찬가지로 음의 보수를 취한 값을 빼주면서 값을 더해주면 된다.
        // 위에서 우린 6이 자신부터 2개의 값(5 ~ 6)을 연산한 것이라고 정의했다.
        // 따라서 6번의 값을 취하고, 음의 보수와의 &연산의 값인 2를 빼주면 4의 값이 나온다
        // 또한 4는 마찬가지로 음의 보수와 & 연산을 통해 4개의 값(1 ~ 4)의 값을 연산한 값이라고 정했다.
        // 4번 위치의 값을 연산해주고, 음의 보수와 &연산 값을 빼주면 0번 위치가 나와 더 이상 연산하지 않는다.
        // 간단히 요약하면
        // 핵심은 값을 갱신할 때는 자신을 음의 보수를 취한 값과 & 연산을 하여 그 값을 더해가며 값을 갱신해주고
        // 값을 가져올 때는 자신과 음의 보수를 취한 값을 & 연산하며 그 값을 빼가며 연산해주면 된다!

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        fenwick = new int[n + 1][n + 1];
        numArray = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                updateValue(i + 1, j + 1, Integer.parseInt(st.nextToken()));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            if (w == 0) {
                updateValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            } else {
                int x1 = Integer.parseInt(st.nextToken());
                int y1 = Integer.parseInt(st.nextToken());
                int x2 = Integer.parseInt(st.nextToken());
                int y2 = Integer.parseInt(st.nextToken());

                int sum = 0;
                for (int row = x1; row <= x2; row++)
                    sum += getValue(row, y1, y2);
                sb.append(sum).append("\n");
            }
        }
        System.out.println(sb);
    }

    static void updateValue(int row, int loc, int value) {
        int diff = value - numArray[row][loc];      // 기존에 입력됐던 값과의 diff를 가져오고
        numArray[row][loc] = value;         // 입력된 값을 저장하는 공간에는 값을 갱신해준다.
        while (loc < fenwick[row].length) {     // 펜윅트리의 크기를 넘지 않는 범위에서
            fenwick[row][loc] += diff;          // diff 값을 더해준다
            loc += loc & -loc;          // 위치는 자신의 위치와 음의 보수를 & 연산한 후 그 값을 더해가며 증가시킨다.
        }
    }

    static int getValue(int row, int start, int end) {      // start 부터 end까지의 계산 결과를 가져오려면
        return getOneToEndValue(row, end) - getOneToEndValue(row, start - 1);       // 1 ~ end까지의 결과와 1 ~ start-1 까지의 결과를 통해 계산해준다.
    }

    static int getOneToEndValue(int row, int end) {     // idx 1부터 end까지의 계산 결과
        int sum = 0;
        while (end > 0) {       // end(idx)값이 0보다 큰 범위 동안
            sum += fenwick[row][end];       // 값을 연산하며
            end -= (end & -end);            // 위치는 자신의 위치와 음의 보수를 & 연산한 값을 빼주며 이동한다.
        }
        return sum;
    }
}