package vo;

/* 책마다 저장한 메모를 데이터베이스에서 가져오는 VO */
public class MemoVO {
    private int m_idx; // 저장한 메모 idx
    private int idx; // 메모를 등록한 책 idx
    private String m_title; // 메모 제목
    private String m_content; // 메모 내용
    private String m_register; // 메모를 등록한 날짜

    public int getM_idx() {
        return m_idx;
    }

    public void setM_idx(int m_idx) {
        this.m_idx = m_idx;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }

    public String getM_content() {
        return m_content;
    }

    public void setM_content(String m_content) {
        this.m_content = m_content;
    }

    public String getM_register() {
        return m_register;
    }

    public void setM_register(String m_register) {
        this.m_register = m_register;
    }
}
