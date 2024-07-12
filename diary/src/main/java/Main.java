import java.util.Scanner;

import diary.service.DiaryService;

public class Main {
	static Scanner scanner;
	static DiaryService service;
	
	public static void main(String[] args) {
		service = new DiaryService();
		scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("메뉴를 선택해주세요.");
			System.out.println("1. 일기 작성하기 ");
			System.out.println("2. 일기 전체보기 ");
			System.out.println("3. 일기 선택보기 ");
			System.out.println("4. 일기 삭제하기 ");
			System.out.println("5. 일기 수정하기 ");
			System.out.println("6. 일기 검색하기 ");
			System.out.println("9. 시스템 종료 ");
			
			switch (scanner.nextInt()) {
				case 1:
					addDiary();
					break;
				case 2:
					getAllDiaries();
					break;
				case 3:
					getOneDiary();
					break;
				case 4:
					getRemoveDiary();
					break;
				case 5:
					getUpdateDiary();
					break;
				case 6:
					searchDiary();
					break;
				case 9:
					System.out.println("시스템 종료");
					scanner.close();
					return;
					
				default:
					break;
			}
		}
	}

	private static void searchDiary() {
		scanner.nextLine();
		System.out.println("검색할 키워드를 입력하세요.");
		String kwd = scanner.nextLine();
		scanner.nextLine();
		service.serviceDiary(kwd);
	}

	private static void getUpdateDiary() {
		scanner.nextLine();
		System.out.println("수정할 일기 번호를 입력하세요.");
		int id = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("타이틀을 입력해주세요(50자)");
		String title = scanner.nextLine();
		if(title.length() > 50) {
			System.out.println("타이틀은 50자 이하입니다.");
			return;
		}
		System.out.println("내용을 입력해주세요(500자)");
		String content = scanner.nextLine();
		if(content.length() > 500) {
			System.out.println("내용은 500자 이하입니다.");
			return;
		}
		
		service.updateDiary(id,title, content);
		
	}

	private static void getRemoveDiary() {
		scanner.nextLine();
		System.out.println("일기 번호를 입력해주세요");
		int id = scanner.nextInt();
		scanner.nextLine();
		service.getRemoveDiary(id);
	}

	public static void getAllDiaries() {
		scanner.nextLine();
		service.getAllDiaries();
	}
	
	public static void getOneDiary() {
		scanner.nextLine();
		System.out.println("일기 번호를 입력해주세요");
		int id = scanner.nextInt();
		scanner.nextLine();
		service.getOneDiary(id);
	}
	
	public static void addDiary() {
		scanner.nextLine();
		System.out.println("타이틀을 입력해주세요(50자)");
		String title = scanner.nextLine();
		if(title.length() > 50) {
			System.out.println("타이틀은 50자 이하입니다.");
			return;
		}
		System.out.println("내용을 입력해주세요(500자)");
		String content = scanner.nextLine();
		if(content.length() > 500) {
			System.out.println("내용은 500자 이하입니다.");
			return;
		}
		
		service.addDiary(title, content);
	}
}
