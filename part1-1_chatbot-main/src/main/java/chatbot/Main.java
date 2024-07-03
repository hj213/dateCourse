package chatbot;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import chatbot.biz.ChatbotApiService;
import chatbot.biz.NaverApiService;
import chatbot.model.SearchResult;
import chatbot.model.SearchResult.Item;
import chatbot.model.UpdateResponse;
import chatbot.model.UpdateResponse.Update;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
    public static void main(String[] args) {
        String baseUrl = "https://openapi.naver.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String clientID = "pvjKOJLyvNU0xuC8bCns";
        String clientSecret = "4RVTdf_xQm";

        final String TOKEN = "6952170629:AAH8NLID2cd4xRtAESjpGlFyLDrI2IZvxH0"; // 여기에 텔레그램 봇 토큰을 입력하세요

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://api.telegram.org/bot" + TOKEN + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NaverApiService naverService = retrofit.create(NaverApiService.class);
        ChatbotApiService telegramService = retrofit2.create(ChatbotApiService.class);

        SearchResult searchResult = null;
        SearchResult searchResult2 = null;
        SearchResult searchResult3 = null;
        

        try {
            UpdateResponse response = telegramService.getUpdates(0).execute().body();
            long lastId = 0;
            if (response != null && response.result != null && !response.result.isEmpty()) {
                lastId = response.result.get(response.result.size() - 1).updateId;
            }

            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                TimeUnit.SECONDS.sleep(1); 

                response = telegramService.getUpdates(lastId + 1).execute().body();

                if (response != null && response.result != null) {
                    for (Update update : response.result) {
                        long id = update.message.from.id;
                        String text = update.message.text;

                        // 사용자가 말한 내용을 출력
                        System.out.println("사용자 메시지: " + text);

                        String replyMessage = "";

                        if (text.equals("/start")) {
                            replyMessage = "안녕하세요 😀 \n데이트 코스를 추천드리는 챗봇입니다. \n검색하고 싶은 지하철 역을 입력해주세요.";
                        } else {
                            // 사용자가 지하철 역 이름을 입력한 경우
                            searchResult = naverService.search(clientID, clientSecret, "blog", text + " 맛집 -카페", 10, 1).execute().body();
                            searchResult2 = naverService.search(clientID, clientSecret, "blog", text + " 놀거리", 10, 1).execute().body();
                            searchResult3 = naverService.search(clientID, clientSecret, "blog", text + " 카페", 10, 1).execute().body();
                            

                            if (text.matches(".*역$") && searchResult != null && searchResult.items != null && !searchResult.items.isEmpty()) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(text).append(" 데이트 코스예요. \n\n");
                                
                                Random randnum = new Random();
                                int rand1 = randnum.nextInt(10) ;
                                int rand2 = randnum.nextInt(10) ;
                                int rand3 = randnum.nextInt(10) ;
                                
                                List<Item> list1 = searchResult.items;
                                List<Item> list2 = searchResult2.items;
                                List<Item> list3 = searchResult3.items;
                                
                                Item food = list1.get(rand1);
                                Item fun = list2.get(rand2);
                                Item cafe = list3.get(rand3);
                                
                                sb.append("📍 우선 맛있는 밥집을 추천드려요. \n [").append(food.getTitle().replace("<b>", "").replace("</b>", "")).append("]\n ").append(food.getLink()).append("\n\n");
                                sb.append( "📍 소화시키기 좋은 놀거리를 추천드려요. \n [").append(fun.getTitle().replace("<b>", "").replace("</b>", "")).append("]\n ").append(fun.getLink()).append("\n\n");
                                sb.append("📍 분위기 좋은 카페를 추천드려요. \n [").append(cafe.getTitle().replace("<b>", "").replace("</b>", "")).append("]\n ").append(cafe.getLink()).append("\n\n");
                                
                                replyMessage = sb.toString();
                                
                            } else {
                                replyMessage = "지하철 역을 입력해주세요.";
                            }
                        }
                        System.out.println('hi');
                        
                        telegramService.sendMessage(String.valueOf(id), replyMessage).execute().body();

                        lastId = update.updateId;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
