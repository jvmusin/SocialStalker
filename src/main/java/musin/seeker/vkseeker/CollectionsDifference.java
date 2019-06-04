package musin.seeker.vkseeker;

import lombok.Data;

import java.util.List;

@Data
public class CollectionsDifference<T> {
    private final List<T> added;
    private final List<T> removed;
}