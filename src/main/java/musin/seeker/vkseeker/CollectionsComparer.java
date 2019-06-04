package musin.seeker.vkseeker;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class CollectionsComparer {
    public static <T> CollectionsDifference<T> compare(Collection<T> prev, Collection<T> now) {
        List<T> added = getNew(prev, now);
        List<T> removed = getNew(now, prev);
        return new CollectionsDifference<>(added, removed);
    }

    private static <T> List<T> getNew(Collection<T> prev, Collection<T> now) {
        return now.stream()
                .filter(item -> !prev.contains(item))
                .collect(toList());
    }
}