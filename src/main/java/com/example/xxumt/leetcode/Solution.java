package com.example.xxumt.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2024/1/19 09:51
 * @since 1.0
 */
public class Solution {

  /**
   * 字母异位词分组 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"] 输出:
   * [["bat"],["nat","tan"],["ate","eat","tea"]]
   *
   * @param strs 参数
   * @return 集合
   */
  public static List<List<String>> groupAnagrams(String[] strs) {
    return new ArrayList<>(
        Arrays.stream(strs)
            .collect(
                Collectors.groupingBy(
                    str -> {
                      char[] array = str.toCharArray();
                      Arrays.sort(array);
                      return new String(array);
                    }))
            .values());
  }

  public static List<List<String>> groupAnagrams2(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String str : strs) {
      int[] counts = new int[26];
      for (int i = 0; i < str.length(); i++) {
        counts[str.charAt(i) - 'a']++;
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < 26; i++) {
        if (counts[i] != 0) {
          sb.append((char) ('a' + i));
          sb.append(counts[i]);
        }
      }
      String key = sb.toString();
      List<String> list = map.getOrDefault(key, new ArrayList<String>());
      list.add(str);
      map.put(key, list);
    }
    return new ArrayList<List<String>>(map.values());
  }

  public static int longestConsecutive(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    // 排序时间复杂度为nlogn
    // Arrays.sort(nums);
    Set<Integer> numSet = new HashSet<>(nums.length);
    for (int num : nums) {
      numSet.add(num);
    }
    int max = 1;
    for (int num : numSet) {
      if (!numSet.contains(num - 1)) {
        int currentNum = num;
        int currentStack = 1;

        while (numSet.contains(currentNum + 1)) {
          currentNum++;
          currentStack++;
        }

        max = Math.max(max, currentStack);
      }
    }

    return max;
  }

  /**
   * 移动零
   *
   * @param nums 移动数组
   */
  public static void moveZero(int[] nums) {
    if (null == nums) {
      return;
    }
    int j = 0;
    // 方法一
    /*
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        nums[j++] = nums[i];
      }
    }
    for (int i = j; i < nums.length; i++) {
      nums[i] = 0;
    }*/

    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        if (i > j) {
          nums[j] = nums[i];
          nums[i] = 0;
        }
        j++;
      }
    }

    System.out.println(Arrays.toString(nums));
  }

  public static int maxArea(int[] height) {
    int i = 0, j = height.length - 1, res = 0;
    while (i < j) {
      /*res =
          height[i] < height[j]
              ? Math.max(res, (j - i) * height[i++])
              : Math.max(res, (j - i) * height[j--]);*/
      res = Math.max(res, Math.min(height[i], height[j]) * (j - i));
      if (height[i] < height[j]) {
        i++;
      } else {
        j--;
      }
    }
    return res;
  }

  public static List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    if (null == nums || nums.length < 3) {
      return result;
    }
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > 0) {
        break;
      }
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      int l = i + 1;
      int r = nums.length - 1;
      while (l < r) {
        int sum = nums[i] + nums[l] + nums[r];
        if (sum == 0) {
          result.add(Arrays.asList(nums[i], nums[l], nums[r]));
          while (l < r && nums[l] == nums[l + 1]) {
            l++;
          }
          while (l < r && nums[r] == nums[r - 1]) {
            r--;
          }
          l++;
          r--;
        }else if (sum < 0) {
          l++;
        }else {
          r--;
        }
      }
    }

    return result;
  }

  public static void main(String[] args) {
    /*String[] strs = new String[] {"eat", "tea", "tan", "ate", "nat", "bat"};
    System.out.println(JSON.toJSONString(groupAnagrams2(strs)));*/

    /*int[] nums = new int[] {105, 4, 200, 1, 3, 2};
    System.out.println(longestConsecutive(nums));*/

    /*int[] nums = new int[] {1,12,0,3,0};
    moveZero(nums);*/

    /*int[] height = new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7};
    System.out.println(maxArea(height));*/

    int[] nums = new int[] {-2,0,3,-1,4,0,3,4,1,1,1,-3,-5,4,0};
    System.out.println(JSON.toJSONString(threeSum(nums)));
  }
}
