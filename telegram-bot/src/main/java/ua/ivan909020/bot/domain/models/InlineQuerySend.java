package ua.ivan909020.bot.domain.models;

import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;

import java.util.List;
import java.util.Objects;

public class InlineQuerySend {

    private final String inlineQueryId;
    private final List<InlineQueryResult> inlineQueryResults;
    private final Integer offset;

    public InlineQuerySend(String inlineQueryId, List<InlineQueryResult> inlineQueryResults) {
        this.inlineQueryId = inlineQueryId;
        this.inlineQueryResults = inlineQueryResults;
        this.offset = null;
    }

    public InlineQuerySend(String inlineQueryId, List<InlineQueryResult> inlineQueryResults, Integer offset) {
        this.inlineQueryId = inlineQueryId;
        this.inlineQueryResults = inlineQueryResults;
        this.offset = offset;
    }

    public String getInlineQueryId() {
        return inlineQueryId;
    }

    public List<InlineQueryResult> getInlineQueryResults() {
        return inlineQueryResults;
    }

    public Integer getOffset() {
        return offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InlineQuerySend that = (InlineQuerySend) o;
        return Objects.equals(inlineQueryId, that.inlineQueryId) &&
                Objects.equals(inlineQueryResults, that.inlineQueryResults) &&
                Objects.equals(offset, that.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inlineQueryId, inlineQueryResults, offset);
    }

    @Override
    public String toString() {
        return "InlineQuerySend{" +
                "inlineQueryId='" + inlineQueryId + '\'' +
                ", inlineQueryResults=" + inlineQueryResults +
                ", offset=" + offset +
                '}';
    }

}
