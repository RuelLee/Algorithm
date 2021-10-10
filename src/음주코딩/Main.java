/*
 Author : Ruel
 Problem : Baekjoon 5676번 음주 코딩
 Problem address : https://www.acmicpc.net/problem/5676
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 음주코딩;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;       // 구간 곱을 저장할 팬윅트리
    static int[] nums;              // 숫자들 저장해둘 배열
    static int[] numOfZero;         // 0의 개수를 저장할 팬윅트리

    public static void main(String[] args) throws IOException {
        // 세그먼트 트리를 이용했다가 시간 초과가 났다 ㅠㅠ
        // 두 개의 펜윅트리를 이용하여 풀었다!
        // 구간의 곱이므로, 0이 들어오면 값이 전부 0이 되버린다
        // 따라서 0 값에 대해서 특별한 조치가 필요한데, 0값이 들어오면 펜윅트리에는 1값을 넣어둔다
        // 그리고 다른 0의 개수를 세는 펜윅트리를 만들어, 0이 1개 있다고 표시해준다.
        // 차후에 구간의 곱을 구할 때, 해당 구간의 0의 개수를 세 1개 이상 있다면 0을 반환하고, 0이 0개 라면 원래 구간 곱을 구해 답을 내자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            nums = new int[n + 1];
            numOfZero = new int[n + 1];
            fenwickTree = new int[n + 1];
            Arrays.fill(fenwickTree, 1);
            Arrays.fill(nums, 1);

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                int value = Integer.parseInt(st.nextToken());       // 곱이 + - 0인지만 중요하므로, 1, -1, 0으로 단순화시켜서 넣자.
                inputValue(i + 1, value > 0 ? 1 : value == 0 ? 0 : -1);
            }

            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());
                if (st.nextToken().charAt(0) == 'C') {
                    int target = Integer.parseInt(st.nextToken());
                    int value = Integer.parseInt(st.nextToken());
                    inputValue(target, value > 0 ? 1 : value == 0 ? 0 : -1);        // 값을 바꾸는 경우
                } else {        // 값을 출력하는 경우
                    int value = getValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                    sb.append((value > 0 ? "+" : (value == 0 ? "0" : "-")));
                }
            }
            sb.append("\n");
            input = br.readLine();
            if (input == null)
                break;
        }
        System.out.println(sb);
    }


    static int getValue(int start, int end) {       // 값을 얻어오는 메소드
        int zeros = 0;          // 0의 개수를 센다.
        int idx = end;
        while (idx > 0) {       // 1 ~ end까지의 0의 개수
            zeros += numOfZero[idx];
            idx -= (idx & -idx);
        }
        idx = start - 1;
        while (idx > 0) {           // 1 ~ start -1 까지의 0의 개수를 뺀다
            zeros -= numOfZero[idx];
            idx -= (idx & -idx);
        }
        if (zeros > 0)      // 0이 하나라도 있다면 구간 곱은 0
            return 0;

        int answer = 1;
        idx = end;
        while (idx > 0) {       // 1 ~ end 까지의 곱
            answer *= fenwickTree[idx];
            idx -= (idx & -idx);
        }
        idx = start - 1;
        while (idx > 0) {           // 1 ~ start -1 까지의 곱
            answer *= fenwickTree[idx];
            idx -= (idx & -idx);
        }
        // 두 곱을 곱해만 주면 된다.
        return answer;
    }

    static void inputValue(int target, int value) {     // 값을 넣는 메소드
        if (value == 0) {
            if (nums[target] != 0) {        // 기존 값이 0이 아니었는데 0으로 바뀌는 경우
                int idx = target;
                while (idx < numOfZero.length) {        // numOfZero에 0이 하나 있음을 표시하자
                    numOfZero[idx]++;
                    idx += (idx & -idx);
                }
            }

            if (nums[target] == -1) {       // 기존 값이 -1이었는데 0으로 바뀌는 경우, 팬윅트리 값을 -1에서 1로 바꾸기 위해 -1씩 곱해줘야한다.
                int idx = target;
                while (idx < fenwickTree.length) {
                    fenwickTree[idx] *= -1;
                    idx += (idx & -idx);
                }
            }
        } else {
            if (nums[target] == 0) {    // 기존 값이 0이고, 새로 들어온 값은 0이 아닌 경우. numOfZero 값을 하나씩 줄여줘야한다.
                int idx = target;
                while (idx < numOfZero.length) {
                    numOfZero[idx]--;
                    idx += (idx & -idx);
                }
            }

            if ((nums[target] == -1 || value == -1) && !(nums[target] == -1 && value == -1)) {      // 기존 값과 새로운 값의 부호가 바뀌는 경우
                int idx = target;
                while (idx < fenwickTree.length) {      // 팬윅트리에 값을 -1씩 곱해 부호를 바꿔준다.
                    fenwickTree[idx] *= -1;
                    idx += (idx & -idx);
                }
            }
        }
        nums[target] = value;       // 마지막으로 nums 에 새로운 값을 넣어준다.
    }
}