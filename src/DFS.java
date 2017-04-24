import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class DFS {

	private static final String PROBLEM = "maze";
	private static final String EXT = ".dat";

	public static void main(String[] args) {
		Scanner scan;
		try {
			scan = new Scanner(new File(PROBLEM + EXT));
		} catch (FileNotFoundException ex) {
			printF(true, "File not found: %s", ex.getMessage());
			return;
		}
		int[][] d = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };
		int times = Integer.parseInt(scan.nextLine());
		while (times-- > 0) {
			String[] split = scan.nextLine().split(" ");
			int rsiz = Integer.parseInt(split[0]);
			int csiz = Integer.parseInt(split[1]);
			char[][] maze = new char[rsiz][csiz];
			int[][] dis = new int[rsiz][csiz];
			fill(dis, 100000);
			for (int i = 0; i < rsiz; i++)
				maze[i] = scan.nextLine().toCharArray();
			Stack<Integer> stack = new Stack<Integer>();
			boolean[][] visited = new boolean[rsiz][csiz];
			for (boolean[] b : visited)
				Arrays.fill(b, false);
			int[] start = getPosition(maze, 'o');
			dis[start[0]][start[1]] = -1;
			stack.add(start[0]);
			stack.add(start[1]);
			int er = -1;
			int ec = -1;
			boolean solvable = false;
			while (!stack.isEmpty()) {
				int c = stack.pop();
				int r = stack.pop();
				if (maze[r][c] == '?') {
					solvable = true;
					er = r;
					ec = c;
				}
				for (int i = 0; i < 4; i++) {
					int rr = r + d[0][i];
					int cc = c + d[1][i];
					if (!inBounds(maze, rr, cc) || maze[rr][cc] == '#')
						continue;
					if (dis[r][c] + 1 < dis[rr][cc]) {
						dis[rr][cc] = dis[r][c] + 1;
						stack.push(rr);
						stack.push(cc);
					}
				}
			}
			List<Integer> used = new ArrayList<Integer>();
			if (!solvable)
				printLine("Not solvable!");
			else {
				printLine("Shortest distance: " + (dis[er][ec] + 1));
				Queue<Integer> qu = new LinkedList<Integer>();
				qu.add(er);
				qu.add(ec);
				while (!qu.isEmpty()) {
					int r = qu.poll();
					int c = qu.poll();
					for (int i = 0; i < 4; i++) {
						int rr = r + d[0][i];
						int cc = c + d[1][i];
						if (!inBounds(maze, rr, cc) || maze[rr][cc] == '#' || used.contains(dis[r][c] - 1))
							continue;
						if (dis[rr][cc] == dis[r][c] - 1) {
							used.add(dis[r][c] - 1);
							maze[rr][cc] = '.';
							qu.add(rr);
							qu.add(cc);
						}
					}
				}
				maze[start[0]][start[1]] = 'o';
				printArray(maze);
			}
		}
		scan.close();
	}
	
	public static void fill(int[][] array, int n) {
		for (int r = 0; r < array.length; r++)
			for (int c = 0; c < array[r].length; c++)
				array[r][c] = n;
	}
	
	public static int[] getPosition(char[][] array, char ch) {
		for (int r = 0; r < array.length; r++)
			for (int c = 0; c < array[r].length; c++)
				if (array[r][c] == ch)
					return new int[] {r, c};
		return new int[] {-1, -1};
	}
	
	public static boolean inBounds(char[][] array, int r, int c) {
		return (r >= 0 && r < array.length) && (c >= 0 && c < array[r].length);
	}
	
	public static void printArray(int[][] obj) {
		for (int[] ob : obj) {
			for (int o : ob)
				print(o);
			printLine();
		}
	}

	public static void printArray(char[][] obj) {
		for (char[] ob : obj) {
			for (char o : ob)
				print(o);
			printLine();
		}
	}

	public static void print(Object... o) {
		for (Object obj : o) {
			System.out.print(obj);
		}
	}

	public static void printLine(Object... o) {
		if (o.length <= 0) {
			System.out.println();
			return;
		}
		for (Object obj : o) {
			System.out.println(obj);
		}
	}

	public static void printF(boolean newLine, String format, Object... o) {
		System.out.printf(format + ((newLine) ? "\n" : ""), o);
	}

}
