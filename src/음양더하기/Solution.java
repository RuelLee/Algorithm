package 음양더하기;

public class Solution {
    public static void main(String[] args) {
        int[] absolutes = {4, 7, 12};
        boolean[] signs = {true, false, true};

        int total = 0;
        for (int i = 0; i < absolutes.length; i++) {
            if (!signs[i])
                total += absolutes[i] * -1;
            else
                total += absolutes[i];
        }
        System.out.println(total);
    }
}