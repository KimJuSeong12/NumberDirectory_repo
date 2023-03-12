package numberDirectoryProject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberDirectoryExample {
	public static final int INPUT = 1, PRINT = 2, SEARCH = 3, UPDATE = 4, SORT = 5, DELETE = 6, EXIT = 7;
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean run = true;
		int no = 0;
		DBConnection dbConnection = new DBConnection();
		while (run) {
			System.out.println("===============================================================");
			System.out.println("| 1.정보입력 | 2.정보출력 | 3.검색 | 4.수정 | 5.정렬 | 6.삭제 | 7.종료 |");
			System.out.println("===============================================================");
			System.out.print(">>");
			no = Integer.parseInt(sc.nextLine());
			switch (no) {
			case INPUT:
				NumberDirectory numberDirectory = inputData();
				int returnValue = dbConnection.insert(numberDirectory);
				if (returnValue == 1) {
					System.out.println("삽입성공");
				} else {
					System.out.println("삽입실패");
				}
				break;
			case PRINT:
				ArrayList<NumberDirectory> list2 = dbConnection.select();
				if (list2 == null) {
					System.out.println("선택실패");
				} else {
					printData(list2);
				}
				break;
			case SEARCH:
				String dataName = searchData();
				ArrayList<NumberDirectory> list3 = dbConnection.nameSearchSelect(dataName);
				if (list3.size() >= 1) {
					printData(list3);
				} else {
					System.out.println("이름 검색 오류");
				}
				break;
			case UPDATE:
				int updateReturnValue = 0;
				int id = inputId();
				NumberDirectory nd = dbConnection.selectId(id);
				if (nd == null) {
					System.out.println("수정 오류 발생");
				} else {
					NumberDirectory updateNumberDirectory = updateData(nd);
					updateReturnValue = dbConnection.update(updateNumberDirectory);
				}
				if (updateReturnValue == 1) {
					System.out.println("update 성공");
				} else {
					System.out.println("update 실패");
				}
				break;
			case SORT:
				ArrayList<NumberDirectory> list4 = dbConnection.selectSort();
				if (list4 == null) {
					System.out.println("정렬 실패");
				} else {
					printScoreSort(list4);
				}
				break;
			case DELETE:
				int deleteId = inputId();
				int deleteReturnValue = dbConnection.delete(deleteId);
				if (deleteReturnValue == 1) {
					System.out.println("삭제 성공");
				} else {
					System.out.println("삭제 실패");
				}
				break;
			case EXIT:
				System.out.println("종료");
				run = false;
				break;
			}
		}
	}

	private static void printScoreSort(ArrayList<NumberDirectory> list4) {
		System.out.println("ID" + "\t" + "이름" + "\t" + "나이" + "\t" + "성별" + "\t" + "   " + "전화번호" + "\t" + "주소");
		for (int i = 0; i < list4.size(); i++) {
			System.out.println(list4.get(i));
		}

	}

	private static int inputId() {
		boolean run = true;
		int id = 0;
		while (run) {
			try {
				System.out.print("ID 입력(number) : ");
				id = Integer.parseInt(sc.nextLine());
				if (id > 0 && id < Integer.MAX_VALUE) {
					run = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("id 입력 오류");
			}
		}
		return id;
	}

	private static NumberDirectory updateData(NumberDirectory nd) {
		int age = inputUpdateAge(nd.getName(), "나이", nd.getAge());
		nd.setAge(age);
		String num = inputUpdateNum(nd.getName(), "전화번호", nd.getNum());
		nd.setNum(num);
		String address = inputUpdateAddress(nd.getName(), "주소", nd.getAddress());
		nd.setAddress(address);
		System.out.println("ID" + "\t" + "이름" + "\t" + "나이" + "\t" + "성별" + "\t" + "전화번호" + "\t" + "지역");
		System.out.println(nd);
		return nd;
	}

	private static String inputUpdateAddress(String name, String string, String address) {
		boolean run = true;
		String data = null;
		while (run) {
			System.out.println("주소를 입력해 주세요. : ");
			System.out.print(name + " " + string + " " + address + ">>");
			try {
				data = sc.nextLine();
				if (data.equals("경기도") || data.equals("전라도") || data.equals("강원도") || data.equals("충청도")
						|| data.equals("경상도")) {
					run = false;
				} else {
					System.out.println("지역을 잘못입력하였습니다. 재입력요청");
				}
			} catch (NumberFormatException e) {
				System.out.println("지역 입력에 오류 발생");
			}
		}
		return data;
	}

	private static String inputUpdateNum(String name, String string, String num) {
		boolean run = true;
		String data = null;
		while (run) {
			System.out.println("전화번호를 입력해 주세요. ex) 010-XXXX-XXXX");
			System.out.print(name + " " + string + " " + num + ">>");
			try {
				data = sc.nextLine();
				Pattern pattern = Pattern.compile("^010-\\d{3,4}-\\d{4}$");
				Matcher matcher = pattern.matcher(data);
				if (matcher.find()) {
					run = false;
				} else {
					System.out.println("전화번호를 잘못입력하였습니다. 재입력요청");
				}
			} catch (NumberFormatException e) {
				System.out.println("전화번호 입력에 오류 발생");
				data = null;
			}
		}
		return data;
	}

	private static int inputUpdateAge(String name, String string, int age) {
		boolean run = true;
		int data = 0;
		while (run) {
			System.out.print(name + " " + string + " " + age + ">>");
			try {
				data = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(data));
				if (matcher.find() && age < 150) {
					run = false;
				} else {
					System.out.println("나이를 잘못입력하였습니다. 재입력요청");
				}
			} catch (NumberFormatException e) {
				System.out.println("나이 입력에 오류 발생");
				data = 0;
			}
		}
		return data;
	}

	private static String searchData() {
		String name = null;
		name = matchingName();
		return name;
	}

	private static String matchingName() {
		String name = null;
		while (true) {
			try {
				System.out.print("이름을 입력하세요 : ");
				name = sc.nextLine();
				Pattern pattern = Pattern.compile("^[가-힣]{3}$");
				Matcher matcher = pattern.matcher(name);
				if (matcher.find()) {
					break;
				} else {
					System.out.println("이름을 잘못 입력하셨습니다.");
					continue;
				}

			} catch (InputMismatchException e) {
				System.out.println("이름 입력중 오류 발생");
			}
		}
		return name;
	}

	private static void printData(ArrayList<NumberDirectory> list3) {
		System.out.println("ID" + "\t" + "이름" + "\t" + "나이" + "\t" + "성별" + "\t" + "   " + "전화번호" + "\t" + "주소");
		for (NumberDirectory data : list3) {
			System.out.println(data);
		}

	}

	private static NumberDirectory inputData() {
		String name = matchingName();
		int age = inputAge();
		String gender = inputGender();
		String num = numberMatching();
		String address = inputAddress();
		NumberDirectory nd = new NumberDirectory(name, age, gender, num, address);
		return nd;

	}

	private static String inputAddress() {
		String address = null;
		while (true) {
			try {
				System.out.println("사는 곳을 입력해주세요. [경기도, 강원도, 충청도, 경상도, 전라도]");
				address = sc.nextLine();
				if (address.equals("경기도") || address.equals("전라도") || address.equals("강원도") || address.equals("충청도")
						|| address.equals("경상도")) {
					break;
				} else {
					System.err.println("주소입력 오류 발생 다시 입력하세요");
					continue;
				}
			} catch (Exception e) {
				System.out.println("주소입력중 오류 발생");
			}
		}
		return address;
	}

	private static String numberMatching() {
		String num = null;
		while (true) {
			try {
				System.out.print("당신의 전화번호를 입력해 주세요.(010-XXXX-XXXX) : ");
				num = sc.nextLine();
				Pattern pattern = Pattern.compile("^010-\\d{3,4}-\\d{4}$");
				Matcher matcher = pattern.matcher(String.valueOf(num));
				if (matcher.find()) {
					break;
				} else {
					System.out.println("입력값 오류");
				}

			} catch (Exception e) {
				System.out.println("전화번호 입력중 오류 발생");
			}
		}
		return num;
	}

	private static int inputAge() {
		int age = 0;
		while (true) {
			try {
				System.out.print("나이를 입력하세요. : ");
				age = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(age));
				if (matcher.find() && age < 150) {
					break;
				} else {
					System.out.println("입력값 오류");
					continue;
				}

			} catch (NumberFormatException e) {
				System.out.println("나이 입력중 오류 발생");
				continue;
			}
		}
		return age;
	}

	private static String inputGender() {
		String gender = null;
		while (true) {
			try {
				System.out.print("성별을 입력해주세요 (남자/여자) : ");
				gender = sc.nextLine();
				if (gender.equals("남자") || gender.equals("여자")) {
					break;
				} else {
					System.out.println("성별 입력이 잘못되었습니다.");
					continue;
				}

			} catch (InputMismatchException e) {
				System.out.println("성별 입력중 오류 발생");
			}

		}
		return gender;
	}

}