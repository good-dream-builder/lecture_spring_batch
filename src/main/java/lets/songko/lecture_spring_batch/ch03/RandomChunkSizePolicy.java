package lets.songko.lecture_spring_batch.ch03;

import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Random;

/**
 * [CompletionPolicy 구현체의 예]
 * 청크 시작마다 랜덤하게 20 미만의 수를 지정
 * 해당 개수만큼의 아이템이 처리되면 청크를 완료
 */
public class RandomChunkSizePolicy implements CompletionPolicy {
    // 내부 상태
    private int chunkSize;
    private int totalProcessed;
    private Random random = new Random();

    @Override
    public boolean isComplete(RepeatContext context, RepeatStatus result) {
        if (RepeatStatus.FINISHED.equals(result)) {
            return true;
        } else {
            return isComplete(context);
        }
    }

    @Override
    public boolean isComplete(RepeatContext context) {
        return this.totalProcessed >= this.chunkSize;
    }

    // Chunk의 시작, 초기화
    @Override
    public RepeatContext start(RepeatContext parent) {
        this.chunkSize = random.nextInt(20);
        this.totalProcessed = 0;
        System.out.println("ChunkSize: " + this.chunkSize);
        return parent;
    }

    // 각 아이템이 처리(read, write가 수행 된 후??)되면, 내부 상태 갱신
    @Override
    public void update(RepeatContext context) {
        this.totalProcessed++;
    }
}
