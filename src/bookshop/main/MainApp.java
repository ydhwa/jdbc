package bookshop.main;

import java.util.List;
import java.util.Scanner;

import bookshop.dao.BookDao;
import bookshop.vo.BookVo;

public class MainApp {

	public static void main(String[] args) {
		displayBookInfo();
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("대여 하고 싶은 책의 번호를 입력하세요:");
		Long no = scanner.nextLong();
		scanner.close();
		
		rent(no);
		displayBookInfo();
		
	}
	
	private static void rent(Long bookNo) {
		new BookDao().update(bookNo, "대여중");
	}

	private static void displayBookInfo() {
		System.out.println("*****도서 정보 출력하기******");
		List<BookVo> list = new BookDao().getList();
		for(BookVo vo: list) {
			System.out.println(
					"책 제목: " + vo.getTitle() + 
					", 작가: " + vo.getAuthorName() + 
					", 대여 유무: " + vo.getStatus());
		}
	}
}