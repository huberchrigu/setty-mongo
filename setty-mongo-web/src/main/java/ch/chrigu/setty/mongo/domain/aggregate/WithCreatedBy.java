package ch.chrigu.setty.mongo.domain.aggregate;

public interface WithCreatedBy extends WithId {
    String getCreatedBy();
}
