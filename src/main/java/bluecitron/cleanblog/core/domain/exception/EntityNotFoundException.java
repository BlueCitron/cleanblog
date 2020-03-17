package bluecitron.cleanblog.core.domain.exception;

import bluecitron.cleanblog.common.CommonException;

public class EntityNotFoundException extends CommonException {

    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("해당 엔티티를 찾을 수 없습니다. (type=%s, id=%d)", entityName, id));
    }

    public EntityNotFoundException(String entityName, String name) {
        super(String.format("해당 엔티티를 찾을 수 없습니다. (type=%s, name=%d)", entityName, name));
    }
}
