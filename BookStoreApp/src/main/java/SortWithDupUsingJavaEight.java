import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortWithDupUsingJavaEight {

	public static void main(String args[]) {
		final int[] array = new int[] { 5, 5, 3, 7, 10, 14 };
		String largestNum = getLargestPossibleNum(array);
		System.out.println("Result :"+largestNum);
	}

	public static String getLargestPossibleNum(int[] array) {
		Stream<Integer> elementStreams = Arrays.stream(array).boxed();
		List<Integer> sortedList = elementStreams.sorted(
				(x, y) -> x.toString().concat(y.toString()).compareTo(y.toString().concat(x.toString())) > 0 ? -1 : 1)
				.collect(Collectors.toList());
		String result = "";
		for (Integer element : sortedList) {
			result = result+(element.toString());
		}
		return result;
	}

}
