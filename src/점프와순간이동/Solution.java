package 점프와순간이동;

public class Solution {
    public static void main(String[] args) {
        // 온 거리의 2배로 순간이동할 때는 소모가 없으므로 최대한 순간이동을 해야한다.
        // 목표지점으로부터 2로 나눠 나머지가 1이라면 1만큼은 반드시 점프를 해야한다.
        // 지속적으로 2로 나눠가며, 나머지를 더해주자.

        int n = 5000;

        int answer = 0;
        while (n > 0) {
            if (n % 2 == 1)
                answer++;
            n /= 2;
        }
        System.out.println(answer);
    }
}