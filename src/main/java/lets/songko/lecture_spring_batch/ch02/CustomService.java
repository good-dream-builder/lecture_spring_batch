package lets.songko.lecture_spring_batch.ch02;

public class CustomService {
    public void run() {
        System.out.println("▶ CustomService 호출");
    }

    public void run(String message) {
        System.out.println("▶ CustomService 호출 : " + message);
    }
}
