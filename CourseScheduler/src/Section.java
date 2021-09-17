
public class Section {

	private String course_name;
	private String id;
	private String teacher_name;
	private String classroom;
	private String time;
	private int semester;
	private int credits;
	private String day;
	private char hour;
	
	public Section(String course_name, String id, String teacher_name, String classroom, String time, int semester, int credits) {
		this.course_name = course_name;
		this.id = id;
		this.teacher_name = teacher_name;
		this.classroom = classroom;
		this.time = time;
		this.semester = semester;
		this.credits = credits;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.course_name + " " + this.teacher_name + " " + this.time;
	}
	
	public String detail() {
		return this.classroom + "、學分數" + this.credits + "、學期" + this.semester;	
	}
	
	public int[][] schedule_time() {
		int[][] arr = new int[time.length()][2];
		int t = 0;
		int d = 0;
		for(char c: time.toCharArray()) {
			switch(c) {
				case '日':
					d=0;
					break;
				case '一':
					d=1;
					break;
				case '二':
					d=2;
					break;
				case '三':
					d=3;
					break;
				case '四':
					d=4;
					break;
				case '五':
					d=5;
					break;
				case 'A':
					arr[t][0]=1;
					arr[t][1]=d;
					t++;
					break;
				case 'B':
					arr[t][0]=2;
					arr[t][1]=d;
					t++;
					break;
				case '1':
					arr[t][0]=3;
					arr[t][1]=d;
					t++;
					break;
				case '2':
					arr[t][0]=4;
					arr[t][1]=d;
					t++;
					break;
				case '3':
					arr[t][0]=5;
					arr[t][1]=d;
					t++;
					break;
				case '4':
					arr[t][0]=6;
					arr[t][1]=d;
					t++;
					break;
				case 'C':
					arr[t][0]=7;
					arr[t][1]=d;
					t++;
					break;
				case 'D':
					arr[t][0]=8;
					arr[t][1]=d;
					t++;
					break;
				case '5':
					arr[t][0]=9;
					arr[t][1]=d;
					t++;
					break;
				case '6':
					arr[t][0]=10;
					arr[t][1]=d;
					t++;
					break;
				case '7':
					arr[t][0]=11;
					arr[t][1]=d;
					t++;
					break;
				case '8':
					arr[t][0]=12;
					arr[t][1]=d;
					t++;
					break;
				case 'E':
					arr[t][0]=13;
					arr[t][1]=d;
					t++;
					break;
				case 'F':
					arr[t][0]=14;
					arr[t][1]=d;
					t++;
					break;
				case 'G':
					arr[t][0]=15;
					arr[t][1]=d;
					t++;
					break;	
			}
		}
		int[][] st = new int[t][2];
		for(int i = 0; i < t; i++) {
			st[i][0] = arr[i][0];
			st[i][1] = arr[i][1];
		}
		return st;
	}

	public void setDay(int day) {
		switch(day) {
			case 0:
				this.day = "Sun";
				break;
			case 1:
				this.day = "Mon";
				break;
			case 2:
				this.day = "Tue";
				break;
			case 3:
				this.day = "Wed";
				break;
			case 4:
				this.day = "Thu";
				break;
			case 5:
				this.day = "Fri";
				break;
		}
	}
	
	public void setHour(int hour) {
		switch(hour) {
			case 1:
				this.hour = 'A';
				break;
			case 2:
				this.hour = 'B';
				break;
			case 3:
				this.hour = '1';
				break;
			case 4:
				this.hour = '2';
				break;
			case 5:
				this.hour = '3';
				break;
			case 6:
				this.hour = '4';
				break;
			case 7:
				this.hour = 'C';
				break;
			case 8:
				this.hour = 'D';
				break;
			case 9:
				this.hour = '5';
				break;
			case 10:
				this.hour = '6';
				break;
			case 11:
				this.hour = '7';
				break;
			case 12:
				this.hour = '8';
				break;
			case 13:
				this.hour = 'E';
				break;
			case 14:
				this.hour = 'F';
				break;
			case 15:
				this.hour = 'G';
				break;
		}
	}
	
	public String getCourse_name() {
		return this.course_name;
	}

	public String getId() {
		return this.id;
	}

	public String getTeacher_name() {
		return this.teacher_name;
	}

	public String getClassroom() {
		return this.classroom;
	}

	public String getTime() {
		return this.time;
	}

	public int getSemester() {
		return this.semester;
	}

	public int getCredits() {
		return this.credits;
	}
	
	public String getDay() {
		return this.day;
	}
	
	public char getHour() {
		return this.hour;
	}
	
	public String getUrl() {
		String semester = Integer.toString(this.semester);
		String yy = semester.substring(0, 3);
		String smt = semester.substring(3, 4);
		String num = this.id.substring(0, 6);
		String gop = this.id.substring(6, 8);
		String s = this.id.substring(8, 9);
		String url = "https://newdoc.nccu.edu.tw/teaschm/" + semester + "/schmPrv.jsp-yy=" + yy + "&smt=" + smt + "&num=" + num + "&gop=" + gop + "&s=" + s + ".html";
		return url;
	}
	
}
