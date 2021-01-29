package parse;


import com.wji.bookapp.HomeActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vo.BookVO;

public class Parser {
    
    //xml파싱( 웹에서 요소(제목, 저자, 가격..)을 검색하여 vo에 담는 과정)을 위한 클래스

    BookVO vo;
    String myQuery = ""; // 검색어

    public ArrayList<BookVO> connectNaver(ArrayList<BookVO> list){
        try{
            myQuery = URLEncoder.encode(HomeActivity.et_search.getText().toString(),"UTF8"); // 검색을 받아서 서버 측 URL로 이동

            int count = 100; // 검색결과를 100건 표시

            //정보를 얻기위한 URL준비
            String urlStr = "https://openapi.naver.com/v1/search/book.xml?query="+myQuery+"&display="+count;

            //위의 경로를 URL클래스를 통해서 웹으로 연결
            URL url = new URL( urlStr );

            //Connection 객체를 통해 실제로 url경로로의 접속을 시도
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //발급받은 아이디를 connection에게 전달
            connection.setRequestProperty("X-Naver-Client-Id","QV9fr6D3yRCbliSu3TrQ");

            //발급받은 시크릿을 connection에게 전달
            connection.setRequestProperty("X-Naver-Client-Secret","Tc6ahVwdrf");


            //인증까지 완료한 결과를 xml을 통해 받게 되는데, 이를 받기위한 클래스
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //factory가 받아놓은 xml 경로로 실제로 접근하는 클래스
            XmlPullParser parser = factory.newPullParser();

            parser.setInput( connection.getInputStream(), null );



            //parser를 통해 서버에서 얻어 온 각각의 요소들을 반복 수행 처리
            int parserEvent = parser.getEventType();

            //현재 parser의 커서 위치가 xml문서의 끝을 만날때 까지만 while문을 반복
            while (parserEvent != XmlPullParser.END_DOCUMENT){

                if( parserEvent == XmlPullParser.START_TAG ){ //시작태그를 만났다면
                    String tagName = parser.getName(); //시작태그의 이름(<title>, <link>, <author>...)

                    if( tagName.equalsIgnoreCase("title") ){ // title태그를 만났으면
                        vo = new BookVO();
                        //<b>태그가 섞인 채 넘겨짐
                        String title = parser.nextText(); // title태그의 존재하는 값을 얻는다

                        //네이버는 검색한 단어를 강조하기 위해 <b>태그로 묶어서 결과를 돌려준다 이를 해결하기 위해 정규식을 사용
                        Pattern pattern = Pattern.compile("<.*?>"); // <b>, <a>, <p> .. 등을 잡아낸다
                        //태그가 존재하는지 확인하는 클래스
                        Matcher matcher = pattern.matcher(title); // matcher() : javascript의 test() 역할

                        if(matcher.find()){ // 태그가 존재하면
                            String s_title = matcher.replaceAll(""); // 찾아낸 태그를 공백으로 바꾼다
                            vo.setB_title(s_title);
                        }
                        else{
                            vo.setB_title(title);
                        }

                    }

                    else if(tagName.equalsIgnoreCase("image")){
                        String img = parser.nextText();
                        vo.setB_img(img);
                    }

                    else if(tagName.equalsIgnoreCase("author")){
                        String author = parser.nextText();
                        vo.setB_author(author);
                        list.add(vo);
                    }

                }

                parserEvent = parser.next(); // 다음 요소로 커서를 이동(한 칸씩 내려가기)

            }
        }
        catch (Exception e){

        }

        return list;
    }
}
