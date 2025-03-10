package cwchoiit.ecommerce.common.outboxmessagerelay;

import lombok.Getter;

import java.util.List;
import java.util.stream.LongStream;

@Getter
public class AssignedShard {
    /**
     * 애플리케이션에 할당된 샤드 번호들을 담고 있는 리스트
     */
    private List<Long> shards;

    public static AssignedShard of(String appId, List<String> appIds, long shardCount) {
        AssignedShard assignedShard = new AssignedShard();
        assignedShard.shards = assign(appId, appIds, shardCount);
        return assignedShard;
    }

    private static List<Long> assign(String appId, List<String> appIds, long shardCount) {
        int appIndex = findAppIndex(appId, appIds); // 현재 애플리케이션의 인덱스
        if (appIndex == -1) { // 인덱스가 -1인 경우, 현재 애플리케이션이 zSet 에 저장되지 않았음을 의미
            return List.of(); // 현재 애플리케이션은 담당할 샤드가 없으므로 빈 리스트 반환
        }

        long start = appIndex * shardCount / appIds.size(); // 할당할 샤드의 첫번째 번호
        long end = ((appIndex + 1) * shardCount / appIds.size()) - 1; // 할당할 샤드의 마지막 번호

        // 예를 들어, 오더 애플리케이션이 2개가 띄워져 있고 오더 데이터베이스의 샤드 개수가 4개라면, 0번 애플리케이션은 0-1 샤드를, 1번 애플리케이션은 2-3 샤드를 담당하게 된다.
        return LongStream.rangeClosed(start, end).boxed().toList();
    }

    /**
     * 파라미터로 받은 {@code appIds}라는 이 outbox-message-relay 모듈을 내려받은 서비스의 모든 애플리케이션 ID들 중에서,
     * 현재 애플리케이션의 ID가 몇번째 인덱스인지 찾는 메서드.
     *
     * @param appId  현재 애플리케이션 ID
     * @param appIds 이 서비스의 모든 애플리케이션 ID
     * @return 현재 실행중인 애플리케이션의 index
     */
    private static int findAppIndex(String appId, List<String> appIds) {
        for (int i = 0; i < appIds.size(); i++) {
            if (appIds.get(i).equals(appId)) {
                return i;
            }
        }
        return -1;
    }
}
