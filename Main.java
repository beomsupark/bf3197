import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	static int r;
	static int c;
	static Point[] swan_point = new Point[2];
	
	static int[] move_x = {0,1,0,-1};
	static int[] move_y = {1,0,-1,0};
	
	static char[][] map;
	static boolean[][] swan_move_check;
	
	static Queue<int[]> swan_move = new LinkedList<>();
	static Queue<int[]> water_point = new LinkedList<>();
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		String[] s =bf.readLine().split(" ");
		r= Integer.parseInt(s[0]);
		c= Integer.parseInt(s[1]);
		map = new char[r+1][c+1];
		int swan_index =0;
		swan_move_check = new boolean[r+1][c+1];
		for(int i=1;i<=r;i++)
		{
			String a = bf.readLine();
			for(int j=1;j<=c;j++)
			{
				map[i][j] = a.charAt(j-1);
				if(map[i][j]=='L'||map[i][j]=='.')
				{
					if(map[i][j]=='L')
					{
					swan_point[swan_index++]=new Point(j,i);
					}
					water_point.offer(new int[] {j,i});
				}
			}		
		}
		swan_move.offer(new int[] {swan_point[0].x,swan_point[0].y});
		int day =0;
		while(true)
		{
			if(swan_mate(swan_point[1]))
			{
				break;
			}
			
			next_day();
			day++;
		}
		System.out.println(day);
	}
	public static boolean swan_mate(Point target)
	{
		Queue<int[]> temp = new LinkedList<>();
		while(!swan_move.isEmpty())
		{
			int[] a =swan_move.poll();
			int x = a[0];
			int y = a[1];
			swan_move_check[y][x]=true;
			for(int i=0;i<4;i++)
			{
				int nx =x+move_x[i];
				int ny =y+move_y[i];
				if(isrange(nx,ny)&&!swan_move_check[ny][nx])
				{
					if(map[ny][nx]=='.')
					{
						swan_move.offer(new int[] {nx,ny});
					}
					else if(map[ny][nx]=='X')
					{
						temp.offer(new int[] {nx,ny});
					}
					else if(map[ny][nx]=='L')
					{
						return true;
					}
					
				}
			}
		}
		swan_move = temp;
		return false;
	}
	public static void next_day()
	{
		int size =water_point.size();
		for(int t=0;t<size;t++)
		{
			int[] a =water_point.poll();
			int x = a[0];
			int y = a[1];
			for(int i=0;i<4;i++)
			{
				int nx =x+move_x[i];
				int ny =y+move_y[i];
				if(isrange(nx,ny))
				{
					if(map[ny][nx]=='X')
					{
						map[ny][nx] = '.';
						water_point.add(new int[] {nx,ny});
					}
				}
			}
		}
	}

	public static boolean isrange(int x, int y)
	{
		if(1<=x&&x<=c&&1<=y&&y<=r)
		{
			return true;
		}
		else
		{
		return false;
		}
	}
}
