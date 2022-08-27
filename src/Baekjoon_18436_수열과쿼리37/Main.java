/*
 Author : Ruel
 Problem : Baekjoon 18436번 수열과 쿼리 37
 Problem address : https://www.acmicpc.net/problem/18436
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18436_수열과쿼리37;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 쿼리의 종류는
        // 1 i x: Ai를 x로 바꾼다.
        // 2 l r: l ≤ i ≤ r에 속하는 모든 Ai중에서 짝수의 개수를 출력한다.
        // 3 l r: l ≤ i ≤ r에 속하는 모든 Ai중에서 홀수의 개수를 출력한다.
        // 로 주어질 때, 2, 3 쿼리의 답을 출력한다
        //
        // 세그먼트 트리 문제
        // 범위 내의 짝수와 홀수의 개수를 센다.
        // 당연히 둘 다 각각 세그먼트 트리를 만들 필요는 없다. 짝수 아니면 홀수이기 때문에.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 짝수의 개수만 센다.
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];
        // 세그먼트 트리 초기화
        init(nums, 1, 0, n - 1);
        
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            switch (order) {
                // 값 변경 쿼리
                case 1 -> modifyNum(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                // 짝수 개수 쿼리
                case 2 -> {
                    int l = Integer.parseInt(st.nextToken());
                    int r = Integer.parseInt(st.nextToken());
                    sb.append(countEvenNumbers(l, r, 1, 1, n)).append("\n");
                }
                // 홀수 개수 쿼리
                // r - l + 1개에서 countEvenbers 만큼이 짝수이면 나머지 개수는 홀수.
                case 3 -> {
                    int l = Integer.parseInt(st.nextToken());
                    int r = Integer.parseInt(st.nextToken());
                    sb.append(r - l + 1 - countEvenNumbers(l, r, 1, 1, n)).append("\n");
                }
            }
        }
        System.out.print(sb);
    }
    
    // 값 변경 메소드
    static void modifyNum(int target, int value) {
        // 만약 짝수 -> 홀수, 홀수 -> 짝수 같이 변경이 일어난다면
        if (nums[target - 1] % 2 != value % 2) {
            int loc = 1;
            int start = 1;
            int end = nums.length;
            // 해당하는 target의 주소까지 찾아간 후
            while (start < end) {
                int mid = (start + end) / 2;
                if (target <= mid) {
                    loc *= 2;
                    end = mid;
                } else {
                    loc = loc * 2 + 1;
                    start = mid + 1;
                }
            }
            // 짝수 여부에 따라서 값 변경.
            segmentTree[loc] = value % 2 == 0 ? 1 : 0;
            loc /= 2;
            // 부모 노드로 올라가면서, 짝수의 개수 변경
            while (loc > 0) {
                segmentTree[loc] = segmentTree[loc * 2] + segmentTree[loc * 2 + 1];
                loc /= 2;
            }
        }
        // nums 값 변경.
        nums[target - 1] = value;
    }

    // 짝수 값 세기
    static int countEvenNumbers(int targetStart, int targetEnd, int loc, int seekStart, int seekEnd) {
        // 범위가 일치할 때까지.
        while (targetStart != seekStart || targetEnd != seekEnd) {
            int mid = (seekStart + seekEnd) / 2;
            // 왼쪽 자식 노드
            if (targetEnd <= mid) {
                loc *= 2;
                seekEnd = mid;
            // 오른쪽 자식 노드
            } else if (targetStart > mid) {
                loc = loc * 2 + 1;
                seekStart = mid + 1;
            // 두 자식 노드에 범위가 걸쳐있는 경우.
            // targetStart ~ mid, mid + 1 ~ targetEnd로 나눠 계산한다.
            } else
                return countEvenNumbers(targetStart, mid, loc * 2, seekStart, mid) +
                        countEvenNumbers(mid + 1, targetEnd, loc * 2 + 1, mid + 1, seekEnd);
        }
        return segmentTree[loc];
    }

    // 세그먼트 트리 초기 세팅.
    static void init(int[] nums, int loc, int start, int end) {
        // start == end가 같다면(= 단말 노드라면) 해당 위치에 짝수 여부 저장.
        if (start == end) {
            segmentTree[loc] = nums[start] % 2 == 0 ? 1 : 0;
            return;
        }

        // 단말 노드가 아니라면 자식 노드의 짝수 개수들을 더해 값을 정한다.
        int mid = (start + end) / 2;
        init(nums, loc * 2, start, mid);
        init(nums, loc * 2 + 1, mid + 1, end);
        segmentTree[loc] = segmentTree[loc * 2] + segmentTree[loc * 2 + 1];
    }
}