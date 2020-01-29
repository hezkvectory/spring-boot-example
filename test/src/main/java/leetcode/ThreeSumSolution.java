package leetcode;

import java.util.*;

public class ThreeSumSolution {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(nums));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        //nums先进行排序
        Arrays.sort(nums);
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.get(num) == null) {
                List<Integer> subscripts = new ArrayList<>();
                subscripts.add(i);
                map.put(num, subscripts);
            } else {
                map.get(num).add(i);
            }
        }
        for (int i = 0; i <= nums.length - 3; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j <= nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int finalNum = -nums[i] - nums[j];
                if (finalNum < nums[j]) {
                    break;
                }
                List<Integer> subscripts = map.get(finalNum);
                if (subscripts == null) {
                    continue;
                }
                for (Integer subscript : subscripts) {
                    if (subscript != j && subscript != i) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[subscript]);
                        res.add(list);
                        break;
                    }
                }
            }
        }
        return res;
    }
}