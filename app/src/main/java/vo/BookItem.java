package vo;

/* 내가 저장한 책을 데이터베이스에서 가져오는 VO */
public class BookItem {
    private int idx; // 저장한 책 idx
    private String id; // 아이디
    private String book_img; // 저장한 책의 이미지 경로
    private String title; // 저장한 책의 제목
    private String register; // 책을 등록한 날짜

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_img() {
        return book_img;
    }

    public void setBook_img(String book_img) {
        this.book_img = book_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}
