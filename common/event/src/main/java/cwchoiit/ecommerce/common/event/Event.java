package cwchoiit.ecommerce.common.event;

import cwchoiit.ecommerce.common.event.payload.EventPayload;
import cwchoiit.ecommerce.common.serializer.Serializer;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Event<T extends EventPayload> {
    private Long eventId;
    private EventType eventType;
    private T eventPayload;

    public static Event<EventPayload> of(Long eventId, EventType eventType, EventPayload eventPayload) {
        Event<EventPayload> event = new Event<>();
        event.eventId = eventId;
        event.eventType = eventType;
        event.eventPayload = eventPayload;
        return event;
    }

    /**
     * Event 오브젝트를 Json 으로 변환
     * @return 변환된 Event 오브젝트의 Json 데이터
     */
    public String toJson() {
        return Serializer.serialize(this);
    }

    /**
     * Json 데이터를 Event 오브젝트로 변환
     * @param json JSON
     * @return {@link Event<EventPayload>}
     */
    public static Event<EventPayload> fromJson(String json) {
        EventRow eventRowData = Serializer.deserialize(json, EventRow.class);
        if (eventRowData == null) {
            return null;
        }

        EventType eventType = EventType.from(eventRowData.eventType);
        if (eventType == null) {
            return null;
        }

        EventPayload eventPayload = Serializer.deserialize(eventRowData.eventPayload, eventType.getPayloadClass());

        return Event.of(
                eventRowData.eventId,
                eventType,
                eventPayload
        );
    }

    @Getter
    private static class EventRow {
        private Long eventId;
        private String eventType;
        private Object eventPayload;
    }
}
